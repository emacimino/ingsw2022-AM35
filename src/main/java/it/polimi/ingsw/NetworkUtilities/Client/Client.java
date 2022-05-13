package it.polimi.ingsw.NetworkUtilities.Client;

import it.polimi.ingsw.NetworkUtilities.Message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class Client{

    private Socket client;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private boolean connected;


    public Client(String address, int port) throws IOException {
        this.client = new Socket(address,port);
        this.inputStream = new ObjectInputStream(client.getInputStream());
        this.outputStream = new ObjectOutputStream(client.getOutputStream());
    }

    public void run(){

    }
}
