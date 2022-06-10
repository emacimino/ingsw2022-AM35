package it.polimi.ingsw.ControllerTest;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Controller.GameState;
import it.polimi.ingsw.Controller.TurnController;
import it.polimi.ingsw.Controller.TurnPhase;
import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.FactoryMatch;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Cloud;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.NetworkUtilities.Message.*;
import it.polimi.ingsw.Server.Server;
import it.polimi.ingsw.Server.SocketClientConnection;
import it.polimi.ingsw.View.RemoteView;
import it.polimi.ingsw.View.ViewInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class ControllerTest {

    private Set<String> usernameBasicMatch2Players;
    private Set<String> usernameBasicMatch4Players;
    private Controller controllerBasicMatch2Players;
    private Controller controllerBasicMatch4Players;
    private TurnController turnControllerBasicMatch2Players;
    private final FactoryMatch factoryMatch = new FactoryMatch();
    private final BasicMatch basicMatch2Players = factoryMatch.newMatch(2);
    private final FactoryMatch factoryMatch4 = new FactoryMatch();
    private final BasicMatch basicMatch4Players = factoryMatch.newMatch(4);
    private final Player playerOne = new Player("One");
    private final Player playerTwo = new Player("Two");
    private final Player playerThree = new Player("Three");
    private final Player playerFour = new Player("Four");
    private  ViewInterface view1;
    private SocketClientConnection clientConnection1;
    private  ViewInterface view2;
    private  SocketClientConnection clientConnection2;
    private  ViewInterface view3;
    private  SocketClientConnection clientConnection3;
    private  ViewInterface view4;
    private  SocketClientConnection clientConnection4;



    private void setControllerInTest(){
        setListsOfPlayers(); //aggiunge al set di stringhe gli username dei players
        Assertions.assertDoesNotThrow(()->{
            view1 = new RemoteView(this.clientConnection1);
            view2 = new RemoteView(this.clientConnection2);
        });
        if(basicMatch2Players.getNumberOfPlayers() == 2) {
            try {
                controllerBasicMatch2Players = new Controller(basicMatch2Players, usernameBasicMatch2Players);
                controllerBasicMatch2Players.setMatchOnGoing(false);
                controllerBasicMatch2Players.addView(playerOne.getUsername(), view1);
                controllerBasicMatch2Players.addView(playerTwo.getUsername(), view2);
                controllerBasicMatch2Players.initGame();
            } catch (ExceptionGame | CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

    }


    private void setControllerInTest4players(){
        setListsOfPlayers4(); //aggiunge al set di stringhe gli username dei players
        Assertions.assertDoesNotThrow(()->{
            view1 = new RemoteView(this.clientConnection1);
            view2 = new RemoteView(this.clientConnection2);
            view3 = new RemoteView(this.clientConnection3);
            view4 =new RemoteView(this.clientConnection4);
        });
        if(basicMatch4Players.getNumberOfPlayers() == 4) {
            try {
                controllerBasicMatch4Players = new Controller(basicMatch4Players, usernameBasicMatch4Players);
                controllerBasicMatch4Players.setMatchOnGoing(false);
                controllerBasicMatch4Players.addView(playerOne.getUsername(), view1);
                controllerBasicMatch4Players.addView(playerTwo.getUsername(), view2);
                controllerBasicMatch4Players.addView(playerFour.getUsername(), view3);
                controllerBasicMatch4Players.addView(playerFour.getUsername(), view4);
                controllerBasicMatch4Players.initGame();
            } catch (ExceptionGame | CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

    }



    private void setListsOfPlayers(){
        usernameBasicMatch2Players = new HashSet<>();
        usernameBasicMatch2Players.add(playerOne.getUsername());
        usernameBasicMatch2Players.add(playerTwo.getUsername());
    }

    private void setListsOfPlayers4(){
        usernameBasicMatch4Players = new HashSet<>();
        usernameBasicMatch4Players.add(playerOne.getUsername());
        usernameBasicMatch4Players.add(playerTwo.getUsername());
        usernameBasicMatch4Players.add(playerThree.getUsername());
        usernameBasicMatch4Players.add(playerFour.getUsername());
    }



    @Test
    void getMatch4Players(){
        setControllerInTest4players();
        Assertions.assertEquals(controllerBasicMatch4Players.getMatch(), basicMatch4Players);
    }

    @Test
    void getMatch_Test() {
        setControllerInTest();
        Assertions.assertEquals(controllerBasicMatch2Players.getMatch(), basicMatch2Players);
    }

    @Test
    void initGame_Test(){
        setControllerInTest();
        Assertions.assertTrue(controllerBasicMatch2Players.getMatch().getPlayers().stream().map(p -> p.getUsername()).toList().containsAll(usernameBasicMatch2Players));
        Assertions.assertEquals(GameState.PLANNING_PHASE, controllerBasicMatch2Players.getGameState());
        Assertions.assertNotNull(controllerBasicMatch2Players.getMatch().getGame());
    }

    @Test
    void initGame4Players_Test(){
        setControllerInTest4players();
        Assertions.assertTrue(controllerBasicMatch4Players.getMatch().getPlayers().stream().map(p -> p.getUsername()).toList().containsAll(usernameBasicMatch4Players));
        Assertions.assertEquals(GameState.PLANNING_PHASE, controllerBasicMatch4Players.getGameState());
        Assertions.assertNotNull(controllerBasicMatch4Players.getMatch().getGame());
        Assertions.assertDoesNotThrow(() -> controllerBasicMatch4Players.getMatch().getTeams());
    }


    @Test
    void onMessageReceived_Test() {
        setControllerInTest();
        controllerBasicMatch2Players.setMatchOnGoing(false);
        controllerBasicMatch2Players.setGameState(GameState.ACTION_PHASE);
        Assertions.assertEquals(controllerBasicMatch2Players.getGameState(), GameState.ACTION_PHASE);
        controllerBasicMatch2Players.setGameState(GameState.PLANNING_PHASE);
        Assertions.assertDoesNotThrow(()-> basicMatch2Players.playAssistantsCard(playerOne, AssistantsCards.CardNine));
        Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.onMessageReceived(new AssistantCardMessage(AssistantsCards.CardEight)));
        Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.onMessageReceived(new MoveStudentMessage(1,1)));
        if(basicMatch2Players.getPositionOfMotherNature()!=1)Assertions.assertEquals(2, basicMatch2Players.getGame().getArchipelagos().get(1).getStudentFromArchipelago().size());
        controllerBasicMatch2Players.setGameState(GameState.ACTION_PHASE);
        Assertions.assertThrows(Exception.class, () -> controllerBasicMatch2Players.onMessageReceived(new AssistantCardMessage(AssistantsCards.CardEight)));
        Assertions.assertFalse(basicMatch2Players.getGame().getAssistantsCardsPlayedInRound().isEmpty());

    }

    @Test
    void onMessageReceived2PLayersMotherNature_Test(){
        setControllerInTest();
        List<AssistantsCards> list = new ArrayList<>();
        list.add(AssistantsCards.CardEight);
        list.add(AssistantsCards.CardFive);
        controllerBasicMatch2Players.setMatchOnGoing(false);
        controllerBasicMatch2Players.setGameState(GameState.PLANNING_PHASE);
        Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.onMessageReceived(new AssistantCardMessage(AssistantsCards.CardEight)));
        Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.onMessageReceived(new AssistantCardMessage(AssistantsCards.CardFive)));
        Assertions.assertEquals(list, basicMatch2Players.getGame().getAssistantsCardsPlayedInRound());
        //Inizia il round player che gioca card Five e muove 2 studenti
        Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.onMessageReceived(new MoveStudentMessage(1, 3)));
        Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.onMessageReceived(new MoveStudentMessage(4, 3)));
        Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.onMessageReceived(new MoveStudentMessage(2, 5)));
        Assertions.assertEquals(TurnPhase.MOVE_MOTHERNATURE, controllerBasicMatch2Players.getTurnController().getTurnPhase());
        if(basicMatch2Players.getPositionOfMotherNature() != 2 && basicMatch2Players.getPositionOfMotherNature()!=8)Assertions.assertEquals(4,  basicMatch2Players.getGame().getArchipelagos().get(2).getStudentFromArchipelago().size());
        else Assertions.assertEquals(2, basicMatch2Players.getGame().getArchipelagos().get(2).getStudentFromArchipelago().size());
        //Muove madre natura
        int i = basicMatch2Players.getPositionOfMotherNature();
        Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.onMessageReceived(new MoveMotherNatureMessage((i+2) % basicMatch2Players.getGame().getArchipelagos().size())));
        Assertions.assertEquals(TurnPhase.CHOOSE_CLOUD, controllerBasicMatch2Players.getTurnController().getTurnPhase());
        Assertions.assertEquals((i+1), basicMatch2Players.getPositionOfMotherNature());
        Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.onMessageReceived(new CloudMessage(1)));
        Assertions.assertEquals(TurnPhase.MOVE_STUDENTS, controllerBasicMatch2Players.getTurnController().getTurnPhase());
    }

    @Test
    void addView_Test() {
        setControllerInTest();
        Assertions.assertNotNull(controllerBasicMatch2Players);
    }

    @Test
    void update_Test(){
        setControllerInTest();
        controllerBasicMatch2Players.setGameState(GameState.PLANNING_PHASE);
        Assertions.assertDoesNotThrow( () -> basicMatch2Players.playAssistantsCard(playerOne, AssistantsCards.CardFive));
        controllerBasicMatch2Players.setGameState(GameState.ACTION_PHASE);
        Assertions.assertThrows(ExceptionGame.class, () ->basicMatch2Players.playAssistantsCard(playerTwo, AssistantsCards.CardFive));
        //basicMatch2Players.getGame().getClouds().add(new Cloud(3));
        //Assertions.assertDoesNotThrow(()->controllerBasicMatch2Players.update(new CloudMessage(0)));
    }



    @Test
    void setViewMap_Test() {
        setListsOfPlayers();
        Assertions.assertDoesNotThrow(()->{
            view1 = new RemoteView(this.clientConnection1);
            view2 = new RemoteView(this.clientConnection2);
        });
        if(basicMatch2Players.getNumberOfPlayers() == 2) {
            try {
                controllerBasicMatch2Players = new Controller(basicMatch2Players, usernameBasicMatch2Players);
                controllerBasicMatch2Players.setMatchOnGoing(false);
            } catch (ExceptionGame | CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        Map<String, ViewInterface> viewmap = new HashMap<>();
        viewmap.put(playerOne.getUsername(), view1);
        viewmap.put(playerTwo.getUsername(), view2);
        controllerBasicMatch2Players.setViewMap(viewmap);
        Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.initGame());
        Assertions.assertNotNull(controllerBasicMatch2Players);
    }

    @Test
    void setGameState_Test() {
        setControllerInTest();
        controllerBasicMatch2Players.setGameState(GameState.PLANNING_PHASE);
        Assertions.assertEquals(controllerBasicMatch2Players.getGameState(), GameState.PLANNING_PHASE);
        controllerBasicMatch2Players.setGameState(GameState.ACTION_PHASE);
        Assertions.assertEquals(controllerBasicMatch2Players.getGameState(), GameState.ACTION_PHASE);
    }

    @Test
    void getGameState_Test() {
        setControllerInTest();
        controllerBasicMatch2Players.setGameState(GameState.PLANNING_PHASE);
        Assertions.assertEquals(controllerBasicMatch2Players.getGameState(), GameState.PLANNING_PHASE);
    }

    @Test
    void isMatchOnGoing_test(){
        setControllerInTest();
        controllerBasicMatch2Players.setMatchOnGoing(true);
        Assertions.assertEquals(controllerBasicMatch2Players.isMatchOnGoing(), true);
    }

    @Test
    void setMatchOnGoing_test(){
        setControllerInTest();
        controllerBasicMatch2Players.setMatchOnGoing(false);
        Assertions.assertEquals(controllerBasicMatch2Players.isMatchOnGoing(), false);
    }
}