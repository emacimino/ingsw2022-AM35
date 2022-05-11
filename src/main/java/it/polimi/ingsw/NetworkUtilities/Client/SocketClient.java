package it.polimi.ingsw.NetworkUtilities.Client;

import it.polimi.ingsw.NetworkUtilities.Message.Message;
import it.polimi.ingsw.NetworkUtilities.Message.Ping;
import it.polimi.ingsw.NetworkUtilities.Server.SocketServer;
import it.polimi.ingsw.View.ActualView;
import it.polimi.ingsw.View.ViewInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketClient implements Client{

    private final Socket client = new Socket();

    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    public SocketClient(String address, int port) throws IOException {
        client.connect(new InetSocketAddress(address,port));
        this.inputStream = new ObjectInputStream(client.getInputStream());
        this.outputStream = new ObjectOutputStream(client.getOutputStream());
        sendMessage(new Ping());
    }

    @Override
    public void sendMessage(Message message) throws IOException {
        try{
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            disconnect();
        }
    }

    @Override
    public void readMessage() throws IOException {
        try {
            Object o = inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            disconnect();
        }

    }

    @Override
    public void disconnect() throws IOException {
        if(!client.isClosed()) {
            client.close();
        }
    }

}
