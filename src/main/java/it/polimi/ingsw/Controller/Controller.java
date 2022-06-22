package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.NetworkUtilities.Message;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.View.ViewInterface;

import java.util.*;

public class Controller implements Observer {
    private final BasicMatch match;
    private GameState gameState;
    private final Collection<String> playersUsername;
    private Map<String, ViewInterface> viewMap = new HashMap<>();
    private TurnController turnController;
    private boolean matchOnGoing = true;

    public Map<String, ViewInterface> getViewMap() {
        return viewMap;
    }

    //Initialize the Game having already a lobby
    public Controller(BasicMatch match, Collection<String> playersUsername) throws ExceptionGame, CloneNotSupportedException {
        this.playersUsername = playersUsername;
        this.match = match;
    }

    public BasicMatch getMatch() {
        return match;
    }

    public void initGame() throws ExceptionGame{
        List<Player> players = setListOfPlayers(this.playersUsername);
        if (match.getNumberOfPlayers() == 4) {
            match.setTeams(players);
        }
        this.match.setGame(players);
        gameState = GameState.PLANNING_PHASE;
        turnController = new TurnController(this, viewMap);
        turnController.pickFirstPlayerPlanningPhaseHandler();
    }

    public synchronized void onMessageReceived(Message receivedMessage) throws ExceptionGame {
        switch (gameState) {
            case PLANNING_PHASE-> turnController.planningPhaseHandling(receivedMessage);

            case ACTION_PHASE -> turnController.actionPhaseHandling(receivedMessage);

            case GAME_ENDED -> {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            default-> throw new ExceptionGame("Error: invalid gameState");//should never reach this condition
        }
    }



    private List<Player> setListOfPlayers(Collection<String> playersUsername) {
        List<Player> players = new ArrayList<>();
        for (String username : playersUsername) {
            players.add(new Player(username));
        }
        return players;
    }


    public void addView(String username, ViewInterface view){
        viewMap.put(username, view);
    }

    @Override
    public void update(Message message)  {
        try {
            onMessageReceived( message);
        } catch (ExceptionGame e) {
            e.printStackTrace();
        }
    }

    public void setViewMap(Map<String, ViewInterface> viewMap) {
        this.viewMap = viewMap;
    }


    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setMatchOnGoing(boolean matchOnGoing) {
        this.matchOnGoing = matchOnGoing;
    }

    public TurnController getTurnController() {
        return turnController;
    }

    public boolean isMatchOnGoing() {
        return matchOnGoing;
    }
}
