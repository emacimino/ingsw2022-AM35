package it.polimi.ingsw.NetworkUtilities.Client;

import it.polimi.ingsw.NetworkUtilities.Message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler implements ClientHandlerInterface, Runnable{
    private final ServerSocket serverSocket;
    private final Socket client;

    private boolean connected;

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public ClientHandler(ServerSocket serverSocket, Socket client) throws IOException {

        this.serverSocket = serverSocket;
        this.client = client;

        connected = true;

        this.inputStream = new ObjectInputStream(client.getInputStream());
        this.outputStream = new ObjectOutputStream(client.getOutputStream());

    }
    @Override
    public void run() {
        handleClientConnection();
    }


    private void handleClientConnection() {
        while ((!Thread.currentThread().isInterrupted())){
//message to be implemented
        }
    }

    @Override
    public boolean isConnected() {
        return connected;
        }

    @Override
    public void disconnect() throws IOException {
        if(connected) {
            client.close();
            connected = false;
        }
    }

    @Override
    public void sendMessage(Message message) {

    }
    
}
