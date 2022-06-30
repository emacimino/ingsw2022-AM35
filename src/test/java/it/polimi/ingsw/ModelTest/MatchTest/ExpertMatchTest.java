package it.polimi.ingsw.ModelTest.MatchTest;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.FactoryMatch;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.Wizard.Wizard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to test the creation of an expert match
 */
public class ExpertMatchTest {
    private final FactoryMatch factoryMatch = new FactoryMatch();
    private final BasicMatch basicMatch2Players = factoryMatch.newMatch(2);
    private final BasicMatch expertMatch = new ExpertMatch(basicMatch2Players);
    Wizard wizard1, wizard2;
    private final Player player1 = new Player("username1");
    private final Player player2 = new Player("username2");

    /**
     * Methods used to set the game
     * @param player1 player one
     * @param player2 player two
     * @return a list of players
     */
    private List<Player> setPlayers(Player player1, Player player2){
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        return players;
    }

    /**
     * Method used to get steps of mother nature
     * @param player player one or two
     * @return the steps you can move mother nature by
     * @throws ExceptionGame is something goes wrong
     */
    public int getSteps(Player player) throws ExceptionGame {
        Wizard wizard = expertMatch.getGame().getWizardFromPlayer(player);
        return wizard.getRoundAssistantsCard().getStep();
    }

    /**
     * Method used to set a test match
     * @throws ExceptionGame if something goes wrong
     */
    private void setATestMatch() throws ExceptionGame {
        expertMatch.setGame(setPlayers(player1, player2));
    }

    /**
     * This method tests the chooseCloud method
     */
    @Test
    void chooseCloudTest(){
        Assertions.assertDoesNotThrow(()->{
            this.setATestMatch();
            wizard1 = expertMatch.getGame().getWizardFromPlayer(player1);
            wizard2 = expertMatch.getGame().getWizardFromPlayer(player2);
            expertMatch.playAssistantsCard(player1, wizard1.getAssistantsDeck().getPlayableAssistants().get(2));
            expertMatch.playAssistantsCard(player2, wizard2.getAssistantsDeck().getPlayableAssistants().get(1));
            wizard1.getBoard().getStudentsInEntrance().clear();
            wizard2.getBoard().getStudentsInEntrance().clear();
            expertMatch.chooseCloud(player2, expertMatch.getGame().getClouds().get(0));
            Assertions.assertEquals(3, wizard2.getBoard().getStudentsInEntrance().size());
            Assertions.assertEquals(0, expertMatch.getGame().getClouds().get(0).getStudentOnCloud().size());
            Assertions.assertEquals(3, expertMatch.getGame().getClouds().get(1).getStudentOnCloud().size());
            expertMatch.chooseCloud(player1, expertMatch.getGame().getClouds().get(1));

            Assertions.assertEquals(3, expertMatch.getGame().getClouds().get(0).getStudentOnCloud().size());
            Assertions.assertEquals(3, expertMatch.getGame().getClouds().get(1).getStudentOnCloud().size());
        });
    }
}
