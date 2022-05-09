package it.polimi.ingsw.NetworkUtilities.Client;

import it.polimi.ingsw.NetworkUtilities.Message.Message;
import it.polimi.ingsw.View.ActualView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler  implements Client{
    private ServerSocket serverSocket;
    private Socket client;
    private ActualView actualView;

    private boolean connected;

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public ClientHandler(ServerSocket serverSocket, Socket client) throws IOException {

        this.serverSocket = serverSocket;
        this.client = client;

        connected = true;

        this.inputStream = new ObjectInputStream(client.getInputStream());
        this.outputStream = new ObjectOutputStream(client.getOutputStream());

        actualView = new ActualView(this);
    }

    @Override
    public void sendMessage(Message message) {
        try{
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readMessage(Message message) {
        try {
            Object o = inputStream.readObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void disconnect() {

    }
}
