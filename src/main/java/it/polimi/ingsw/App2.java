package it.polimi.ingsw;

import it.polimi.ingsw.NetworkUtilities.Client.SocketClient;
import it.polimi.ingsw.NetworkUtilities.Server.SocketServer;

import java.io.IOException;

public class App2 {

    public static void main( String[] args ) throws IOException {
        SocketClient socketClient = new SocketClient("127.0.0.1",1338);
    }


}
