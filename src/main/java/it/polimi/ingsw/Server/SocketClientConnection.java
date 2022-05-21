package it.polimi.ingsw.Server;


import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.NetworkUtilities.Message.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;

public class SocketClientConnection implements Runnable, ClientConnection {
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private Server server;
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendMessage(message);
            }
        }).start();
    }

    @Override
    public Integer getNumOfMatch() {
        return numOfMatch;
    }

    @Override
    public void run() {
      //  Scanner in;
        Message newMessage;
        try{
          /*  in = new Scanner(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            sendMessage("How many players do you want your match to have? Choose from 2 to 4");
            numberOfPlayers = in.nextInt();
            sendMessage("Do you want to play as an expert?");
            String expert = in.next();
            if(expert.equals("yes")){
                isExpert = true;
            }else if(expert.equals("no")){
                isExpert = false;
            } else {
                System.out.println("pre error");
              //  throw new IllegalArgumentException();
            }
*/
            login();
           // in = new Scanner(socket.getInputStream());
            while(isActive()){//waiting for client input to add new connections
                System.out.println("devo processare messaggio da client");
                newMessage = (Message) inputStream.readObject();
                controller.onMessageReceived(newMessage);
            }
        } catch (IOException | NoSuchElementException | ExceptionGame | CloneNotSupportedException | ClassNotFoundException e) {
            asyncSendMessage(new ErrorMessage("Error from SCC! " + e.getMessage()));
            System.err.println("Error from SCC! " + e.getMessage());
        }finally{
            close();
        }
    }

    public boolean isExpert(){
        return isExpert;
    }

    public int getNumberOfPlayers(){
        return numberOfPlayers;
    }

    private void login() throws IOException, ClassNotFoundException, ExceptionGame, CloneNotSupportedException {
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
