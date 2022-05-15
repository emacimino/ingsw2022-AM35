package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.*;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.NetworkUtilities.Message.GameStateMessage;
import it.polimi.ingsw.NetworkUtilities.Message.Message;
import it.polimi.ingsw.NetworkUtilities.Server.Server;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.View.ActualView;
import it.polimi.ingsw.View.ViewInterface;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class GameInitController implements Serializable,Observer {

    //aggiustare le actualView
    @Serial
    private static final long serialVersionUID = -3248504358856237848L;
    private BasicMatch match;
    private Map<String, ViewInterface> viewMap;
    private int playerInAction = 1;
    private final Observable matchObserver;

    public GameInitController(int numberOfPlayer, Map<String, ViewInterface> viewMap, Observable matchObservers) throws ExceptionGame {
            handleGame(numberOfPlayer,viewMap,matchObservers);
            this.matchObserver = matchObservers;
    }

    private void handleGame(int numberOfPlayer,Map<String, ViewInterface> viewMap, Observable matchObservers) throws ExceptionGame {
        this.match = new FactoryMatch().newMatch(numberOfPlayer);
        this.viewMap = viewMap;
        this.match.setGame(setListOfPlayers());
        this.match.getGame().setRandomlyFirstPlayer();
    }


    private List<Player> setListOfPlayers() {
        List<Player> playersForThisGame = new ArrayList<>();
        for(String username: viewMap.keySet()){
            playersForThisGame.add(new Player(username,username));
        }
        return playersForThisGame;
    }

    public void ReceivedMessage(Message receivedMessage) throws ExceptionGame {

        ActualView actualView = (ActualView) viewMap.get(receivedMessage.getUsername());
        switch (receivedMessage.getGamePhase()) {
            case PLANNING_PHASE -> planningPhaseHandling(receivedMessage, actualView);
            case ACTION_PHASE -> actionPhaseHandling(receivedMessage, actualView);
            default -> {
            } // Should never reach this condition
        }
    }



    private void planningPhaseHandling(Message receivedMessage,ActualView actualView) throws ExceptionGame {
        if (receivedMessage.getType() == GameStateMessage.ASSISTANT_CARD) {
            assistantCardsHandling(receivedMessage);
        } else throw new ExceptionGame("GameStateMessage is not Assistant_Card");
    }

    private void assistantCardsHandling(Message receivedMessage) throws ExceptionGame {
        if (receivedMessage.getPlayer().getUsername().equals(match.getActionPhaseOrderOfPlayers().get(playerInAction).getUsername()) ){
            match.playAssistantsCard(receivedMessage.getPlayer(), (AssistantsCards) receivedMessage.getContentOne());
            playerInAction++;
        }
        else throw new ExceptionGame("Player turn not respected");

        if( playerInAction == match.getNumberOfPlayers() )
            playerInAction = 1;
        }

    private void actionPhaseHandling(Message receivedMessage, ActualView actualView) throws ExceptionGame {
        switch (receivedMessage.getType()){
            case STUDENT_ON_BOARD -> match.moveStudentOnBoard(receivedMessage.getPlayer(), (Student) receivedMessage.getContentOne());
            case STUDENT_IN_ARCHIPELAGO -> match.moveStudentOnArchipelago(receivedMessage.getPlayer(), (Student) receivedMessage.getContentOne(), (Archipelago) receivedMessage.getContentTwo());
            case MOVE_MOTHER_NATURE -> match.moveMotherNature(receivedMessage.getPlayer(), match.getGame().getArchipelagos().get((Integer) receivedMessage.getContentOne()));
        }
    }


    @Override
    public void update(Message message) {
        ActualView actualView = viewMap.get(turnController.getActivePlayer());
        switch (message.getType()) {
            case ASSISTANT_CARD:
                actualView.askPlayAssistantCard(((PositionMessage) message).getPositionList());
                break;
            case BUILD_FX:
                actualView.askBuildFx(((PositionMessage) message).getPositionList());
                break;
            case WIN_FX:
                win();
                break;
            case ERROR:
                ErrorMessage errMsg = (ErrorMessage) message;
                Server.LOGGER.warning(errMsg.getError());
                break;
            default:
                Server.LOGGER.warning("Invalid effect request!");
                break;
        }

}
