package it.polimi.ingsw;

import it.polimi.ingsw.NetworkUtilities.Client.Client;
import it.polimi.ingsw.NetworkUtilities.Client.SocketClient;

import java.io.IOException;

public class AppClient {

    public static void main(String[] args) throws IOException {
        SocketClient socketClient = new SocketClient("127.0.0.1",1338);
    }
}
