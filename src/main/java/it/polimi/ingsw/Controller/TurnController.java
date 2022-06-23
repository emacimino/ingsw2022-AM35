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

public class TurnController {
    private final MessageHandler messageHandler = new MessageHandler();
    private final Controller controller;
    private final Map<String, ViewInterface> viewMap;
    private Player activePlayer;
    private final List<Player> actionOrderOfPlayers = new ArrayList<>();
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
            sendMessageToView(new ErrorMessage("Can't move MotherNature in this position"), (RemoteView)viewMap.get(getActivePlayer().getUsername()) );

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
                askingViewToMoveAStudent(numberOfStudentMoved);

        } catch (ExceptionGame exceptionGame) {
            exceptionGame.printStackTrace();
            sendMessageToView(new GenericMessage("Can't move more students from board"), (RemoteView)viewMap.get(getActivePlayer().getUsername()) );
            askingViewToMoveAStudent(numberOfStudentMoved);
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
            case MOVE_MOTHER_NATURE -> askingViewToMoveMotherNature();
            case CHOOSE_CLOUD -> askingViewToChooseCloud();
        }
    }

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
    public Player getActivePlayer() {
        return activePlayer;
    }
    private void askingViewToMoveAStudent(int numberOfStudentMoved) {
        RemoteView remoteView = (RemoteView) viewMap.get(activePlayer.getUsername());
        sendMessageToView(new GenericMessage("It's your turn, move " + (controller.getMatch().getNumberOfMovableStudents() - numberOfStudentMoved) + " students from your board"), remoteView);
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
    private void askingViewToMoveMotherNature(){
        try{
            RemoteView remoteView = (RemoteView) viewMap.get(activePlayer.getUsername());
            sendMessageToView(new GenericMessage("\nIt's your turn, move Mother Nature!!"), remoteView);
            messageHandler.setArchipelagoMap(controller.getMatch().getGame().getArchipelagos());

            sendMessageToView(new ArchipelagoInGameMessage(messageHandler.getArchipelagoMap()), remoteView);
            sendMessageToView(new BoardMessage(controller.getMatch().getGame().getWizardFromPlayer(activePlayer).getBoard()), remoteView);
            sendMessageToView(new AskToMoveMotherNatureMessage(controller.getMatch().getGame().getWizardFromPlayer(activePlayer).getRoundAssistantsCard().getStep()), remoteView);
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
       /* try{*/
            RemoteView remoteView = (RemoteView) viewMap.get(activePlayer.getUsername());
     //       sendMessageToView(new GenericMessage("\n It's your turn, choose a Cloud!!"), remoteView);

            messageHandler.setCloudMap(controller.getMatch().getGame().getClouds().stream().toList());
            messageHandler.setArchipelagoMap(controller.getMatch().getGame().getArchipelagos());
           // sendMessageToView(new ArchipelagoInGameMessage(messageHandler.getArchipelagoMap()), remoteView);
           // sendMessageToView(new BoardMessage(controller.getMatch().getGame().getWizardFromPlayer(activePlayer).getBoard()), remoteView);
            sendMessageToView(new CloudInGame(messageHandler.getCloudMap()), remoteView);
      /*  } catch (ExceptionGame e) {
            e.printStackTrace();
        }*/

    }

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
            sendMessageToView(new GenericMessage("\n It's your turn, write what needed for your CharacterCard!!"), remoteView);
        }else {
            sendMessageToView(new ErrorMessage("you don't have enough coins!"), remoteView);
            askNextAction();
        }
    }

    private void playCharacterCardForThisTurn(PlayCharacterMessage message){
        String cardName = message.getNameCharacterCard();
        RemoteView remoteView = (RemoteView) viewMap.get(activePlayer.getUsername());
        if(((ExpertMatch)controller.getMatch()).getCharactersForThisGame().containsKey(cardName)){
            try {
                handleCardSettings(((ExpertMatch)controller.getMatch()).getCharactersForThisGame().get(cardName),message);
            } catch (ExceptionGame e) {
                e.printStackTrace();
                sendMessageToView(new ErrorMessage(e.getMessage()),remoteView);
            }
        }

        try {
            ((ExpertMatch)controller.getMatch()).getCharactersForThisGame().get(cardName).useCard((ExpertMatch) controller.getMatch());
            sendMessageToView(new CharacterCardInGameMessage(((ExpertMatch)controller.getMatch()).getCharactersForThisGame()), remoteView);
            askNextAction();
        } catch (ExceptionGame e) {
            e.printStackTrace();
            sendMessageToView(new ErrorMessage(e.getMessage()), remoteView);
            askNextAction();
        }


    }

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


    private void sendMessageToView(Message message, RemoteView remoteView){
        if(controller.isMatchOnGoing()){
            remoteView.sendMessage(message);
        }
    }
}
