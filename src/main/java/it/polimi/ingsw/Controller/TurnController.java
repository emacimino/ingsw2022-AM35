package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.NetworkUtilities.Message.*;
import it.polimi.ingsw.View.RemoteView;
import it.polimi.ingsw.View.ViewInterface;
import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsMembers.Student;

import java.util.*;

public class TurnController {

    private Controller controller;
    private Map<String, ViewInterface> viewMap;
    private Player activePlayer;
    private List<Player> actionOrderOfPlayers = new ArrayList<>();
    private TurnPhase turnPhase = null;
    private int numberOfStudentMoved = 0;


    public TurnController(Controller controller, Map<String, ViewInterface> viewMap){
        this.controller = controller;
        this.viewMap = viewMap;
    }

    public void nextPlayerPlanningPhase(){
        int indexNewActivePlayer = (controller.getMatch().getPlayers().indexOf(activePlayer) + 1) % controller.getMatch().getNumberOfPlayers();
        setActivePlayer(controller.getMatch().getPlayers().get(indexNewActivePlayer));
        setTurnPhase(TurnPhase.PLAY_ASSISTANT);
    }

    public void nextPlayerActionPhase(){
        actionOrderOfPlayers.remove(activePlayer);
        if(actionOrderOfPlayers.isEmpty()){
            setTurnPhase(TurnPhase.PLAY_ASSISTANT);
            controller.setGameState(GameState.PLANNING_PHASE);
        }else
            setActivePlayer(actionOrderOfPlayers.get(0));
    }

    public void setActivePlayer(Player player) { //VOGLIO CHE A LATO CLIENT ATTRAVERSO UN FLAG SETTATO DA QUESTI MESSAGGI IL MIO CLIENT SA SE è IL SUO TURNO O MENO
        activePlayer = player;
        System.out.println("Active player: "+ player);
        RemoteView remoteView = (RemoteView) viewMap.get(player.getUsername());
        remoteView.sendMessage(new YourTurnMessage());
        for(String c : viewMap.keySet()){
            if(!c.equals(player.getUsername()))
                viewMap.get(c).sendMessage(new EndTurnMessage());
        }
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    protected void pickFirstPlayerPlanningPhaseHandler() {
        Random r = new Random();
        Player player = controller.getMatch().getPlayers().get(r.nextInt(0, controller.getMatch().getNumberOfPlayers()));
        setActivePlayer(player);
        setTurnPhase(TurnPhase.PLAY_ASSISTANT);
    }
    protected synchronized void planningPhaseHandling(Message receivedMessage) {
        System.out.println("SONO IN PLANNING DI TURN");
        System.out.println(receivedMessage.getType());
        Player activePlayer = getActivePlayer();
        if (receivedMessage.getType().equals(TypeMessage.ASSISTANT_CARD)) {
            AssistantsCards assistantsCard = ((AssistantCardMessage) receivedMessage).getAssistantsCard();
            playAssistantCard(activePlayer, assistantsCard);
        }
        if (controller.getMatch().getActionPhaseOrderOfPlayers().size() == viewMap.size()) {
            controller.setGameState(GameState.ACTION_PHASE);
            pickFirstPlayerActionPhaseHandler();
        }
    }

    protected void pickFirstPlayerActionPhaseHandler() {
        setActionOrderOfPlayers(controller.getMatch().getActionPhaseOrderOfPlayers());
        setTurnPhase(TurnPhase.MOVE_STUDENTS);
    }
    protected void actionPhaseHandling(Message receivedMessage) {
        switch (receivedMessage.getType()) {
            case MOVE_STUDENT -> {
                MoveStudentMessage message = (MoveStudentMessage) receivedMessage;
                moveStudentsForThisTurn(message);
            }
            case CHOOSE_CLOUD -> {
                CloudMessage message = (CloudMessage) receivedMessage;
                selectCloudForThisTurn(message);
            }
            case MOVE_MOTHER_NATURE -> {
                MoveMotherNatureMessage message = (MoveMotherNatureMessage) receivedMessage;
                MoveMotherNatureForThisTurn(message);

            }

            //find a way to understand if influence in archipelago changed
            // case MOVE_MOTHER_NATURE -> match.moveMotherNature(receivedMessage.getPlayer(), match.getGame().getArchipelagos().get((Integer) receivedMessage.getContentOne()));
            default ->
                    throw new IllegalStateException("Unexpected value: " + receivedMessage.getType());

        }

    }
    private void MoveMotherNatureForThisTurn(MoveMotherNatureMessage message) {
        try {
           controller.getMatch().moveMotherNature(getActivePlayer(), message.getArchipelago());
           setTurnPhase(TurnPhase.CHOOSE_CLOUD);
        } catch (ExceptionGame exceptionGame) {
            viewMap.get(getActivePlayer().getUsername()).sendMessage(new ErrorMessage("Can't move MotherNature in this position"));
        }
    }

    private void selectCloudForThisTurn(CloudMessage message) {
        try {
            controller.getMatch().chooseCloud(getActivePlayer(), message.getCloud());
        } catch (ExceptionGame e) {
            e.printStackTrace();
            viewMap.get(getActivePlayer().getUsername()).sendMessage(new ErrorMessage("Can't select this cloud"));
        }
        viewMap.get(getActivePlayer().getUsername()).sendMessage(new EndTurnMessage());
        nextPlayerActionPhase();
    }

    private void moveStudentsForThisTurn(MoveStudentMessage message) {
        try {
            if (message.getArchipelago() != null) {
                controller.getMatch().moveStudentOnArchipelago(getActivePlayer(), message.getStudent(), message.getArchipelago());
            } else {
                controller.getMatch().moveStudentOnBoard(getActivePlayer(), message.getStudent());
            }
            numberOfStudentMoved ++;
            if (numberOfStudentMoved == 3) {
                numberOfStudentMoved = 0;
                setTurnPhase(TurnPhase.MOVE_MOTHERNATURE);
            }

        } catch (ExceptionGame exceptionGame) {
            exceptionGame.printStackTrace();
            viewMap.get(getActivePlayer().getUsername()).sendMessage(new ErrorMessage("Can't move more students from board"));
        }
    }


    private void playAssistantCard(Player activePlayer, AssistantsCards assistantsCard) {
        try {
            System.out.println("SONO DENTRO PLAY ASSISTANT");
            controller.getMatch().playAssistantsCard(activePlayer, assistantsCard);
            System.out.println("ho giocato ASSISTANT");
            nextPlayerPlanningPhase();

        } catch (ExceptionGame e) {
            ViewInterface view = viewMap.get(activePlayer.getUsername());
            view.sendMessage(new ErrorMessage(e.getMessage()));
            askingViewToPlayAnAssistantCard();
        }

    }

    public void setActionOrderOfPlayers(List<Player> actionOrderOfPlayers) {
        Collections.copy(this.actionOrderOfPlayers, actionOrderOfPlayers);
        setActivePlayer(actionOrderOfPlayers.get(0));
    }

    public void setTurnPhase(TurnPhase turnPhase) {
        this.turnPhase = turnPhase;
        switch (turnPhase) {
            case PLAY_ASSISTANT ->  askingViewToPlayAnAssistantCard();
            case MOVE_STUDENTS ->   askingViewToMoveAStudent();

        }
        System.out.println("set the phase of " + activePlayer.getUsername()  + " to " + turnPhase);
    }

    private void askingViewToMoveAStudent() {
        RemoteView remoteView = (RemoteView) viewMap.get(activePlayer.getUsername());
        remoteView.showGenericMessage(new GenericMessage("It's your turn, move " + controller.getMatch().getNumberOfMovableStudents() + " students from your board"));
        try {
            List<Student> studentsFromBoard = controller.getMatch().getGame().getWizardFromPlayer(activePlayer).getBoard().getStudentsInEntrance().stream().toList();
            remoteView.askToMoveStudentFromEntrance(studentsFromBoard);
        } catch (ExceptionGame e) {
            e.printStackTrace();
        }
    }

    private void askingViewToPlayAnAssistantCard() {
        RemoteView remoteView = (RemoteView) viewMap.get(activePlayer.getUsername());
        remoteView.showGenericMessage(new GenericMessage("It's your turn, pick an assistant card"));
        try {
            remoteView.askAssistantCard(controller.getMatch().getGame().getWizardFromPlayer(activePlayer).getAssistantsDeck().getPlayableAssistants());
        } catch (ExceptionGame e) {
            e.printStackTrace();
        }
    }

}
