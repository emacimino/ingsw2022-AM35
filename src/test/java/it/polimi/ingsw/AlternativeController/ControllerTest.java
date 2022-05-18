package it.polimi.ingsw.AlternativeController;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.FactoryMatch;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.Wizard.Wizard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    private Set<String> usernameBasicMatch2Players,usernameBasicMatch3Players;
    private final Controller controllerBasicMatch2Players;
    private final Controller controllerBasicMatch3Players;
    private final FactoryMatch factoryMatch = new FactoryMatch();
    private final BasicMatch basicMatch3Players = factoryMatch.newMatch(3);
    private final BasicMatch basicMatch2Players = factoryMatch.newMatch(2);
    private final Player playerOne = new Player("usernameOne");
    private final Player playerTwo = new Player("usernameTwo");
    private final Player playerThree = new Player("usernameThree");


    private void setListsOfPlayers(){
        usernameBasicMatch2Players = new HashSet<>();
        usernameBasicMatch3Players = new HashSet<>();
        usernameBasicMatch2Players.add(playerOne.getUsername());
        usernameBasicMatch2Players.add(playerTwo.getUsername());
        usernameBasicMatch3Players.add(playerOne.getUsername());
        usernameBasicMatch3Players.add(playerTwo.getUsername());
        usernameBasicMatch3Players.add(playerThree.getUsername());
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

    public ControllerTest() {
        setListsOfPlayers();
        gameSetter();
        try {
            controllerBasicMatch2Players = new Controller(basicMatch2Players,usernameBasicMatch2Players);
        } catch (ExceptionGame | CloneNotSupportedException e) {
            e.printStackTrace();
        }
        try {
            controllerBasicMatch3Players = new Controller(basicMatch3Players,usernameBasicMatch3Players);
        } catch (ExceptionGame | CloneNotSupportedException e) {
            e.printStackTrace();
        }


    }

    @Test
    void onMessageReceived() {

    }

    @Test
    void addView() {
    }

    @Test
    void update() {
    }
}