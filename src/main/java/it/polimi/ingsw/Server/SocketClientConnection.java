package it.polimi.ingsw.Server;


import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.NetworkUtilities.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;

/**
 * Handle the connection for one client
 */
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

    /**
     * Constructor of the class
     * @param socket a new one for each new client
     * @param server the server where the client is connected
     * @throws IOException if the object in input or in output could not be created
     */
    public SocketClientConnection(Socket socket, Server server) throws IOException{
        this.socket = socket;
        this.server = server;
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * Setter for the number of the match
     * @param numOfMatch index of the match that is played
     */
    public void setNumOfMatch(Integer numOfMatch) {
        this.numOfMatch = numOfMatch;
    }

    /**
     * Boolean that states if the connection is active
     * @return active parameter
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Send a message from the server after an event
     * @param message message to be sent
     */
    public synchronized void sendMessage(Message message) {

             try {
                 System.out.println("in socketClientController, socket send : " + message);
                 outputStream.reset();
                 outputStream.writeObject(message);
                 outputStream.flush();
             } catch (IOException exception) {

                 exception.printStackTrace();
             }

    }

    /**
     * Handle the closing of a connection
     */
    @Override
    public synchronized void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        active = false;

    }

    /**
     * Close a connection
     */
    public synchronized void close() {
        System.out.println("De-registering client...");
        server.deregisterConnection(this);
        System.out.println("Done!");
        closeConnection();
    }

    /**
     * Send a message after an event
     * @param message message that contains an event
     */
    @Override
    public void asyncSendMessage(final Message message){
        new Thread(() -> sendMessage(message)).start();
    }

    /**
     * Getter for num of match
     * @return num of the match played
     */
    @Override
    public Integer getNumOfMatch() {
        return numOfMatch;
    }

    /**
     * Getter for the controller
     * @return controller for current match
     */
    public Controller getController() {
        return controller;
    }

    /**
     * Runnable implementation of the class, handle all the game communication from client to server
     */
    @Override
    public void run() {
        Message newMessage;
        try{
            login();
            while(isActive()){
                newMessage = (Message) inputStream.readObject();
                if(!(newMessage instanceof Ping) &&  !( newMessage instanceof NewMatchMessage)) {
                    System.out.println("in socketClientController, socket received : " + newMessage);
                    controller.onMessageReceived(newMessage);
                }else if(newMessage instanceof NewMatchMessage){
                    login();
                }
            }
        } catch (IOException | NoSuchElementException | ClassNotFoundException | ExceptionGame e) {
            System.err.println("Error from SCC! ");
            close();
        }
    }

    /**
     * Check if a match is in expert mode
     * @return match mode
     */
    public boolean isExpert(){
        return isExpert;
    }

    /**
     * Getter for number of players of the match
     * @return number of players
     */
    public int getNumberOfPlayers(){
        return numberOfPlayers;
    }

    /**
     * Handle the login phase of the match
     * @throws IOException if the input is not correct
     * @throws ClassNotFoundException if it can't found the correct class
     */
    private void login() throws IOException, ClassNotFoundException {
        asyncSendMessage(new LoginRequest());
        Message message = (Message) inputStream.readObject();
        while(! (message instanceof LoginResponse login)){
            message = (Message) inputStream.readObject();
        }

        while(server.isNameNotOk(login.getName())){
            asyncSendMessage(new ErrorMessage("Username already used or not set, please choose another username"));
            message = (Message) inputStream.readObject();
            while(! (message instanceof LoginResponse)){
                message = (Message) inputStream.readObject();
            }
            login = (LoginResponse) message;
        }
        asyncSendMessage(new OkLoginMessage());
        username = login.getName();
        numberOfPlayers = login.getNumberOfPlayer();
        isExpert = login.isExpertMatch();
        server.lobby(this);
    }




    /**
     * Setter for the match controller
     * @param controller new controller for the match
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Getter for the client username
     * @return client username
     */
    public String getUsername() {
        return username;
    }
}
