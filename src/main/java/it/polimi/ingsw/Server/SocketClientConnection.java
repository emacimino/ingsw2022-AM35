package it.polimi.ingsw.Server;


import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.NetworkUtilities.Message.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;

public class SocketClientConnection implements Runnable, ClientConnection {
    private final Socket socket;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private final Server server;
    private Integer numOfMatch = null;
    private String username = null;
    private int numberOfPlayers;
    private boolean isExpert;
    private Controller controller;

    private boolean active = true;

    public Socket getSocket() {
        return socket;
    }

    public SocketClientConnection(Socket socket, Server server) throws IOException{
        this.socket = socket;
        this.server = server;
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());

    }

    public void setNumOfMatch(Integer numOfMatch) {
        this.numOfMatch = numOfMatch;
    }

    public boolean isActive() {
        return active;
    }

    public synchronized void sendMessage(Message message) {
        try{
            outputStream.reset();
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (IOException exception) {

            exception.printStackTrace();
        }
    }
    @Override
    public synchronized void closeConnection() {
        sendMessage(new GenericMessage("Connection closed! I'm in close connection"));
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        active = false;
    }

    private void close() {
        closeConnection();
        System.out.println("De-registering client...");
        server.deregisterConnection(this);
        System.out.println("Done!");
    }

    @Override
    public void asyncSendMessage(final Message message){
        new Thread(() -> sendMessage(message)).start();
    }

    @Override
    public Integer getNumOfMatch() {
        return numOfMatch;
    }

    @Override
    public void run() {
        Message newMessage;
        try{
            login();
            while(isActive()){
                newMessage = (Message) inputStream.readObject();
                controller.onMessageReceived(newMessage);
            }
        } catch (IOException | NoSuchElementException | ClassNotFoundException e) {
            asyncSendMessage(new ErrorMessage("Error from SCC! " + e.getMessage()));
            System.err.println("Error from SCC! " + e.getMessage());
            close();
        }
    }

    public boolean isExpert(){
        return isExpert;
    }

    public int getNumberOfPlayers(){
        return numberOfPlayers;
    }

    private void login() throws IOException, ClassNotFoundException {
        asyncSendMessage(new LoginRequest());
        LoginResponse login = (LoginResponse) inputStream.readObject();
        while(server.isNameNotOk(login.getName())){
            asyncSendMessage(new ErrorMessage("Username already used, please choose another username"));
            login = (LoginResponse) inputStream.readObject();
        }
        username = login.getName();
        numberOfPlayers = login.getNumberOfPlayer();
        isExpert = login.isExpertMatch();
        server.lobby(this);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public String getUsername() {
        return username;
    }
}
