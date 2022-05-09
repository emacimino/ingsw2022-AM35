package it.polimi.ingsw.NetworkUtilities.Client;

import it.polimi.ingsw.NetworkUtilities.Message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketClient extends Client{

    private final Socket client;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;


    public SocketClient(String address, int port) throws IOException {
        client = new Socket();
        client.connect(new InetSocketAddress(address,port));//add a timeout
        this.inputStream = new ObjectInputStream(client.getInputStream());
        this.outputStream = new ObjectOutputStream(client.getOutputStream());
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    @Override
    public void sendAMessage(Message message) {

    }

    @Override
    public void readAMessage(Message message) {

    }

    @Override
    public void disconnect() {

    }
}
