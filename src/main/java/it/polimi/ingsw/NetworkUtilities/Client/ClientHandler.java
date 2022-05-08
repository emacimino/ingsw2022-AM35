package it.polimi.ingsw.NetworkUtilities.Client;

import it.polimi.ingsw.NetworkUtilities.Message.GameStateMessage;
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
    private Object inputLock;
    private Object outputLock;

    public ClientHandler(ServerSocket serverSocket, Socket client) throws IOException {

        this.serverSocket = serverSocket;
        this.client = client;

        connected = true;

        this.inputStream = new ObjectInputStream(client.getInputStream());
        this.outputStream = new ObjectOutputStream(client.getOutputStream());

    }
    @Override
    public void run() {
        try {
            handleClientConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void handleClientConnection() throws IOException, ClassNotFoundException {
        while ((!Thread.currentThread().isInterrupted())){
            synchronized (inputLock){
                Message message = (Message) inputStream.readObject();
                if(message!=null && message.getType()== GameStateMessage.LOGIN)
            }
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
        try{
            outputStream.writeObject(message);
            outputStream.reset();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    
}
