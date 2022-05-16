package it.polimi.ingsw.AlternativeServer;


import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Observer.Observable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SocketClientConnection extends Observable implements Runnable, ClientConnection {
    private Socket socket;
    private ObjectOutputStream outputStream;
    private Server server;
    private Integer numOfMatch = null;
    private int numberOfPlayers;
    private boolean isExpert;

    private boolean active = true;

    public Socket getSocket() {
        return socket;
    }

    public SocketClientConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void setNumOfMatch(Integer numOfMatch) {
        this.numOfMatch = numOfMatch;
    }

    public boolean isActive() {
        return active;
    }

    public synchronized void sendMessage(Object message) {
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
        sendMessage("Connection closed!");
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        active = false;
    }

    private void close() {
        closeConnection();
        System.out.println("Deregistering client...");
        server.deregisterConnection(this);
        System.out.println("Done!");
    }

    @Override
    public void asyncSend(final Object message){
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
        Scanner in;
        String read;
        try{
            in = new Scanner(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            sendMessage("Welcome!\nWhat is your name?");
            read = in.nextLine();
            sendMessage("How many players do you want your match to have? Choose from 2 to 4\n");
            numberOfPlayers = in.nextInt();
            sendMessage("Do you want to play as an expert?");
            String expert = in.nextLine();
            if(expert.equals("yes")){
                isExpert = true;
            }else if(expert.equals("no")){
                isExpert = false;
            }else throw new IllegalArgumentException();

            server.lobby(this);
            in = new Scanner(socket.getInputStream());
            while(isActive()){//waiting for client input to add new connections
                read = in.nextLine();
                notifyObserver(read);//gives notifications to view
            }
        } catch (IOException | NoSuchElementException | ExceptionGame e) {
            System.err.println("Error! " + e.getMessage());
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

}
