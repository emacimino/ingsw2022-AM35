package it.polimi.ingsw.NetworkUtilities.Server;

import it.polimi.ingsw.Controller.ClientController;
import it.polimi.ingsw.NetworkUtilities.Message.GameStateMessage;
import it.polimi.ingsw.NetworkUtilities.Message.Message;
import it.polimi.ingsw.View.ActualView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements ClientHandlerInterface, Runnable{
    private final SocketServer socketServer;
    private final Socket client;

    private boolean connected;

    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;
    private final Object inputLock = new Object();
    private final Object outputLock = new Object();


    public ClientHandler(SocketServer socketServer, Socket client) throws IOException {
        this.socketServer = socketServer;
        this.client = client;

        connected = true;

        this.inputStream = new ObjectInputStream(client.getInputStream());
        this.outputStream = new ObjectOutputStream(client.getOutputStream());

    }
    @Override
    public void run() {
        try {
            handleClientConnection();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void handleClientConnection() throws IOException, ClassNotFoundException {
        while ((!Thread.currentThread().isInterrupted())){
            synchronized (inputLock){
                Message message = (Message) inputStream.readObject();
                if(message!=null && message.getType()== GameStateMessage.LOGIN_REQUEST){
                    //Server.addAClient(message.getMessage().toString(),this);
                    System.out.println(message.getType().toString());
                }
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
