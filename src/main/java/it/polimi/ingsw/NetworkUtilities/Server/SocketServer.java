package it.polimi.ingsw.NetworkUtilities.Server;

import it.polimi.ingsw.NetworkUtilities.Message.Message;

import java.io.IOException;
import java.lang.reflect.Executable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer implements Runnable{

    private static int PORT = 25342;
    private ServerSocket serverSocket;
    private Server server;

    public SocketServer(Server server) throws IOException {
        this.server = server;
    }


    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (!Thread.currentThread().isInterrupted()){
            try {
                Socket client = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(this, client);
                Thread thread = new Thread(clientHandler);
                thread.start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addClient(String username, ClientHandler clientHandler){
        server.addAClient(username, clientHandler);

    }

    public void receiveAMessage(Message message) {
        server.receiveAmessage(message);
    }

    public void disconnect(ClientHandler clientHandler){
        server.disconnect(clientHandler);
    }

}
