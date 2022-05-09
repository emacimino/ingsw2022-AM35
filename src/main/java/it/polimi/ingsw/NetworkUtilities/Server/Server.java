package it.polimi.ingsw.NetworkUtilities.Server;

import it.polimi.ingsw.Controller.GameController;

import java.util.Map;

public class Server {
    private GameController gameController;

    private final Map<String, ClientHandler> clientHandlerMap;
//implements the creation of a match
    public Server() {
        initializeServer();
    }

    private void initializeServer() {
        gameController = new GameController(match);
    }
}
