package it.polimi.ingsw.Observer;

import it.polimi.ingsw.Controller.GameState;
import it.polimi.ingsw.Controller.LoginController;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.FactoryMatch.Match;

import java.util.HashMap;
import java.util.Map;

public class MatchObserver implements Observer {
    private Match match;
    private Map<GameState,Game> games = new HashMap<>();

    public MatchObserver(Match match) {
        this.match = match;
    }

    private LoginController loginController;
    private ClientHandlingcontroller clientHandlingcontroller;
    private PlanningController planningController;
    private AttackPhaseController attackPhaseController;

    public MatchObserver(String username) {
        this.username = username;
    }

    public void setMatchObserver(Game game){
        games.put(setGameState(GameState.INITIALIZED),game);
    }

    @Override
    public void update(Object object) {

    }

    public GameState setGameState(GameState gameState) {
        return gameState;
    }
}
