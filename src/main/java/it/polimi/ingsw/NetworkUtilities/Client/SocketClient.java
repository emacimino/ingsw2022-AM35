package it.polimi.ingsw.NetworkUtilities.Client;

import it.polimi.ingsw.NetworkUtilities.Server.SocketServer;
import it.polimi.ingsw.View.ActualView;
import it.polimi.ingsw.View.ViewInterface;

import java.net.ServerSocket;
import java.net.Socket;

public class SocketClient {
    private final ViewInterface modelView = new ActualView();

    private ServerSocket socketServer;
    private Socket client;

    public SocketClient(ServerSocket socketServer, Socket client) {
        this.socketServer = socketServer;
        this.client = client;
    }
}
