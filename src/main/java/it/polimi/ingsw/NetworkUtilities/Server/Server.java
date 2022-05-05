package it.polimi.ingsw.NetworkUtilities.Server;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.NetworkUtilities.Client.ClientHandling;

import java.util.HashMap;
import java.util.Map;

public class Server {

    private final GameController gameController;

    //Mappa i client che si connettono tramite l'username
    private Map<String,ClientHandling> clientHandlingMap;

    public Server(GameController gameController) {
        this.gameController = gameController;
        this.clientHandlingMap = new HashMap<>();
    }

    public void addAClient(String username, ClientHandling clientHandling){
        if(/*check game started*/){

        }
        else{//if game is not started yet
            clientHandlingMap.put(username,clientHandling);
        }
    }
}
