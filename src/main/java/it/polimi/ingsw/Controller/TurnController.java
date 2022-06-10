package it.polimi.ingsw.Controller;

//import it.polimi.ingsw.Model.Exception.ExceptionStudentBagEmpty;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.*;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Cloud;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.NetworkUtilities.Message.*;
import it.polimi.ingsw.View.RemoteView;
import it.polimi.ingsw.View.ViewInterface;
import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsMembers.Student;

import java.util.*;

public class TurnController {
    private MessageHandler messageHandler = new MessageHandler();
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
    }
    public void nextPlayerActionPhase(){
        //actionOrderOfPlayers.remove(activePlayer);
        if(controller.getMatch().getActionPhaseOrderOfPlayers().isEmpty()){
            setTurnPhase(TurnPhase.PLAY_ASSISTANT);
            controller.setGameState(GameState.PLANNING_PHASE);
            setActivePlayer(actionOrderOfPlayers.get(0));
        }else {
            setActivePlayer(actionOrderOfPlayers.get(actionOrderOfPlayers.indexOf(activePlayer)+1));
        }

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
            case CLOUD_CHOICE -> {
                CloudMessage message = (CloudMessage) receivedMessage;
                selectCloudForThisTurn(message);
            }
            case MOVE_MOTHER_NATURE -> {
                MoveMotherNatureMessage message = (MoveMotherNatureMessage) receivedMessage;
                MoveMotherNatureForThisTurn(message);
            }
            case PLAY_CHARACTER_CARD -> {
                PlayCharacterMessage message = (PlayCharacterMessage) receivedMessage;
                playCharacterCardForThisTurn(message);
            }

            default ->
                    throw new IllegalStateException("Unexpected value: " + receivedMessage.getType());

        }
    }

    private boolean playAssistantCard(Player activePlayer, AssistantsCards assistantsCard) {
        try {
            controller.getMatch().playAssistantsCard(activePlayer, assistantsCard);
            return true;

        } catch (ExceptionGame e) {
            ViewInterface view = viewMap.get(activePlayer.getUsername());
            sendMessageToView(new GenericMessage(e.getMessage()), (RemoteView) view);
            askingViewToPlayAnAssistantCard();
            return false;
        }

    }
    private void MoveMotherNatureForThisTurn(MoveMotherNatureMessage message) {
        Integer indexArch = message.getArchipelago();
        try {
            controller.getMatch().moveMotherNature(getActivePlayer(), messageHandler.getArchipelagoMap().get(indexArch));
            setTurnPhase(TurnPhase.CHOOSE_CLOUD);
            askNextAction();
        } catch (ExceptionGame exceptionGame) {
            exceptionGame.printStackTrace();
            sendMessageToView(new GenericMessage("Can't move MotherNature in this position"), (RemoteView)viewMap.get(getActivePlayer().getUsername()) );

        }
    }
    private void selectCloudForThisTurn(CloudMessage message) {
        try {
            controller.getMatch().chooseCloud(getActivePlayer(), messageHandler.getCloudMap().get(message.getCloud()));
            setTurnPhase(TurnPhase.MOVE_STUDENTS);
            nextPlayerActionPhase();

        } catch (ExceptionGame e) {
            e.printStackTrace();
            sendMessageToView(new GenericMessage(e.getMessage()), (RemoteView)viewMap.get(getActivePlayer().getUsername()) );
        }


    }
    private void moveStudentsForThisTurn(MoveStudentMessage message) {
        Integer indexStud = message.getStudent();
        Integer indexArch = message.getArchipelago();
        try {
            if (message.getArchipelago() != null) {
                Student s = messageHandler.getStudentsOnEntranceMap().get(indexStud);
                Archipelago a = messageHandler.getArchipelagoMap().get(indexArch);
                controller.getMatch().moveStudentOnArchipelago(getActivePlayer(), s, a);
            } else {
                Student s = messageHandler.getStudentsOnEntranceMap().get(indexStud);
                controller.getMatch().moveStudentOnBoard(getActivePlayer(), s);
            }
            numberOfStudentMoved ++;
            if (numberOfStudentMoved == controller.getMatch().getNumberOfMovableStudents()) {
                numberOfStudentMoved = 0;
                setTurnPhase(TurnPhase.MOVE_MOTHERNATURE);
                askNextAction();
            }else
                askingViewToMoveAStudent(numberOfStudentMoved);

        } catch (ExceptionGame exceptionGame) {
            exceptionGame.printStackTrace();
            sendMessageToView(new GenericMessage("Can't move more students from board"), (RemoteView)viewMap.get(getActivePlayer().getUsername()) );
            askingViewToMoveAStudent(numberOfStudentMoved);
        }
    }

    private void playCharacterCardForThisTurn(PlayCharacterMessage message){
        String cardName = message.getCharacterCard().getName();
        CharacterCard card = ((ExpertMatch)controller.getMatch()).getCharacterCardInMatchMap().get(cardName);
        Archipelago archipelago;
        int numOfStep;
        List<Student> studentFromCardToTrade;
        List<Student> studentFromBoardToTrade;
        List<Student> studentFromEntranceToTrade;

        if(((ExpertMatch)controller.getMatch()).getCharacterCardInMatchMap().containsKey(cardName) &&
                (!message.getCharacterCard().getName().equals("Archer") && !cardName.equals("Chef") && !cardName.equals("Knight") && !cardName.equals("Baker"))){
            handleCardSettings(((ExpertMatch)controller.getMatch()).getCharacterCardInMatchMap().get(cardName),message);
        }

        try {

            message.getCharacterCard().useCard((ExpertMatch) controller.getMatch());

        } catch (ExceptionGame e) {
            System.out.println("Character card move not valid");
        }

    }

    private void handleCardSettings(CharacterCard card, PlayCharacterMessage message) {
        switch (card.getName()) {

            case "Messenger" -> {
                card.setArchipelagoEffected(this.controller.getMatch().getGame().getArchipelagos().get(message.getIndexOfArchipelago()));
            }
            case "Princess" -> {
                //card.setActiveStudents(this.controller.getMatch().getGame());
            }
            case "Jester" -> {
            }
            case "Friar" -> {
            }
            case "Minstrel" -> {
            }
            case "Magician" -> {
            }
            case "Banker" -> { }

            case "Herbalist" -> {
                 }

            default -> throw new IllegalStateException("Unexpected value: " + card.getName());
        }
    }


    public void setActionOrderOfPlayers(List<Player> actionOrderOfPlayers) {
        this.actionOrderOfPlayers.clear();
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
            case CHOOSE_CLOUD -> askingViewToChooseCloud();
        }
    }
    public void setActivePlayer(Player player) {
        activePlayer = player;
        System.out.println("Active player: "+ player);
        RemoteView remoteView = (RemoteView) viewMap.get(player.getUsername());
        sendMessageToView(new YourTurnMessage(), remoteView);
        askNextAction();
        for(String c : viewMap.keySet()){
            if(!c.equals(player.getUsername()))
                sendMessageToView(new EndTurnMessage(), (RemoteView) viewMap.get(c));

        }
    }
    public Player getActivePlayer() {
        return activePlayer;
    }

    private void askingViewToMoveAStudent(int numberOfStudentMoved) {
        RemoteView remoteView = (RemoteView) viewMap.get(activePlayer.getUsername());
        sendMessageToView(new GenericMessage("It's your turn, move " + (controller.getMatch().getNumberOfMovableStudents() - numberOfStudentMoved) + " students from your board"), remoteView);
        try {
            messageHandler.setStudentOnEntranceMap(controller.getMatch().getGame().getWizardFromPlayer(activePlayer).getBoard().getStudentsInEntrance().stream().toList());
            messageHandler.setArchipelagoMap(controller.getMatch().getGame().getArchipelagos());

            sendMessageToView(new AskToMoveStudents(), remoteView);
            sendMessageToView(new ArchipelagoInGameMessage(messageHandler.getArchipelagoMap()), remoteView);
            sendMessageToView(new BoardMessage(controller.getMatch().getGame().getWizardFromPlayer(activePlayer).getBoard()), remoteView);
            sendMessageToView(new StudentsOnEntranceMessage(messageHandler.getStudentsOnEntranceMap()), remoteView);

        } catch (ExceptionGame e) {
            e.printStackTrace();
        }
    }
    private void askingViewToPlayAnAssistantCard() {
        RemoteView remoteView = (RemoteView) viewMap.get(activePlayer.getUsername());
       // remoteView.showGenericMessage(new GenericMessage("It's your turn, pick an assistant card"));
        try {
            List<AssistantsCards> assistantsCardsToSend = new ArrayList<>(controller.getMatch().getGame().getWizardFromPlayer(activePlayer).getAssistantsDeck().getPlayableAssistants());
            if(assistantsCardsToSend.size() != controller.getMatch().getGame().getAssistantsCardsPlayedInRound().size()){
                assistantsCardsToSend.removeAll(controller.getMatch().getGame().getAssistantsCardsPlayedInRound());
            }
            sendMessageToView(new AskAssistantCardMessage(assistantsCardsToSend), remoteView);
        } catch (ExceptionGame e) {
            e.printStackTrace();
        }
    }
    private void askingViewToMoveMotherNature(){
        try{
            RemoteView remoteView = (RemoteView) viewMap.get(activePlayer.getUsername());
            remoteView.showGenericMessage(new GenericMessage("\nIt's your turn, move Mother Nature!!"));
            messageHandler.setArchipelagoMap(controller.getMatch().getGame().getArchipelagos());
            sendMessageToView(new AskToMoveMotherNatureMessage(controller.getMatch().getGame().getWizardFromPlayer(activePlayer).getRoundAssistantsCard().getStep()), remoteView);
            sendMessageToView(new ArchipelagoInGameMessage(messageHandler.getArchipelagoMap()), remoteView);
            sendMessageToView(new BoardMessage(controller.getMatch().getGame().getWizardFromPlayer(activePlayer).getBoard()), remoteView);
        } catch (ExceptionGame e) {
            e.printStackTrace();
        }

    }
    private void askingViewToChooseCloud(){
        if(controller.getMatch().getGame().getStudentBag().getStudentsInBag().isEmpty()){
            setTurnPhase(TurnPhase.MOVE_STUDENTS);
            nextPlayerActionPhase();
            return;
        }
        try{
            RemoteView remoteView = (RemoteView) viewMap.get(activePlayer.getUsername());
            remoteView.showGenericMessage(new GenericMessage("\n It's your turn, choose a Cloud!!"));
            messageHandler.setCloudMap(controller.getMatch().getGame().getClouds().stream().toList());
            messageHandler.setArchipelagoMap(controller.getMatch().getGame().getArchipelagos());
            sendMessageToView(new ArchipelagoInGameMessage(messageHandler.getArchipelagoMap()), remoteView);
            sendMessageToView(new BoardMessage(controller.getMatch().getGame().getWizardFromPlayer(activePlayer).getBoard()), remoteView);
            sendMessageToView(new CloudInGame(messageHandler.getCloudMap()), remoteView);
        } catch (ExceptionGame e) {
            e.printStackTrace();
        }

    }

    public TurnPhase getTurnPhase() {
        return turnPhase;
    }

    private void sendMessageToView(Message message, RemoteView remoteView){
        if(controller.isMatchOnGoing()){
            remoteView.sendMessage(message);
        }
    }
}
