package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.NetworkUtilities.Message;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.View.ViewInterface;

import java.util.*;

/**
 * Controller of the match, instantiate the model and help to run a match through the server
 */
public class Controller implements Observer {
    private final BasicMatch match;
    private GameState gameState;
    private final Collection<String> playersUsername;
    private Map<String, ViewInterface> viewMap = new HashMap<>();
    private TurnController turnController;
    private boolean matchOnGoing = true;

    /**
     * Constructor of the class
     * @param match associated to controller
     * @param playersUsername list of players of the match
     * @throws ExceptionGame if model could not be created
     * @throws CloneNotSupportedException if controller could not send a clone
     */
    public Controller(BasicMatch match, Collection<String> playersUsername) throws ExceptionGame, CloneNotSupportedException {
        this.playersUsername = playersUsername;
        this.match = match;
    }


    /**
     * Getter of the match
     * @return an instance of a Match
     */
    public BasicMatch getMatch() {
        return match;
    }

    /**
     * Initialize a match
     * @throws ExceptionGame if the match could not be initialized
     */
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

    /**
     * receive a message and set the correct action to be played by the model
     * @param receivedMessage message whit what to do
     * @throws ExceptionGame if the action could not be taken
     */
    public synchronized void onMessageReceived(Message receivedMessage) throws ExceptionGame {
        switch (gameState) {
            case PLANNING_PHASE-> turnController.planningPhaseHandling(receivedMessage);

            case ACTION_PHASE -> turnController.actionPhaseHandling(receivedMessage);

            case GAME_ENDED -> {
                //do nothing
            }
            default-> throw new ExceptionGame("Error: invalid gameState");//should never reach this condition
        }
    }


    /**
     * Set the list of players to create a game
     * @param playersUsername list of names received from the lobby
     * @return list of players for the match
     */
    private List<Player> setListOfPlayers(Collection<String> playersUsername) {
        List<Player> players = new ArrayList<>();
        for (String username : playersUsername) {
            players.add(new Player(username));
        }
        return players;
    }

    /**
     * add a view for every player
     * @param username user of the player
     * @param view either cli or gui
     */
    public void addView(String username, ViewInterface view){
        viewMap.put(username, view);
    }

    /**
     * update observer by the received message
     * @param message received update message
     */
    @Override
    public void update(Message message)  {
        try {
            onMessageReceived( message);
        } catch (ExceptionGame e) {
            e.printStackTrace();
        }
    }

    /**
     * Setter for viewMap
     * @param viewMap associate view and usernames
     */
    public void setViewMap(Map<String, ViewInterface> viewMap) {
        this.viewMap = viewMap;
    }

    /**
     * Setter for gameState
     * @param gameState to be set
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Getter for gameState
     * @return gameState
     */
    public GameState getGameState() {
        return gameState;
    }
    /**
     * Setter for matchOnGoing
     * @param matchOnGoing match currently playing
     */
    public void setMatchOnGoing(boolean matchOnGoing) {
        this.matchOnGoing = matchOnGoing;
    }

    /**
     * Getter for matchOnGoing
     * @return matchOnGoing
     */
    public boolean isMatchOnGoing() {
        return matchOnGoing;
    }

    public TurnController getTurnController(){
        return this.turnController;
    }
}
