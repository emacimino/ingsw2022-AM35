package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
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
    private Map<Integer, Student> studentsMap = new HashMap<>();
    private Map<Integer, Archipelago> archipelagoMap = new HashMap<>();


    public TurnController(Controller controller, Map<String, ViewInterface> viewMap){
        this.controller = controller;
        this.viewMap = viewMap;
    }

    public void nextPlayerPlanningPhase(){
        int indexNewActivePlayer = (controller.getMatch().getPlayers().indexOf(activePlayer) + 1) % controller.getMatch().getNumberOfPlayers();
        setActivePlayer(controller.getMatch().getPlayers().get(indexNewActivePlayer));
       // setTurnPhase(TurnPhase.PLAY_ASSISTANT);
    }
    public void nextPlayerActionPhase(){
        actionOrderOfPlayers.remove(activePlayer);
        if(actionOrderOfPlayers.isEmpty()){
            setTurnPhase(TurnPhase.PLAY_ASSISTANT);
            controller.setGameState(GameState.PLANNING_PHASE);
        }else {
            setActivePlayer(actionOrderOfPlayers.get(0));
        }

    }
    public void setActivePlayer(Player player) { //VOGLIO CHE A LATO CLIENT ATTRAVERSO UN FLAG SETTATO DA QUESTI MESSAGGI IL MIO CLIENT SA SE è IL SUO TURNO O MENO
        activePlayer = player;
        System.out.println("Active player: "+ player);
        RemoteView remoteView = (RemoteView) viewMap.get(player.getUsername());
        remoteView.sendMessage(new YourTurnMessage());
        askNextAction();
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
        setTurnPhase(TurnPhase.PLAY_ASSISTANT);
        setActivePlayer(player);
    }
    protected synchronized void planningPhaseHandling(Message receivedMessage) {
        Player activePlayer = getActivePlayer();
        boolean correctPlay = false;
        if (receivedMessage.getType().equals(TypeMessage.ASSISTANT_CARD)) {
            AssistantsCards assistantsCard = ((AssistantCardMessage) receivedMessage).getAssistantsCard();
            correctPlay = playAssistantCard(activePlayer, assistantsCard);
        }
        if (controller.getMatch().getActionPhaseOrderOfPlayers().size() == viewMap.size()) {
            controller.setGameState(GameState.ACTION_PHASE);
            System.out.println("Game state è diventato "+ controller.getGameState());
            pickFirstPlayerActionPhaseHandler();
        }else if(correctPlay){
            nextPlayerPlanningPhase();
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
        Integer indexArch = message.getArchipelago();
        try {
            Archipelago archipelagoFromIndex = controller.getMatch().getGame().getArchipelagos().get(indexArch);
        }catch (Exception exceptionGame){
            exceptionGame.printStackTrace();
        }
        System.out.println(controller.getMatch().getGame().getArchipelagos());
        Archipelago archipelago = archipelagoMap.get(indexArch);
        System.out.println("Archipelago da mappa: " + archipelago);
        try {
           controller.getMatch().moveMotherNature(getActivePlayer(), archipelagoMap.get(indexArch));
           setTurnPhase(TurnPhase.CHOOSE_CLOUD);
           askNextAction();
        } catch (ExceptionGame exceptionGame) {
            exceptionGame.printStackTrace();
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
        Integer indexStud = message.getStudent();
        Integer indexArch = message.getArchipelago();
        try {
            if (message.getArchipelago() != null) {
                controller.getMatch().moveStudentOnArchipelago(getActivePlayer(), studentsMap.get(indexStud), archipelagoMap.get(indexArch));
            } else {
                controller.getMatch().moveStudentOnBoard(getActivePlayer(), studentsMap.get(indexStud));
            }
            numberOfStudentMoved ++;
            if (numberOfStudentMoved == 3) {
                numberOfStudentMoved = 0;
                setTurnPhase(TurnPhase.MOVE_MOTHERNATURE);
                askNextAction();
            }else
                askingViewToMoveAStudent(numberOfStudentMoved);

        } catch (ExceptionGame exceptionGame) {
            exceptionGame.printStackTrace();
            viewMap.get(getActivePlayer().getUsername()).sendMessage(new ErrorMessage("Can't move more students from board"));
            askingViewToMoveAStudent(numberOfStudentMoved);
        }
    }
    private boolean playAssistantCard(Player activePlayer, AssistantsCards assistantsCard) {
        try {
            controller.getMatch().playAssistantsCard(activePlayer, assistantsCard);
            return true;

        } catch (ExceptionGame e) {
            ViewInterface view = viewMap.get(activePlayer.getUsername());
            view.sendMessage(new ErrorMessage(e.getMessage()));
            askingViewToPlayAnAssistantCard();
            return false;
        }

    }

    public void setActionOrderOfPlayers(List<Player> actionOrderOfPlayers) {
        this.actionOrderOfPlayers.addAll(actionOrderOfPlayers);
        setTurnPhase(TurnPhase.MOVE_STUDENTS);
        setActivePlayer(actionOrderOfPlayers.get(0));
    }
    public void setTurnPhase(TurnPhase turnPhase) {
        this.turnPhase = turnPhase;
    }
    private void askNextAction() {
        switch (turnPhase) {
            case PLAY_ASSISTANT -> askingViewToPlayAnAssistantCard();
            case MOVE_STUDENTS -> askingViewToMoveAStudent(numberOfStudentMoved);
            case MOVE_MOTHERNATURE -> askingViewToMoveMotherNature();
            case CHOOSE_CLOUD -> selectCloudForThisTurn(null);
        }
    }

    private void askingViewToMoveAStudent(int numberOfStudentMoved) {
        RemoteView remoteView = (RemoteView) viewMap.get(activePlayer.getUsername());
        remoteView.showGenericMessage(new GenericMessage("It's your turn, move " + (controller.getMatch().getNumberOfMovableStudents() - numberOfStudentMoved) + " students from your board"));
        try {
            setStudentMap(controller.getMatch().getGame().getWizardFromPlayer(activePlayer).getBoard().getStudentsInEntrance().stream().toList());
            setArchipelagoMap(controller.getMatch().getGame().getArchipelagos());
            remoteView.sendMessage(new ArchipelagoInGameMessage(archipelagoMap));
            remoteView.sendMessage(new BoardMessage(controller.getMatch().getGame().getWizardFromPlayer(activePlayer).getBoard()));
            remoteView.sendMessage(new StudentsOnEntranceMessage(studentsMap));
        } catch (ExceptionGame e) {
            e.printStackTrace();
        }
    }
    private void askingViewToPlayAnAssistantCard() {
        RemoteView remoteView = (RemoteView) viewMap.get(activePlayer.getUsername());
        remoteView.showGenericMessage(new GenericMessage("It's your turn, pick an assistant card"));
        try {
            List<AssistantsCards> assistantsCardsToSend = controller.getMatch().getGame().getWizardFromPlayer(activePlayer).getAssistantsDeck().getPlayableAssistants();
            if(assistantsCardsToSend.size() != controller.getMatch().getGame().getAssistantsCardsPlayedInRound().size()){
                assistantsCardsToSend.removeAll(controller.getMatch().getGame().getAssistantsCardsPlayedInRound());
            }
            remoteView.sendMessage(new AskAssistantCardMessage(assistantsCardsToSend));
        } catch (ExceptionGame e) {
            e.printStackTrace();
        }
    }
    private void askingViewToMoveMotherNature(){
        try{
            RemoteView remoteView = (RemoteView) viewMap.get(activePlayer.getUsername());
            remoteView.showGenericMessage(new GenericMessage("\nIt's your turn, move Mother Nature!!"));
            setArchipelagoMap(controller.getMatch().getGame().getArchipelagos());
            remoteView.sendMessage(new ArchipelagoInGameMessage(archipelagoMap));
            remoteView.sendMessage(new BoardMessage(controller.getMatch().getGame().getWizardFromPlayer(activePlayer).getBoard()));
            remoteView.sendMessage(new AskToMoveMotherNatureMessage(controller.getMatch().getGame().getWizardFromPlayer(activePlayer).getRoundAssistantsCard().getStep()));
        } catch (ExceptionGame e) {
            e.printStackTrace();
        }

    }

    public TurnPhase getTurnPhase() {
        return turnPhase;
    }
    private void setArchipelagoMap(List<Archipelago> archipelagos){
        archipelagoMap.clear();
        Integer i = 1;
        for(Archipelago a: archipelagos){
            archipelagoMap.put(i, a);
            i++;
        }
    }
    private void setStudentMap(List<Student> students){
        studentsMap.clear();
        Integer i = 1;
        for(Student s: students){
            studentsMap.put(i, s);
            i++;
        }
    }
}
