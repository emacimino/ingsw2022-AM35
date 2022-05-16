package it.polimi.ingsw.NetworkUtilities.Client;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.NetworkUtilities.Message.ErrorMessage;
import it.polimi.ingsw.NetworkUtilities.Message.Message;
import it.polimi.ingsw.Observer.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import java.util.Observer;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client extends Observable implements ClientInterface {

    private Socket client;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private ExecutorService executionQueue;
    private boolean connected;


    public Client(String address, int port) throws IOException {
        this.client = new Socket();
        this.client.connect(new InetSocketAddress(address, port));
        this.inputStream = new ObjectInputStream(client.getInputStream());
        this.outputStream = new ObjectOutputStream(client.getOutputStream());
        this.executionQueue = Executors.newSingleThreadExecutor();
    }


    @Override
    public void sendMessage(Message message) throws IOException, ExceptionGame {
        try {
            outputStream.writeObject(message);
            outputStream.reset();
        } catch (IOException e) {
            disconnect();
            notifyObserver(new ErrorMessage(null, "Message not sent"));
        }
    }


    @Override
    public void readMessage() throws IOException {
        executionQueue.execute(() -> {
            while(!executionQueue.isShutdown()){
                Message message;
                try {
                    message = (Message) inputStream.readObject();
                } catch (IOException | ClassNotFoundException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    @Override
    public void disconnect() throws IOException {
        if(client.isConnected()){
            executionQueue.shutdownNow();
            client.close();
        }
    }
}
