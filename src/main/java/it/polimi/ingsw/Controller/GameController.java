package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.FactoryMatch;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.NetworkUtilities.Message.Message;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.View.ViewInterface;

import java.io.Serializable;
import java.util.Map;

public class GameController implements Observer, Serializable {
    private BasicMatch match;
    private Game game;
    private Map<String, ViewInterface> viewMap;

    public GameController(int numOfPlayers) {
        initializeGameController(numOfPlayers);
    }

    private void initializeGameController(int numOfPlayers) {
        this.match = new FactoryMatch().newMatch(numOfPlayers);
        //gestire l'expertMatch
        this.game = match.getGame();
    }

    @Override
    public void addObserver(Observer observer) {

    }

    @Override
    public void removeObserver(Observer observer) {

    }

    @Override
    public void notifyObserver(Message message) {

    }

    @Override
    public void update(Message message) {

    }

    private
}
