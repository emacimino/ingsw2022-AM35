package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Exception.ExceptionEndGame;
import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.*;
import it.polimi.ingsw.NetworkUtilities.Message.Message;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.View.ViewInterface;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameInitController implements Serializable,Observer {

    @Serial
    private static final long serialVersionUID = -3248504358856237848L;
    private BasicMatch match;
    private Map<String, ViewInterface> viewMap;
    private boolean matchToContinue = true;
    private GameState gameState;

    public GameInitController(int numberOfPlayer, Map<String, ViewInterface> viewMap) throws ExceptionGame {
        handleGame(numberOfPlayer,viewMap);
        try {
            inGameController(viewMap);
        } catch (ExceptionEndGame e) {
            System.out.println("Game won");
        }
    }

    private void handleGame(int numberOfPlayer,Map<String, ViewInterface> viewMap) throws ExceptionGame {
        this.match = new FactoryMatch().newMatch(numberOfPlayer);
        this.viewMap = viewMap;
        this.match.setGame(setListOfPlayers());
        this.match.getGame().setRandomlyFirstPlayer();
        this.gameState = GameState.GAME_STARTED;
    }

    private void inGameController(Map<String,ViewInterface> viewMap) throws ExceptionGame {
            PlanningController planningController = new PlanningController(this.match,viewMap);
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
        for(Observer observer:)
    }

}
