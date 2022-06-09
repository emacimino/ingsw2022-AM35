package it.polimi.ingsw.ControllerTest;

import it.polimi.ingsw.Controller.Controller;
import it.polimi.ingsw.Controller.GameState;
import it.polimi.ingsw.Controller.TurnController;
import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.FactoryMatch;
import it.polimi.ingsw.Model.FactoryMatch.Player;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ControllerTest {

    private Set<String> usernameBasicMatch2Players;
    private Controller controllerBasicMatch2Players;
    private TurnController turnControllerBasicMatch2Players;
    private final FactoryMatch factoryMatch = new FactoryMatch();
    private final BasicMatch basicMatch2Players = factoryMatch.newMatch(2);
    private final Player playerOne = new Player("One");
    private final Player playerTwo = new Player("Two");
    private  ViewInterface view1;
    private SocketClientConnection clientConnection1;
    private  ViewInterface view2;
    private  SocketClientConnection clientConnection2;



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
                controllerBasicMatch2Players.addView(playerOne.getUsername(), view2);
                controllerBasicMatch2Players.initGame();
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
       // Assertions.assertNotNull(turnControllerBasicMatch2Players);
    }


    @Test
    void onMessageReceived_Test() {
        setControllerInTest();
        controllerBasicMatch2Players.setGameState(GameState.ACTION_PHASE);
        Assertions.assertDoesNotThrow(() -> controllerBasicMatch2Players.onMessageReceived(new MoveStudentMessage(3, 4)));
    }

  /*  @Test
    void addView_Test() {
        setControllerInTest();
        controllerBasicMatch2Players.addView(playerOne.getUsername(), viewInterfaceMap2Players.get(0));
        Assertions.assertNotNull(controllerBasicMatch2Players);
    }

    @Test
    void update_Test(){
        setControllerInTest();
        controllerBasicMatch2Players.setGameState(GameState.PLANNING_PHASE);
        Assertions.assertDoesNotThrow( () -> basicMatch2Players.playAssistantsCard(playerOne, AssistantsCards.CardFive));
        controllerBasicMatch2Players.setGameState(GameState.ACTION_PHASE);
        Assertions.assertThrows(ExceptionGame.class, () ->basicMatch2Players.playAssistantsCard(playerTwo, AssistantsCards.CardFive));
    }

    @Test
    void setViewMap_Test() {
        setControllerInTest();
        controllerBasicMatch2Players.setViewMap(viewInterfaceMap2Players);
    }

    @Test
    void setGameState_Test(GameState planningPhase) {
        setControllerInTest();
        controllerBasicMatch2Players.setGameState(GameState.PLANNING_PHASE);
        Assertions.assertEquals(controllerBasicMatch2Players.getGameState(), GameState.PLANNING_PHASE);
        //controllerBasicMatch3Players.setGameState(GameState.GAME_STARTED);
        //Assertions.assertEquals(controllerBasicMatch3Players.getGameState(), GameState.GAME_STARTED);
    }

    @Test
    void getGameState_Test() {
        controllerBasicMatch2Players.setGameState(GameState.PLANNING_PHASE);
        Assertions.assertNotNull(controllerBasicMatch2Players.getGameState());
        //Assertions.assertTrue(controllerBasicMatch3Players.getGameState().getClass().isEnum());
    }*/
}