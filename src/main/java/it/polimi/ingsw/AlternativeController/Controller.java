package it.polimi.ingsw.AlternativeController;

import it.polimi.ingsw.AlternativeView.RemoteView;
import it.polimi.ingsw.AlternativeView.ViewInterface;
import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
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
        setMatchHandler();
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
            RemoteView remoteView = (RemoteView) viewMap.get(player.getUsername());
            remoteView.showGenericMessage("It's your turn, pick an assistant card");
            try {
                remoteView.askAssistantCard(getMatch().getGame().getWizardFromPlayer(player).getAssistantsDeck().getPlayableAssistants());
            } catch (ExceptionGame e) {
                e.printStackTrace();
            }
        }


    public void onMessageReceived(Message receivedMessage) throws ExceptionGame {

        switch (gameState){
            case PLANNING_PHASE:
                planningPhaseHandling(receivedMessage);
            case ACTION_PHASE:
                ;
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


    private void setMatchHandler() throws ExceptionGame {
        //if(match.getNumberOfPlayers() == 2 || match.getNumberOfPlayers() == 3){
            //notifyObserver(new MatchInfoMessage("server", match.getPlayers(),match.getGame().getArchipelagos(), GameStateMessage.MATCH_INFO));

       // }
    }

    public void receivedMessage(Message receivedMessage) throws ExceptionGame {

        //RemoteView view = (RemoteView) viewMap.get(receivedMessage.getUsername());
        //switch ((GameState) receivedMessage.getGamePhase()) {

          //  case GameState.PLANNING_PHASE -> planningPhaseHandling(receivedMessage, view);
           // case GameState.ACTION_PHASE -> actionPhaseHandling(receivedMessage, view);
           // default -> {
           // } // Should never reach this condition
       // }
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
                    turnController.setActionOrderOfPlayers(match.getActionPhaseOrderOfPlayers());
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


    private void actionPhaseHandling(Message receivedMessage, RemoteView actualView) throws ExceptionGame {
        switch (receivedMessage.getType()) {
            case STUDENT_ON_BOARD:
                match.moveStudentOnBoard(receivedMessage.getPlayer(), (Student) receivedMessage.getContentOne());
                //notifyObserver(receivedMessage);
            case ATTACK:
                break;
            case END_OF_TURN:
                break;
            case STUDENT_IN_ARCHIPELAGO:
                match.moveStudentOnArchipelago(receivedMessage.getPlayer(), (Student) receivedMessage.getContentOne(), (Archipelago) receivedMessage.getContentTwo());
                //notifyObserver(if((Archipelago) receivedMessage.getContentTwo().)){

            ;//find a way to understad if influence in archipelago changed
           // case MOVE_MOTHER_NATURE -> match.moveMotherNature(receivedMessage.getPlayer(), match.getGame().getArchipelagos().get((Integer) receivedMessage.getContentOne()));
            case GENERIC_MESSAGE:
                break;
            case ASK_NUM_OF_PLAYERS:
                break;
            case MOVED_PROFESSOR:
                break;
            case TOWER_BUILT:
                break;
            case ERROR:
                break;
            case TEAM:
                break;
            case MATCH_INFO:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + receivedMessage.getType());
        }

    }

    public void addView(String username, ViewInterface view){
        viewMap.put(username, view);
    }
    @Override
    public void update(Object message) throws ExceptionGame {

    }
}
