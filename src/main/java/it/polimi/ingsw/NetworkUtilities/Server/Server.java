package it.polimi.ingsw.NetworkUtilities.Server;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.NetworkUtilities.Client.ClientHandler;

import java.util.Map;

public class Server {
    private GameController gameController;

    private final Map<String, ClientHandler> clientHandlerMap;

    public Server() {
        initializeServer();
    }

    private void initializeServer() {
        gameController = new GameController();
    }
}