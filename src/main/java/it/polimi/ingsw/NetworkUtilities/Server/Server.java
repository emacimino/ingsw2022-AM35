package it.polimi.ingsw.NetworkUtilities.Server;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.View.ActualView;
import it.polimi.ingsw.View.ViewInterface;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private GameController gameController;

    private final Map<String, ClientHandler> clientHandlerMap;

    public Server(GameController gameController) {
        this.gameController = gameController;
        this.clientHandlerMap = new ConcurrentHashMap<>();
    }


    public void addAClient(String username, ClientHandler clientHandler) {
        ViewInterface view = new ActualView(clientHandler);
        this.clientHandlerMap.put(username, clientHandler);

    }
}
