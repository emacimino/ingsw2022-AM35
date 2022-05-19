package it.polimi.ingsw.AlternativeController;

import it.polimi.ingsw.AlternativeServer.ClientConnection;
import it.polimi.ingsw.AlternativeServer.Server;
import it.polimi.ingsw.AlternativeServer.SocketClientConnection;
import it.polimi.ingsw.AlternativeView.RemoteView;
import it.polimi.ingsw.AlternativeView.ViewInterface;
import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.FactoryMatch;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.Wizard.Wizard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

class ControllerTest {


    private Set<String> usernameBasicMatch2Players,usernameBasicMatch3Players;
    private Controller controllerBasicMatch2Players;
    private Controller controllerBasicMatch3Players;
    private TurnController turnControllerBasicMatch2Players;
    private TurnController turnControllerBasicMatch3Players;private final Server server = new Server();
    private final Socket socket = new Socket("127.0.0.1",62341);
    private final FactoryMatch factoryMatch = new FactoryMatch();
    private final BasicMatch basicMatch3Players = factoryMatch.newMatch(3);
    private final BasicMatch basicMatch2Players = factoryMatch.newMatch(2);
    private final Player playerOne = new Player("usernameOne");
    private final Player playerTwo = new Player("usernameTwo");
    private final Player playerThree = new Player("usernameThree");
    private final ViewInterface view1 = new RemoteView((SocketClientConnection) this.clientConnection1);
    private final ClientConnection clientConnection1 = new SocketClientConnection(this.socket,this.server);
    private final ViewInterface view2 = new RemoteView((SocketClientConnection) this.clientConnection2);
    private final ClientConnection clientConnection2 = new SocketClientConnection(this.socket,this.server);
    private final ViewInterface view3 = new RemoteView((SocketClientConnection) this.clientConnection3);
    private final ClientConnection clientConnection3 = new SocketClientConnection(this.socket,this.server);
    private final Map<String,ViewInterface> viewInterfaceMap2Players = new HashMap<>();
    private final Map<String,ViewInterface> viewInterfaceMap3Players = new HashMap<>();



    private void setListsOfPlayersAndMap(){
        usernameBasicMatch2Players = new HashSet<>();
        usernameBasicMatch3Players = new HashSet<>();
        usernameBasicMatch2Players.add(playerOne.getUsername());
        usernameBasicMatch2Players.add(playerTwo.getUsername());
        usernameBasicMatch3Players.add(playerOne.getUsername());
        usernameBasicMatch3Players.add(playerTwo.getUsername());
        usernameBasicMatch3Players.add(playerThree.getUsername());
        viewInterfaceMap2Players.put(playerOne.getUsername(),view1);
        viewInterfaceMap2Players.put(playerTwo.getUsername(),view2);
        viewInterfaceMap3Players.put(playerOne.getUsername(),view1);
        viewInterfaceMap3Players.put(playerTwo.getUsername(),view2);
        viewInterfaceMap3Players.put(playerThree.getUsername(),view3);
    }

    public void gameSetter(){
        List<Player> players = new ArrayList<>();
        players.add(playerOne);
        players.add(playerTwo);
        Assertions.assertDoesNotThrow(() ->
                basicMatch2Players.setGame(players)
        );
        players.add(playerThree);
        if(basicMatch3Players.getNumberOfPlayers() == players.size())
            Assertions.assertDoesNotThrow(() ->
                    basicMatch3Players.setGame(players)
            );
        else
            Assertions.assertThrows(ExceptionGame.class, ()->basicMatch3Players.setGame(players));
    }
    public void printGame(){
        System.out.println("Archipelagos "+ basicMatch3Players.getGame().getArchipelagos().size());
        System.out.println(basicMatch3Players.getGame().getWizards() + "\n");
        for( Wizard w : basicMatch3Players.getGame().getWizards()){
            System.out.println(w);
            System.out.println("towers " + w.getBoard().getTowersInBoard().size());
            System.out.println("assistantCard " + w.getRoundAssistantsCard());
            System.out.println("number of students in entrance " + w.getBoard().getStudentsInEntrance().size());

        }
    }

    public ControllerTest() throws IOException {
        setListsOfPlayersAndMap();
        gameSetter();
        if(basicMatch2Players.getNumberOfPlayers() == 2) {
            try {
                controllerBasicMatch2Players = new Controller(basicMatch2Players, usernameBasicMatch2Players);
                turnControllerBasicMatch2Players = new TurnController(this.controllerBasicMatch2Players, viewInterfaceMap2Players);
            } catch (ExceptionGame | CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        if(basicMatch3Players.getNumberOfPlayers() == 3) {
            try {
                controllerBasicMatch3Players = new Controller(basicMatch3Players, usernameBasicMatch3Players);
                turnControllerBasicMatch3Players = new TurnController(this.controllerBasicMatch3Players, viewInterfaceMap3Players);
            } catch (ExceptionGame | CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

    }


        @Test
    void onMessageReceived() throws IOException {
        ControllerTest controllerTest = new ControllerTest();
        Assertions.assertTrue(controllerBasicMatch2Players.getMatch().getPlayers().contains(turnControllerBasicMatch2Players.getActivePlayer()));

    }

    private void checkCorrectSettings() {
    }

    @Test
    void addView() {
    }

    @Test
    void update() {
    }
}