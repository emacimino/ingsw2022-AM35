package it.polimi.ingsw.ClientServerNetwork.Server;

import java.net.*;
import java.io.*;

public class ServerMain {
    public static void main(String[] args) throws IOException {

       /* if (args.length != 1) {
            System.err.println("Usage: java KnockKnockServer <port number>");
            System.exit(1);
        }
*/
        //int portNumber = Integer.parseInt(args[0]);
        int portNumber = 1234;
        boolean listening = true;
        try{
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Server is ready to receive");

            while (listening) {
                new MultiThreadServer(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
