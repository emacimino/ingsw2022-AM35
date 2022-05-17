package it.polimi.ingsw.AlternativeController;

import it.polimi.ingsw.AlternativeView.RemoteView;
import it.polimi.ingsw.AlternativeView.ViewInterface;
import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.NetworkUtilities.Message.*;
import it.polimi.ingsw.Observer.Observer;

import java.util.*;

public class Controller implements Observer {
    private final BasicMatch match;
    private GameState gameState;
    private Collection<String> playersUsername;
    private Map<String,ViewInterface> viewMap = new HashMap<>();
    private TurnController turnController;
    private Player firstPlanningPhasePlayer;

    //Initialize the Game having already a lobby
    public Controller(BasicMatch match, Set<String> playersUsername) throws ExceptionGame, CloneNotSupportedException {
        super();
        this.playersUsername = playersUsername;
        this.match = match;
        initGame();
    }

    public BasicMatch getMatch() {
        return match;
    }

    private void initGame() throws ExceptionGame, CloneNotSupportedException {
        List<Player> players = setListOfPlayers(this.playersUsername);
        if(match.getNumberOfPlayers() == 4){
            match.setTeamsOne(players.get(0), players.get(1));
            match.setTeamsOne(players.get(2), players.get(3));
        }
        this.match.setGame(players);
        gameState = GameState.PLANNING_PHASE;
        match.infoMatch();
        turnController = new TurnController(this, viewMap);
        Random r = new Random();
        firstPlanningPhasePlayer = this.getMatch().getPlayers().get(r.nextInt(0, this.getMatch().getNumberOfPlayers()));
        pickFirstPlayerPlanningPhaseHandler(firstPlanningPhasePlayer);

    }
        public void pickFirstPlayerPlanningPhaseHandler(Player player){
            turnController.setActivePlayer(player);
            turnController.setTurnPhase(TurnPhase.PLAY_ASSISTANT);
        }


    public void onMessageReceived(Message receivedMessage) throws ExceptionGame {

        switch (gameState){
            case PLANNING_PHASE:
                planningPhaseHandling(receivedMessage);
            case ACTION_PHASE:
                actionPhaseHandling(receivedMessage);
            case END_OF_TURN:
                ;
            default: //should never reach this condition
                //Server.Logger.warning(STR_INVALID_STATE);
                break;
        }
    }

    private List<Player> setListOfPlayers(Collection<String> playersUsername) {
        List<Player> players = new ArrayList<>();
        for (String username: playersUsername){
            players.add(new Player(username));
        }
        return players;
    }


    private synchronized void planningPhaseHandling(Message receivedMessage) {
        Player activePlayer = turnController.getActivePlayer();
        if (receivedMessage.getType() == GameStateMessage.ASSISTANT_CARD) {
           AssistantsCards assistantsCard= ((AssistantCardMessage) receivedMessage).getAssistantsCard();
           try{
               match.playAssistantsCard(activePlayer, assistantsCard);
               turnController.nextPlayerPlanningPhase();
               if(match.getActionPhaseOrderOfPlayers().size() == viewMap.size()){
                   this.gameState = GameState.ACTION_PHASE;
                    pickFirstPlayerActionPhaseHandler();
               }
           }catch(ExceptionGame e){
               ViewInterface view = viewMap.get(activePlayer.getUsername());
               view.showGenericMessage("Please, insert a valid Assistant card");
               try {
                   List<AssistantsCards> availableAssistantsCards = match.getGame().getWizardFromPlayer(activePlayer).getAssistantsDeck().getPlayableAssistants();
                   view.askAssistantCard(availableAssistantsCards);
               }catch (ExceptionGame er){
                   view.showGenericMessage(er.getMessage());

               }
           }

        }
    }

    private void pickFirstPlayerActionPhaseHandler(){
        turnController.setActionOrderOfPlayers(match.getActionPhaseOrderOfPlayers());
        turnController.setTurnPhase(TurnPhase.MOVE_STUDENTS);

    }

    private void actionPhaseHandling(Message receivedMessage) {

        switch (receivedMessage.getType()) {
            case MOVE_STUDENT: {
                MoveStudentMessage message = (MoveStudentMessage) receivedMessage;
                try {
                    if (message.getArchipelago() != null) {
                        match.moveStudentOnArchipelago(turnController.getActivePlayer(), message.getStudent(), message.getArchipelago());
                    } else {
                        match.moveStudentOnBoard(turnController.getActivePlayer(), message.getStudent());
                    }
                    if (message.getNumberOfStudentMoved() == 3) {
                        turnController.setTurnPhase(TurnPhase.MOVE_MOTHERNATURE);
                    }

                } catch (ExceptionGame exceptionGame) {
                    exceptionGame.printStackTrace();
                    viewMap.get(turnController.getActivePlayer().getUsername()).sendMessage(new ErrorMessage("Can't move more students from board"));
                }
                break;
            }
            case MOVE_MOTHER_NATURE:

                break;
            case CHOOSE_CLOUD: {
                try {
                    match.chooseCloud(turnController.getActivePlayer(), ((CloudMessage)receivedMessage).getCloud());
                } catch (ExceptionGame e) {
                    e.printStackTrace();
                    viewMap.get(turnController.getActivePlayer().getUsername()).sendMessage(new ErrorMessage("Can't select this cloud"));
                }
                turnController.setTurnPhase(TurnPhase.END_TURN);
                turnController.nextPlayerActionPhase();
                break;
            case MOVE_MOTHER_NATURE:
                try {
                    match.moveMotherNature(turnController.getActivePlayer(), ((MoveMotherNature) receivedMessage).getArchipelago());
                }catch (ExceptionGame exceptionGame){
                    viewMap.get(turnController.getActivePlayer().getUsername()).sendMessage(new ErrorMessage("Can't move MotherNature in this position"));
                }
                break;
            }

            //find a way to understand if influence in archipelago changed
           // case MOVE_MOTHER_NATURE -> match.moveMotherNature(receivedMessage.getPlayer(), match.getGame().getArchipelagos().get((Integer) receivedMessage.getContentOne()));
            default: {
                throw new IllegalStateException("Unexpected value: " + receivedMessage.getType());
            }
        }

    }

    public void addView(String username, ViewInterface view){
        viewMap.put(username, view);
    }
    @Override
    public void update(Object message)  {
        onMessageReceived((Message) message);
    }
}
