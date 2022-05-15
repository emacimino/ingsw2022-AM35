package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.NetworkUtilities.Message.GameStateMessage;
import it.polimi.ingsw.NetworkUtilities.Message.Message;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.View.ActualView;
import it.polimi.ingsw.View.ViewInterface;

import java.util.*;

import static it.polimi.ingsw.Controller.GameState.END_OF_TURN;

public class PlanningController implements Observer {
    private final BasicMatch match;
    private final Map<String, ViewInterface> viewMap;
    private List<Player> roundPlayingOrder;
    private Player playerInTurn;

    public PlanningController(BasicMatch match, Map<String, ViewInterface> viewMap) {
        this.match = match;
        this.viewMap = viewMap;
        this.roundPlayingOrder = match.getActionPhaseOrderOfPlayers();
        this.playerInTurn = roundPlayingOrder.get(1); //set first player in round

    }


    @Override
    public void update(Message message) throws ExceptionGame {
        Map<String,Integer> values = new HashMap<>();
        ActualView actualView = (ActualView) viewMap.get(playerInTurn.getUsername());
        for (Player player: roundPlayingOrder) {
            if (message.getType().equals(GameStateMessage.ASSISTANT_CARD)) {
                values.put(playerInTurn.getUsername(),(Integer) message.getContent());
                match.playAssistantsCard(playerInTurn, (AssistantsCards) message.getContent());
            } else {
                throw new ExceptionGame("Wrong message sent");
            }
        }
    }


        }
