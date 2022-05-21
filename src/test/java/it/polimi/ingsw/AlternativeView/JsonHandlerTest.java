package it.polimi.ingsw.AlternativeView;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.FactoryMatch;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.Wizard.Wizard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonHandlerTest {
    private final FactoryMatch factoryMatch = new FactoryMatch();
    private final BasicMatch basicMatch3Players = factoryMatch.newMatch(3);
    private final BasicMatch basicMatch2Players = factoryMatch.newMatch(2);
    private final Player playerOne = new Player("usernameOne");
    private final Player playerTwo = new Player("usernameTwo");
    private final Player playerThree = new Player("usernameThree");


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

    @Test
    public void JavaToJsonParser(){
        gameSetter();
        JsonHandler jsonHandler = new JsonHandler();
        jsonHandler.javaToJsonParser(basicMatch2Players.getGame());
        Reader reader = new InputStreamReader(jsonHandler);
        System.out.println(jsonHandler);
    }


}