package it.polimi.ingsw.AlternativeClient;

import it.polimi.ingsw.NetworkUtilities.Message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client{
    private final String ip;
    private final int port;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    private boolean active = true;

    public synchronized boolean isActive() {
        return active;
    }

    public synchronized void setActive(boolean active) {
        this.active = active;
    }

    public Thread asyncReadFromSocket(final ObjectInputStream obj){
        Thread thread = new Thread(() -> {
            try{
                while (isActive()){
                    Object inputObject = obj.readObject();
                    if(inputObject instanceof Message){
                        //implement a switch
                    }
                    else throw new IllegalArgumentException();
                }
            }catch (Exception e){
                setActive(false);
            }
        });
        thread.start();
        return  thread;
    }


    public Thread asyncWriteToSocket(final Scanner scanner, final PrintWriter socketOut){
        Thread thread = new Thread(() -> {
            try{
                while (isActive()){
                    String inputLine = scanner.nextLine();
                    //create a message from scanner
                    socketOut.println(inputLine);
                    socketOut.flush();
                }
            }catch (Exception e){
                setActive(false);
            }
        });
        thread.start();
        return  thread;
    }

    public void run() throws IOException {
        Socket socketClient = new Socket(ip,port);
        System.out.println("Connection Established");
        ObjectInputStream socketIn = new ObjectInputStream(socketClient.getInputStream());
        PrintWriter socketOut = new PrintWriter(socketClient.getOutputStream());
        Scanner stdin = new Scanner(System.in);

        try{
            Thread t0 = asyncReadFromSocket(socketIn);
            Thread t1 = asyncWriteToSocket(stdin, socketOut);
            t0.join();
            t1.join();
        } catch(InterruptedException | NoSuchElementException e){
            System.out.println("Connection closed from the client side");
        } finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socketClient.close();
        }
    }
}
