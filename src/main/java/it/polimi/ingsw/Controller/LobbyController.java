package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.NetworkUtilities.Message.MessageFromServer;
import it.polimi.ingsw.NetworkUtilities.Server.ClientHandler;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.View.ActualView;
import it.polimi.ingsw.View.ViewInterface;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static it.polimi.ingsw.NetworkUtilities.Message.GameStateMessage.ASK_NUM_OF_PLAYERS;
import static it.polimi.ingsw.NetworkUtilities.Message.GameStateMessage.NUMBER_OF_PLAYERS;

public class LobbyController extends Observable implements Serializable {
    @Serial
    private static final long serialVersionUID = 8436960781973268305L;

    private final Map<String, ViewInterface> viewMap;
    private int numOfPlayers;
    private final Observable matchObservers = new Observable();

    public LobbyController() {
        this.viewMap = new HashMap<>();
    }

    public void addPlayer(String username, ClientHandler clientHandler) throws IOException, ClassNotFoundException, InterruptedException, ExceptionGame {
        if(viewMap.isEmpty()){
            createAMatch(username,clientHandler);
        }

        if( viewMap.size() <= numOfPlayers ) {
            ActualView actualView = new ActualView(username,clientHandler);
            viewMap.put(username, actualView);
            this.matchObservers.addObserver(actualView);
        }

        if( viewMap.size() == numOfPlayers ) {
            new GameInitController( numOfPlayers , viewMap , matchObservers);
        }


    }

    private void createAMatch(String username, ClientHandler clientHandler) throws IOException, ClassNotFoundException, ExceptionGame {
        String server = "SERVER";
        ActualView actualView = new ActualView(username,clientHandler);
        viewMap.put(username, actualView);
        this.matchObservers.addObserver(actualView);
        clientHandler.sendMessage(new MessageFromServer(server, ASK_NUM_OF_PLAYERS));
        if(clientHandler.readMessage().getType().equals(NUMBER_OF_PLAYERS))
            numOfPlayers = (int) clientHandler.readMessage().getContentOne();
        else throw new ExceptionGame("Number of Players has not been decided, could not start the game");
    }
}
