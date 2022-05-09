package it.polimi.ingsw.NetworkUtilities.Client;

import it.polimi.ingsw.NetworkUtilities.Message.Message;
import it.polimi.ingsw.NetworkUtilities.Server.SocketServer;
import it.polimi.ingsw.View.ActualView;
import it.polimi.ingsw.View.ViewInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClient{

    private SocketServer socketServer;
    private Socket client;

    public SocketClient(SocketServer socketServer, Socket client) {
        this.socketServer = socketServer;
        this.client = client;
    }
}
