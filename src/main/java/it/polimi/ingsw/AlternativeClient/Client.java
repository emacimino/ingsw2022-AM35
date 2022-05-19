package it.polimi.ingsw.AlternativeClient;

import it.polimi.ingsw.NetworkUtilities.Message.AssistantCardListMessage;
import it.polimi.ingsw.NetworkUtilities.Message.GenericMessage;
import it.polimi.ingsw.NetworkUtilities.Message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
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

    public Thread asyncReadFromSocket(final ObjectInputStream obj){ //da inoltrare alla view (CLI E GUI)
        Thread thread = new Thread(() -> {
            try{
                while (isActive()){
                    Object inputObject = obj.readObject();
                    if(inputObject instanceof String) {
                        System.out.println(inputObject);
                    }else if(inputObject instanceof Message) {
                         Message message = (Message)inputObject;
                         switch (message.getType()){
                             case GENERIC_MESSAGE -> System.out.println((String) ((GenericMessage)message).getContent());

                             case LIST_ASSISTANT_CARD -> System.out.println(((AssistantCardListMessage)message).getAssistantsCards());
                         }
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
