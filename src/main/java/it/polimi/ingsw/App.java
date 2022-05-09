package it.polimi.ingsw;

import it.polimi.ingsw.NetworkUtilities.Server.Server;
import it.polimi.ingsw.NetworkUtilities.Server.SocketServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ){
        SocketServer socketServer = new SocketServer(1338);
    }

}
