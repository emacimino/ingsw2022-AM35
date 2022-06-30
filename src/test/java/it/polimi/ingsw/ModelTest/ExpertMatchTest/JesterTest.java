package it.polimi.ingsw.ModelTest.ExpertMatchTest;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.Jester;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.FactoryMatch;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.Wizard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class that contains the tests for the Jester character card
 */
class JesterTest {
    private final FactoryMatch factoryMatch = new FactoryMatch();
    private final BasicMatch basicMatch2Players = factoryMatch.newMatch(2);
    private final ExpertMatch expertMatch = new ExpertMatch(basicMatch2Players);
    Wizard wizard1, wizard2;
    private final Player player1 = new Player("username1");
    private final Player player2 = new Player("username2");

    /**
     * Method used to set the player for the game
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
     * Method used to set a test match
     * @throws ExceptionGame if something goes wrong
     */
    private void setATestMatch() throws ExceptionGame {
        expertMatch.setGame(setPlayers(player1, player2));
    }

    /**
     * Method that tests 10 times the match and Jester character card interaction
     */
    @RepeatedTest(10)
    public void setTestMatch1() {
        Assertions.assertDoesNotThrow(() -> {
            this.setATestMatch();
            wizard1 = expertMatch.getGame().getWizardFromPlayer(player1);
            wizard2 = expertMatch.getGame().getWizardFromPlayer(player2);
            expertMatch.playAssistantsCard(player1, wizard1.getAssistantsDeck().getPlayableAssistants().get(2));
            expertMatch.playAssistantsCard(player2, wizard2.getAssistantsDeck().getPlayableAssistants().get(1));

            CharacterCard jester = new Jester(basicMatch2Players, "Jester");
            expertMatch.getCharactersForThisGame().put(jester.getName(), jester);
            Assertions.assertEquals(1, jester.getCost());


            Assertions.assertEquals(1, wizard1.getCoins());
            List<Student> studentsOnCard = jester.getStudentsOnCard();
            List<Student> fromA = new ArrayList<>(studentsOnCard.subList(0,3));
            List<Student> toB = new ArrayList<>(wizard1.getBoard().getStudentsInEntrance().stream().toList().subList(0,3));

            jester.setActiveWizard(wizard1);
            jester.setActiveStudents(fromA);
            jester.setPassiveStudents(toB);
            jester.useCard(expertMatch);

            Assertions.assertFalse(jester.getStudentsOnCard().containsAll(fromA));
            Assertions.assertFalse(wizard1.getBoard().getStudentsInEntrance().containsAll(toB));
            Assertions.assertTrue(jester.getStudentsOnCard().containsAll(toB));
            Assertions.assertTrue(wizard1.getBoard().getStudentsInEntrance().containsAll(fromA));
            Assertions.assertEquals(0, wizard1.getCoins());
            Assertions.assertEquals(2, jester.getCost());


            List<Student> fromA_2 = new ArrayList<>(studentsOnCard.subList(0,4));
            List<Student> toB_2 = new ArrayList<>(wizard1.getBoard().getStudentsInEntrance().stream().toList().subList(0,3));

            wizard1.addACoin();
            jester.setActiveWizard(wizard1);
            jester.setActiveStudents(fromA_2);
            jester.setPassiveStudents(toB_2);
            Assertions.assertThrows(ExceptionGame.class, ()-> jester.useCard(expertMatch));

            fromA_2.remove(0);
            fromA_2.remove(0);
            fromA_2.add(new Student(Color.BLUE));
            jester.setActiveStudents(fromA_2);
            Assertions.assertThrows(ExceptionGame.class, ()-> jester.useCard(expertMatch));
        });

    }

    /**
     * Method that tests 10 times the match and Jester character card interaction in a 4 player match
     */
    @RepeatedTest(10)
    public void match4player_Test(){
        BasicMatch match4players = factoryMatch.newMatch(4);
        Player player3 = new Player("username3");
        Player player4 = new Player("username4");
        List<Player> players = setPlayers(player1, player2);
        players.add(player3);
        players.add(player4);
        ExpertMatch expertMatch4Players = new ExpertMatch(match4players);

        assertThrows(ExceptionGame.class, ()-> expertMatch4Players.setGame(players));

        assertDoesNotThrow( ()->{
            expertMatch4Players.setTeams(players);

            expertMatch4Players.setGame(players);
            Wizard wizard1 = expertMatch4Players.getGame().getWizardFromPlayer(player1);
            Wizard wizard2 = expertMatch4Players.getGame().getWizardFromPlayer(player2);
            expertMatch4Players.playAssistantsCard(player1, wizard1.getAssistantsDeck().getPlayableAssistants().get(0));
            expertMatch4Players.playAssistantsCard(player2, wizard2.getAssistantsDeck().getPlayableAssistants().get(3));
            Wizard wizard3 = expertMatch4Players.getGame().getWizardFromPlayer(player3);
            Wizard wizard4 = expertMatch4Players.getGame().getWizardFromPlayer(player4);
            expertMatch4Players.playAssistantsCard(player3, wizard3.getAssistantsDeck().getPlayableAssistants().get(1));
            expertMatch4Players.playAssistantsCard(player4, wizard4.getAssistantsDeck().getPlayableAssistants().get(5));
            CharacterCard jester = new Jester(match4players, "Jester");
            expertMatch4Players.getCharactersForThisGame().put(jester.getName(), jester);
            assertEquals(1, jester.getCost());
            assertEquals(1, wizard1.getCoins());
            assertEquals(1, wizard2.getCoins());
            assertEquals(1, wizard3.getCoins());
            assertEquals(1, wizard4.getCoins());

            Assertions.assertEquals(1, wizard4.getCoins());
            List<Student> studentsOnCard = jester.getStudentsOnCard();
            List<Student> fromA = new ArrayList<>(studentsOnCard.subList(0,3));
            List<Student> toB = new ArrayList<>(wizard4.getBoard().getStudentsInEntrance().stream().toList().subList(0,3));


            jester.setActiveWizard(wizard4);
            jester.setActiveStudents(fromA);
            jester.setPassiveStudents(toB);
            jester.useCard(expertMatch4Players);

            Assertions.assertFalse(jester.getStudentsOnCard().containsAll(fromA));
            Assertions.assertFalse(wizard4.getBoard().getStudentsInEntrance().containsAll(toB));
            Assertions.assertTrue(jester.getStudentsOnCard().containsAll(toB));
            Assertions.assertTrue(wizard4.getBoard().getStudentsInEntrance().containsAll(fromA));
            Assertions.assertEquals(0, wizard4.getCoins());
            Assertions.assertEquals(2, jester.getCost());

            List<Student> fromA_2 = new ArrayList<>(studentsOnCard.subList(0,4));
            List<Student> toB_2 = new ArrayList<>(wizard1.getBoard().getStudentsInEntrance().stream().toList().subList(0,3));

            wizard1.addACoin();
            jester.setActiveWizard(wizard1);
            jester.setActiveStudents(fromA_2);
            jester.setPassiveStudents(toB_2);
            Assertions.assertThrows(ExceptionGame.class, ()-> jester.useCard(expertMatch));

            fromA_2.remove(0);
            fromA_2.remove(0);
            fromA_2.add(new Student(Color.BLUE));
            jester.setActiveStudents(fromA_2);
            Assertions.assertThrows(ExceptionGame.class, ()-> jester.useCard(expertMatch));

        });

    }

}