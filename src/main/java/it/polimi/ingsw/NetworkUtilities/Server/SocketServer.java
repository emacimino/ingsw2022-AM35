package it.polimi.ingsw.NetworkUtilities.Server;

import it.polimi.ingsw.NetworkUtilities.Client.ClientHandler;
import it.polimi.ingsw.NetworkUtilities.Client.SocketClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer implements Runnable{

    private final Server server;
    private final int port;
    private ServerSocket serverSocket;

    public SocketServer(Server server, int port) {
        this.server = server;
        this.port = port;
    }

    @Override
    public void run() {

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (!Thread.currentThread().isInterrupted()){
            try {
                Socket client = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(this.serverSocket,client);
                Thread thread = new Thread(clientHandler); //add a name to this connection

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}