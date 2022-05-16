package it.polimi.ingsw.AlternativeController;

import it.polimi.ingsw.AlternativeView.RemoteView;
import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.FactoryMatch;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.NetworkUtilities.Message.*;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;

import java.util.*;

public class Controller implements Observer {
    private BasicMatch match;
    private int playerInAction = 1;
    private GameState gameState = GameState.GAME_STARTED;
    private Observable matchObserver;
    private Collection<String> playersUsername;

    //Initialize the Game having already a lobby
    public Controller(BasicMatch match, Set<String> playersUsername) throws ExceptionGame {
        this.playersUsername = playersUsername;
        this.match = match;
        handleGame();
        setMatchHandler();
    }

    private void handleGame() throws ExceptionGame {
        List<Player> players = setListOfPlayers(this.playersUsername);
        if(match.getNumberOfPlayers() == 4){
            match.setTeamsOne(players.get(0), players.get(1));
            match.setTeamsOne(players.get(2), players.get(3));
        }
        this.match.setGame(players);
        this.match.getGame().setRandomlyFirstPlayer();
        update( new MatchInfoMessage("server",this.match.getPlayers(),this.match.getGame().getArchipelagos(),GameStateMessage.MATCH_INFO));
        }


if(match.getNumberOfPlayers() == 4){
        notifyObserver(new MatchInfoMessage("server", match.getPlayers(),match.getGame().getArchipelagos(), GameStateMessage.MATCH_INFO));
        notifyObserver(new MatchFourPlayersInfoMessage("server", match.getTeams(), GameStateMessage.MATCH_INFO));
        for(Player player: match.getPlayers()){
        notifyObserver(new TeamCaptainForEveryPlayerInfo("server",match.getCaptainTeamOfPlayer(player),GameStateMessage.MATCH_INFO));
        }
private List<Player> setListOfPlayers(Collection<String> playersUsername) {
        List<Player> players = new ArrayList<>();
        for (String username: playersUsername){
            players.add(new Player(username));
        }
        return players;
    }


    private void setMatchHandler() throws ExceptionGame {
        if(match.getNumberOfPlayers() == 2 || match.getNumberOfPlayers() == 3){
            notifyObserver(new MatchInfoMessage("server", match.getPlayers(),match.getGame().getArchipelagos(), GameStateMessage.MATCH_INFO));

        }
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


    private void planningPhaseHandling(Message receivedMessage, RemoteView actualView) throws ExceptionGame {
        if (receivedMessage.getType() == GameStateMessage.ASSISTANT_CARD) {
            assistantCardsHandling(receivedMessage);
            //notifyObserver(new ActionPhaseTurnMessage(match.getActionPhaseOrderOfPlayers()));
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

    private void actionPhaseHandling(Message receivedMessage, RemoteView actualView) throws ExceptionGame {
        switch (receivedMessage.getType()) {
            case STUDENT_ON_BOARD:
                match.moveStudentOnBoard(receivedMessage.getPlayer(), (Student) receivedMessage.getContentOne());
                notifyObserver(receivedMessage);
            case PING:
                break;
            case PONG:
                break;
            case LOGIN_REPLY:
                break;
            case LOGIN_REQUEST:
                break;
            case PARTICIPATE_TO_A_MATCH:
                break;
            case CREATE_A_MATCH:
                break;
            case NUMBER_OF_PLAYERS:
                break;
            case PLANNING:
                break;
            case ATTACK:
                break;
            case END_OF_TURN:
                break;
            case ASSISTANT_CARD:
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

    @Override
    public void update(Object message) throws ExceptionGame {

    }
}
