package it.polimi.ingsw.Client;

import it.polimi.ingsw.Controller.TurnPhase;
import it.polimi.ingsw.NetworkUtilities.Message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public abstract class Client{
    private final String ip;
    private final int port;
    protected ObjectOutputStream outputStream;
    private boolean active = true;
    protected ObjectInputStream socketIn;
    protected TurnPhase turnPhase = TurnPhase.LOGIN;
    private final RemoteModel remoteModel = new RemoteModel();

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public synchronized boolean isActive() {
        return active;
    }

    public synchronized void setActive(boolean active) {
        this.active = active;
    }

    public abstract Thread asyncReadFromSocket(final ObjectInputStream socketInput);
    public abstract Thread asyncWriteToSocket();
    public abstract void login();

    protected synchronized void sendToServer(Message message) {
        try{
            outputStream.reset();
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void run() throws IOException {
        Socket socketClient = new Socket(ip,port);
        System.out.println("Connection Established");
        socketIn = new ObjectInputStream(socketClient.getInputStream());
        outputStream = new ObjectOutputStream(socketClient.getOutputStream());

        try{
            Thread t0 = asyncReadFromSocket(socketIn);
            Thread t1 = asyncWriteToSocket();
            t0.join();
            t1.join();
        } catch(InterruptedException | NoSuchElementException e){
            System.out.println("Connection closed from the client side");
        } finally {
            socketIn.close();
            outputStream.close();
            socketClient.close();
        }
    }

    protected void setNextAction(Message message) {
        switch (message.getType()){
            case ASK_ASSISTANT_CARD -> this.turnPhase = TurnPhase.PLAY_ASSISTANT;
            case STUDENTS_ON_ENTRANCE ->  this.turnPhase = TurnPhase.MOVE_STUDENTS;
            case ASK_MOVE_MOTHER_NATURE -> this.turnPhase = TurnPhase.MOVE_MOTHERNATURE;
            case CLOUD_IN_GAME -> this.turnPhase = TurnPhase.CHOOSE_CLOUD;
            case END_OF_TURN -> this.turnPhase = TurnPhase.END_TURN;
            case REQUEST_LOGIN -> login();
            default -> {
                break;
            }

        }
    }

    public RemoteModel getRemoteModel() {
        return remoteModel;
    }
}
