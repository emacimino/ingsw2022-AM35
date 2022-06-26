package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.Model.Wizard.TableOfStudents;
import it.polimi.ingsw.Model.Wizard.Wizard;
import it.polimi.ingsw.NetworkUtilities.*;
import it.polimi.ingsw.View.RemoteView;
import it.polimi.ingsw.View.ViewInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Handle the turnPhase in the server
 */
public class TurnController {
    private final MessageHandler messageHandler = new MessageHandler();
    private final Controller controller;
    private final Map<String, ViewInterface> viewMap;
    private Player activePlayer;
    private final List<Player> actionOrderOfPlayers = new ArrayList<>();
    private TurnPhase turnPhase = null;
    private int numberOfStudentMoved = 0;


    /**
     * Constructor of the class
     * @param controller controller associated
     * @param viewMap view of the players associated
     */
    public TurnController(Controller controller, Map<String, ViewInterface> viewMap){
        this.controller = controller;
        this.viewMap = viewMap;
    }

    /**
     * calculate the next player in planning phase
     */
    public void nextPlayerPlanningPhase(){
        int indexNewActivePlayer = (controller.getMatch().getPlayers().indexOf(activePlayer) + 1) % controller.getMatch().getNumberOfPlayers();
        setActivePlayer(controller.getMatch().getPlayers().get(indexNewActivePlayer));
    }

    /**
     * calculate the next player in action phase
     */
    public void nextPlayerActionPhase(){
        if(controller.getMatch().getActionPhaseOrderOfPlayers().isEmpty()){
            setTurnPhase(TurnPhase.PLAY_ASSISTANT);
            controller.setGameState(GameState.PLANNING_PHASE);
            setActivePlayer(actionOrderOfPlayers.get(0));
        }else {
            setActivePlayer(actionOrderOfPlayers.get(actionOrderOfPlayers.indexOf(activePlayer)+1));
        }

    }

    /**
     * pick the first player to play a match
     */
    protected void pickFirstPlayerPlanningPhaseHandler() {
        Random r = new Random();
        Player player = controller.getMatch().getPlayers().get(r.nextInt(0, controller.getMatch().getNumberOfPlayers()));
        setTurnPhase(TurnPhase.PLAY_ASSISTANT);
        setActivePlayer(player);
    }

    /**
     * handle the planning phase
     * @param receivedMessage message to handle
     */
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
    /**
     * pick the first player to play in action phase
     */
    protected void pickFirstPlayerActionPhaseHandler() {
        setActionOrderOfPlayers(controller.getMatch().getActionPhaseOrderOfPlayers());
        setTurnPhase(TurnPhase.MOVE_STUDENTS);
    }
    /**
     * handle the action phase
     * @param receivedMessage message to handle
     */
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
            case ASK_CHARACTER_CARD -> {
                AskCharacterCardMessage message = (AskCharacterCardMessage) receivedMessage;
                try {
                    sendCharacterCardInfo(message);
                } catch (ExceptionGame e) {
                    e.printStackTrace();
                    RemoteView remoteView = (RemoteView) viewMap.get(activePlayer.getUsername());
                    sendMessageToView(new ErrorMessage(e.getMessage()),remoteView);
                }
            }
            case PLAY_CHARACTER_CARD -> {
                PlayCharacterMessage message = (PlayCharacterMessage) receivedMessage;
                playCharacterCardForThisTurn(message);
            }

            default ->
                    throw new IllegalStateException("Unexpected value: " + receivedMessage.getType());

        }
    }

    /**
     * Play an assistant card
     * @param activePlayer the player that's playing
     * @param assistantsCard assistant card played
     * @return if the assistant card could be played
     */
    private boolean playAssistantCard(Player activePlayer, AssistantsCards assistantsCard) {
        try {
            controller.getMatch().playAssistantsCard(activePlayer, assistantsCard);
            return true;

        } catch (ExceptionGame e) {
            ViewInterface view = viewMap.get(activePlayer.getUsername());
            sendMessageToView(new ErrorMessage(e.getMessage()), (RemoteView) view);
            askingViewToPlayAnAssistantCard();
            return false;
        }

    }

    /**
     * Move motherNature
     * @param message indicate where to put motherNature
     */
    private void MoveMotherNatureForThisTurn(MoveMotherNatureMessage message) {
        Integer indexArch = message.getArchipelago();
        try {
            controller.getMatch().moveMotherNature(getActivePlayer(), messageHandler.getArchipelagoMap().get(indexArch));
            setTurnPhase(TurnPhase.CHOOSE_CLOUD);
            askNextAction();
        } catch (ExceptionGame exceptionGame) {
            exceptionGame.printStackTrace();
            sendMessageToView(new ErrorMessage("Can't move MotherNature in this position"), (RemoteView)viewMap.get(getActivePlayer().getUsername()) );

        }
    }
    /**
     * Select a cloud
     * @param message indicate which cloud to pick
     */
    private void selectCloudForThisTurn(CloudMessage message) {
        try {
            controller.getMatch().chooseCloud(getActivePlayer(), messageHandler.getCloudMap().get(message.getCloud()));

            setTurnPhase(TurnPhase.MOVE_STUDENTS);
            nextPlayerActionPhase();

        } catch (ExceptionGame e) {
            e.printStackTrace();
            sendMessageToView(new ErrorMessage(e.getMessage()), (RemoteView)viewMap.get(getActivePlayer().getUsername()) );
        }


    }
    /**
     * Move student
     * @param message indicate where to move a student
     */
    private void moveStudentsForThisTurn(MoveStudentMessage message) {
        Integer indexStud = message.getStudent();
        Integer indexArch = message.getArchipelago();
        try {
            Student s = messageHandler.getStudentsOnEntranceMap().get(indexStud);
            if (message.getArchipelago() != null) {
                Archipelago a = messageHandler.getArchipelagoMap().get(indexArch);
                controller.getMatch().moveStudentOnArchipelago(getActivePlayer(), s, a);
            } else {
                controller.getMatch().moveStudentOnBoard(getActivePlayer(), s);
            }
            numberOfStudentMoved ++;
            if (numberOfStudentMoved == controller.getMatch().getNumberOfMovableStudents()) {
                numberOfStudentMoved = 0;
                setTurnPhase(TurnPhase.MOVE_MOTHER_NATURE);
                askNextAction();
            }else
                askingViewToMoveAStudent();

        } catch (ExceptionGame exceptionGame) {
            exceptionGame.printStackTrace();
            sendMessageToView(new ErrorMessage("Can't move more students from board"), (RemoteView)viewMap.get(getActivePlayer().getUsername()) );
            askingViewToMoveAStudent();
        }
    }

    /**
     * Setter for the action phase order of playing
     * @param actionOrderOfPlayers list of waiting for turn
     */
    public void setActionOrderOfPlayers(List<Player> actionOrderOfPlayers) {
        this.actionOrderOfPlayers.clear();
        this.actionOrderOfPlayers.addAll(actionOrderOfPlayers);
        setTurnPhase(TurnPhase.MOVE_STUDENTS);
        setActivePlayer(actionOrderOfPlayers.get(0));
    }

    /**
     * setter for turnPhase
     * @param turnPhase turnPhase to set
     */
    public void setTurnPhase(TurnPhase turnPhase) {
        this.turnPhase = turnPhase;
    }

    /**
     * Handle the next action by turnPhase
     */
    private void askNextAction() {
        switch (turnPhase) {
            case PLAY_ASSISTANT -> askingViewToPlayAnAssistantCard();
            case MOVE_STUDENTS -> askingViewToMoveAStudent();
            case MOVE_MOTHER_NATURE -> askingViewToMoveMotherNature();
            case CHOOSE_CLOUD -> askingViewToChooseCloud();
        }
    }

    /**
     * Choose the player in turn
     * @param player activePlayer for this turn
     */
    public void setActivePlayer(Player player) {
        activePlayer = player;
        RemoteView remoteView = (RemoteView) viewMap.get(player.getUsername());
        sendMessageToView(new YourTurnMessage(), remoteView);
        askNextAction();
        for(String c : viewMap.keySet()){
            if(!c.equals(player.getUsername()))
                sendMessageToView(new EndTurnMessage(), (RemoteView) viewMap.get(c));

        }
    }

    /**
     * Getter for the active Player
     * @return the player in game
     */
    public Player getActivePlayer() {
        return activePlayer;
    }

    /**
     * Ask the view to move a student
     */
    private void askingViewToMoveAStudent() {
        RemoteView remoteView = (RemoteView) viewMap.get(activePlayer.getUsername());
        try {
            messageHandler.setStudentOnEntranceMap(controller.getMatch().getGame().getWizardFromPlayer(activePlayer).getBoard().getStudentsInEntrance().stream().toList());
            messageHandler.setArchipelagoMap(controller.getMatch().getGame().getArchipelagos());
            sendMessageToView(new ArchipelagoInGameMessage(messageHandler.getArchipelagoMap()), remoteView);
            sendMessageToView(new BoardMessage(controller.getMatch().getGame().getWizardFromPlayer(activePlayer).getBoard()), remoteView);
            sendMessageToView(new StudentsOnEntranceMessage(messageHandler.getStudentsOnEntranceMap()), remoteView);
            sendMessageToView(new AskToMoveStudents(), remoteView);
        } catch (ExceptionGame e) {
            e.printStackTrace();
        }
    }

    /**
     * Ask the view to play an assistant card
     */
    private void askingViewToPlayAnAssistantCard() {
        RemoteView remoteView = (RemoteView) viewMap.get(activePlayer.getUsername());
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

    /**
     * Ask the view to move motherNature
     */
    private void askingViewToMoveMotherNature(){
        try{
            RemoteView remoteView = (RemoteView) viewMap.get(activePlayer.getUsername());
            messageHandler.setArchipelagoMap(controller.getMatch().getGame().getArchipelagos());
            sendMessageToView(new ArchipelagoInGameMessage(messageHandler.getArchipelagoMap()), remoteView);
            sendMessageToView(new BoardMessage(controller.getMatch().getGame().getWizardFromPlayer(activePlayer).getBoard()), remoteView);
            sendMessageToView(new StudentsOnEntranceMessage(messageHandler.getStudentsOnEntranceMap()), remoteView);
            sendMessageToView(new AskToMoveMotherNatureMessage(controller.getMatch().getGame().getWizardFromPlayer(activePlayer).getRoundAssistantsCard().getStep()), remoteView);
        } catch (ExceptionGame e) {
            e.printStackTrace();
        }

    }
    /**
     * Ask the view to pick a cloud
     */
    private void askingViewToChooseCloud(){
        if(controller.getMatch().getGame().getStudentBag().getStudentsInBag().isEmpty()){
            setTurnPhase(TurnPhase.MOVE_STUDENTS);
            nextPlayerActionPhase();
            return;
        }
            RemoteView remoteView = (RemoteView) viewMap.get(activePlayer.getUsername());

            messageHandler.setCloudMap(controller.getMatch().getGame().getClouds().stream().toList());
            messageHandler.setArchipelagoMap(controller.getMatch().getGame().getArchipelagos());
            sendMessageToView(new CloudInGame(messageHandler.getCloudMap()), remoteView);


    }

    /**
     * Send info for the chosen characterCard
     * @param message characterCard asked to be played
     * @throws ExceptionGame if the CharacterCard could not be played
     */
    private void sendCharacterCardInfo(AskCharacterCardMessage message) throws ExceptionGame {
        ExpertMatch match = ((ExpertMatch)this.controller.getMatch());
        messageHandler.setCharacterCardMap(match.getCharactersForThisGame());
        int wizardCoins = match.getGame().getWizardFromPlayer(activePlayer).getCoins();
        int cardCost = messageHandler.getCharacterCardMap().get(message.getCharacterCardName()).getCost();
        RemoteView remoteView = (RemoteView) viewMap.get(activePlayer.getUsername());
        if( wizardCoins >= cardCost) {
            messageHandler.setStudentOnCardMap(match.getCharactersForThisGame().get(message.getCharacterCardName()).getStudentsOnCard());
            messageHandler.setStudentOnEntranceMap(match.getGame().getWizardFromPlayer(activePlayer).getBoard().getStudentsInEntrance().stream().toList());
            messageHandler.setArchipelagoMap(match.getGame().getArchipelagos());
            sendMessageToView(new BoardMessage(controller.getMatch().getGame().getWizardFromPlayer(activePlayer).getBoard()), remoteView);
            sendMessageToView(new CharacterCardInfo(message.getCharacterCardName(), messageHandler.getStudentsOnCardMap(), messageHandler.getStudentsOnEntranceMap(), messageHandler.getArchipelagoMap()), remoteView);
        }else {
            sendMessageToView(new ErrorMessage("you don't have enough coins!"), remoteView);
            askNextAction();
        }
    }


    /**
     * Handle the phase of the match when the controller use a characterCard on the model
     * @param message message with the info to use a characterCard
     */
    private void playCharacterCardForThisTurn(PlayCharacterMessage message){
        String cardName = message.getNameCharacterCard();
        ExpertMatch expertMatch = (ExpertMatch)controller.getMatch();
        RemoteView remoteView = (RemoteView) viewMap.get(activePlayer.getUsername());
        if(expertMatch.getCharactersForThisGame().containsKey(cardName)){
            try {
                handleCardSettings(expertMatch.getCharactersForThisGame().get(cardName),message);
            } catch (ExceptionGame e) {
                e.printStackTrace();
                sendMessageToView(new ErrorMessage(e.getMessage()),remoteView);
                askNextAction();
            }
        }
        try {
            expertMatch.getCharactersForThisGame().get(cardName).useCard(expertMatch);
            sendMessageToView(new ArchipelagoInGameMessage(messageHandler.getArchipelagoMap()), remoteView);
            sendMessageToView(new BoardMessage(controller.getMatch().getGame().getWizardFromPlayer(activePlayer).getBoard()), remoteView);
            sendMessageToView(new CharacterCardInGameMessage(((ExpertMatch)controller.getMatch()).getCharactersForThisGame()), remoteView);
            askNextAction();
        } catch (ExceptionGame e) {
            e.printStackTrace();
            sendMessageToView(new ErrorMessage(e.getMessage()), remoteView);
            askNextAction();
        }


    }

    /**
     * Set the CharacterCard to be played
     * @param card card to be played
     * @param message message with the info to set the card
     * @throws ExceptionGame if the card could not be set
     */
    private void handleCardSettings(CharacterCard card, PlayCharacterMessage message) throws ExceptionGame {
        ExpertMatch match = ((ExpertMatch)this.controller.getMatch());
        Wizard activeWizard = match.getGame().getWizardFromPlayer(activePlayer);
        card.setActiveWizard(activeWizard);
        switch (card.getName()) {
            case "Archer","Knight","Baker", "Magician" ->{
                //do nothing
            }
            case "Messenger",  "Herbalist" -> card.setArchipelagoEffected(messageHandler.getArchipelagoMap().get(message.getIndexOfArchipelago()));
            case "Princess" -> {
                List<Student> activeStudent = new ArrayList<>();
                for (Integer integer: message.getToTradeFromCard()) {
                    activeStudent.add(messageHandler.getStudentsOnCardMap().get(integer));
                }
                card.setActiveStudents(activeStudent);
            }
            case "Jester" -> {
                List<Student> activeStudent = new ArrayList<>();
                List<Student> passiveStudent = new ArrayList<>();
                for (Integer integer: message.getToTradeFromCard()) {
                    activeStudent.add(messageHandler.getStudentsOnCardMap().get(integer));
                }
                for (Integer integer: message.getToTradeFromEntrance()) {
                    passiveStudent.add(messageHandler.getStudentsOnEntranceMap().get(integer));
                }
                card.setActiveStudents(activeStudent);
                card.setPassiveStudents(passiveStudent);
            }
            case "Friar" -> {
                List<Student> activeStudent = new ArrayList<>();
                for (Integer integer: message.getToTradeFromCard()) {
                    activeStudent.add(messageHandler.getStudentsOnCardMap().get(integer));
                }
                card.setActiveStudents(activeStudent);
                card.setArchipelagoEffected(messageHandler.getArchipelagoMap().get(message.getIndexOfArchipelago()));
            }
            case "Minstrel" -> {
                List<Student> activeStudent = new ArrayList<>();
                for(Color c : message.getColors()) {
                    for (TableOfStudents t : activeWizard.getBoard().getTables()) {
                        if (t.getColor().equals(c) && !t.getStudentsInTable().isEmpty()) {
                            activeStudent.add(t.getStudentsInTable().stream().filter(s -> !activeStudent.contains(s)).findAny().get());
                        }
                    }
                }
                List<Student> passiveStudent = new ArrayList<>();
                for (Integer integer: message.getToTradeFromEntrance()) {
                    passiveStudent.add(messageHandler.getStudentsOnEntranceMap().get(integer));

                }
                card.setActiveStudents(activeStudent);
                card.setPassiveStudents(passiveStudent);
            }
            case "Chef", "Banker" ->{
                Color color = message.getColors().get(0);
                card.setColorEffected(color);
            }

            default -> throw new IllegalStateException("Unexpected value: " + card.getName());
        }
    }


    /**
     * Send a message to the view after an event
     * @param message the event
     * @param remoteView the view chosen
     */
    private void sendMessageToView(Message message, RemoteView remoteView){
        if(controller.isMatchOnGoing()){
            remoteView.sendMessage(message);
        }
    }

    public TurnPhase getTurnPhase(){
        return this.turnPhase;
    }
}
