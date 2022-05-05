package it.polimi.ingsw.NetworkUtilities.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler {
    private ServerSocket serverSocket;
    private Socket client;

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
}
