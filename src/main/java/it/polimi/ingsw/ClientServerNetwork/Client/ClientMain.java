package it.polimi.ingsw.ClientServerNetwork.Client;
import it.polimi.ingsw.ClientServerNetwork.Server.JsonObjectsHandler;

import java.io.*;
import java.net.*;

public class ClientMain {
    public static void main(String[] args) throws IOException {

        String hostName = "PC-Ema";
        int portNumber = 1234;
        JsonObjectsHandler jsonObjectsHandler = new JsonObjectsHandler();

        try {
                Socket clientSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()) );
            String fromServer;

            while ((fromServer = in.readLine()) != null) {
                System.out.println("Server: " + fromServer);
                if (fromServer.equals("Game Over"))
                    break;
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }
}