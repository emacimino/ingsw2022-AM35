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

    public SocketServer(){

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.run();
    }


    @Override
    public void run() {

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

}
