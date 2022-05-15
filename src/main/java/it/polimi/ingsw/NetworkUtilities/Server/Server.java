package it.polimi.ingsw.NetworkUtilities.Server;

import it.polimi.ingsw.Controller.GameInitController;
import it.polimi.ingsw.Controller.LobbyController;
import it.polimi.ingsw.NetworkUtilities.Message.Message;
import it.polimi.ingsw.View.ActualView;
import it.polimi.ingsw.View.ViewInterface;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server{

    private List<LobbyController> lobbyControllerList = new ArrayList<>();

    private final Map<String, ClientHandler> clientHandlerMap;

    public Server() {
        this.executorService = Executors.newFixedThreadPool(128);
        this.clientHandlerMap = new ConcurrentHashMap<>();}


    public void addAClient(String username, ClientHandler clientHandler) {
        ViewInterface view = new ActualView(username, clientHandler);
        this.clientHandlerMap.put(username, clientHandler);

    }

    public static void sendMessageFromServer(Message message){

    }

    public void receiveAMessage(Message message) {
    }

    public void disconnect(ClientHandler clientHandler) {
    }


}
