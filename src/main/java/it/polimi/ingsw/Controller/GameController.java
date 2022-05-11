package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.*;
import it.polimi.ingsw.NetworkUtilities.Message.Message;
import it.polimi.ingsw.NetworkUtilities.Message.NumberOfPlayer;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.View.ViewInterface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameController implements Serializable,Observer {

    private static final long serialVersionUID = -3248504358856237848L;
    private final BasicMatch match;
    private final Map<String, ViewInterface> viewMap;
    private boolean matchToContinue = true;

    public GameController(NumberOfPlayer numberOfPlayer,Map<String, ViewInterface> viewMap) throws ExceptionGame {
        this.match = new FactoryMatch().newMatch(numberOfPlayer.getNumberOfPlayers());
        this.viewMap = viewMap;
        this.match.setGame(setListOfPlayers());
        this.match.getGame().setRandomlyFirstPlayer();
        inGameController();
    }

    private void inGameController() throws ExceptionGame {
        while(matchToContinue){
            TurnController turnController = new TurnController(this.match);
        }
    }


    private List<Player> setListOfPlayers() {
        List<Player> playersForThisGame = new ArrayList<>();
        for(String username: viewMap.keySet()){
            playersForThisGame.add(new Player(username,username));
        }
        return playersForThisGame;
    }


    @Override
    public void update(Message message) {

    }

}
