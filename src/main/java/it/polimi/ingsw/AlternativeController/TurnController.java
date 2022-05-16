package it.polimi.ingsw.AlternativeController;

import it.polimi.ingsw.AlternativeView.RemoteView;
import it.polimi.ingsw.AlternativeView.ViewInterface;
import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.Player;

import java.util.*;

public class TurnController {

    private Controller controller;
    private Map<String, ViewInterface> viewMap;
    private Player activePlayer;
    private List<Player> actionOrderOfPlayers = new ArrayList<>();


    public TurnController(Controller controller, Map<String, ViewInterface> viewMap){
        this.controller = controller;
        this.viewMap = viewMap;
    }

    public void nextPlayerPlanningPhase(){
        int setActivePlayer = (controller.getMatch().getPlayers().indexOf(activePlayer) + 1) % controller.getMatch().getNumberOfPlayers();
        activePlayer = controller.getMatch().getPlayers().get(setActivePlayer);
    }

    public void nextPlayerActionPhase(){
        int setActivePlayer = (actionOrderOfPlayers.indexOf(activePlayer)+ 1) % controller.getMatch().getNumberOfPlayers();
        activePlayer = actionOrderOfPlayers.get(setActivePlayer);
    }

    public void setActivePlayer(Player player) {
        activePlayer = player;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public void setActionOrderOfPlayers(List<Player> actionOrderOfPlayers) {
        Collections.copy(this.actionOrderOfPlayers, actionOrderOfPlayers);
        activePlayer = actionOrderOfPlayers.get(0);
    }
}
