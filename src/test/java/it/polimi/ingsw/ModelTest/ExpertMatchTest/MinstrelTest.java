package it.polimi.ingsw.ModelTest.ExpertMatchTest;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.Minstrel;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.FactoryMatch;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.TableOfStudents;
import it.polimi.ingsw.Model.Wizard.Wizard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Class that contains the tests for the Minstrel character card
 */
class MinstrelTest {
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
     * Method that tests 10 times the match and Minstrel character card interaction
     */
    @ParameterizedTest
    @EnumSource(Color.class)
    public void setTestMatch1(Color c) {
       // Color c = Color.BLUE;
        Assertions.assertDoesNotThrow(() -> {
            this.setATestMatch();
            wizard1 = expertMatch.getGame().getWizardFromPlayer(player1);
            wizard2 = expertMatch.getGame().getWizardFromPlayer(player2);
            expertMatch.playAssistantsCard(player1, wizard1.getAssistantsDeck().getPlayableAssistants().get(2));
            expertMatch.playAssistantsCard(player2, wizard2.getAssistantsDeck().getPlayableAssistants().get(1));

            CharacterCard minstrel = new Minstrel(basicMatch2Players, "Minstrel");
            expertMatch.getCharactersForThisGame().put(minstrel.getName(), minstrel);
            Assertions.assertEquals(1, minstrel.getCost());


            Assertions.assertEquals(1, wizard1.getCoins());
            wizard1.getBoard().addStudentInTable(new Student(c));
            wizard1.getBoard().addStudentInTable(new Student(c));
            wizard1.getBoard().addStudentInTable(new Student(c));
            wizard1.getBoard().addStudentInTable(new Student(c));
            List<Student> studentsOnTable = wizard1.getBoard().getTableOfStudent(c).getStudentsInTable().stream().toList();
            List<Student> fromA = new ArrayList<>(studentsOnTable.subList(0,2));
            List<Student> toB = new ArrayList<>(wizard1.getBoard().getStudentsInEntrance().stream().toList().subList(0,2));

            minstrel.setActiveWizard(wizard1);
            minstrel.setActiveStudents(fromA);
            minstrel.setPassiveStudents(toB);
            minstrel.useCard(expertMatch);

            Assertions.assertFalse(wizard1.getBoard().getTableOfStudent(c).getStudentsInTable().containsAll(fromA));
            Assertions.assertFalse(wizard1.getBoard().getStudentsInEntrance().containsAll(toB));
            List<Student> studentsInCase = new ArrayList<>();
            for(TableOfStudents t: wizard1.getBoard().getTables()) {
                studentsInCase.addAll(t.getStudentsInTable());
            }
            Assertions.assertTrue(studentsInCase.containsAll(toB));
            Assertions.assertTrue(wizard1.getBoard().getStudentsInEntrance().containsAll(fromA));
            Assertions.assertEquals(0, wizard1.getCoins());
            Assertions.assertEquals(2, minstrel.getCost());

            wizard1.getBoard().addStudentInTable(new Student(c));
            wizard1.getBoard().addStudentInTable(new Student(c));
            studentsOnTable = wizard1.getBoard().getTableOfStudent(c).getStudentsInTable().stream().toList();

            List<Student> fromA_2 = new ArrayList<>(studentsOnTable.subList(0,3));
            List<Student> toB_2 = new ArrayList<>(wizard1.getBoard().getStudentsInEntrance().stream().toList().subList(0,2));

            wizard1.addACoin();
            wizard1.addACoin();
            minstrel.setActiveWizard(wizard1);
            minstrel.setActiveStudents(fromA_2);
            minstrel.setPassiveStudents(toB_2);
            Assertions.assertThrows(ExceptionGame.class, ()-> minstrel.useCard(expertMatch));

        });

    }

    /**
     * Method that tests 10 times the match and Minstrel character card interaction in a 4 player match
     */
    @ParameterizedTest
    @EnumSource(Color.class)
    public void match4player_Test(Color c){
        Color color = Color.PINK;
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
            CharacterCard minstrel = new Minstrel(match4players, "Minstrel");
            expertMatch4Players.getCharactersForThisGame().put(minstrel.getName(), minstrel);
            assertEquals(1, minstrel.getCost());
            assertEquals(1, wizard1.getCoins());
            assertEquals(1, wizard2.getCoins());
            assertEquals(1, wizard3.getCoins());
            assertEquals(1, wizard4.getCoins());

            wizard4.getBoard().addStudentInTable(new Student(c));
            wizard4.getBoard().addStudentInTable(new Student(c));
            wizard4.getBoard().addStudentInTable(new Student(c));
            wizard4.getBoard().addStudentInTable(new Student(c));
            List<Student> studentsOnTable = wizard4.getBoard().getTableOfStudent(c).getStudentsInTable().stream().toList();
            List<Student> fromA = new ArrayList<>(studentsOnTable.subList(0,2));
            List<Student> toB = new ArrayList<>(wizard4.getBoard().getStudentsInEntrance().stream().toList().subList(0,2));
System.out.println(toB);
            minstrel.setActiveWizard(wizard4);
            minstrel.setActiveStudents(fromA);
            minstrel.setPassiveStudents(toB);
            minstrel.useCard(expertMatch);

            Assertions.assertFalse(wizard4.getBoard().getTableOfStudent(c).getStudentsInTable().containsAll(fromA));
            Assertions.assertFalse(wizard4.getBoard().getStudentsInEntrance().containsAll(toB));
            List<Student> studentsInCase = new ArrayList<>();
            for(TableOfStudents t: wizard4.getBoard().getTables()) {
                studentsInCase.addAll(t.getStudentsInTable());
            }
            Assertions.assertTrue(studentsInCase.containsAll(toB));
            Assertions.assertTrue(wizard4.getBoard().getStudentsInEntrance().containsAll(fromA));
            Assertions.assertEquals(0, wizard4.getCoins());
            Assertions.assertEquals(2, minstrel.getCost());

            wizard4.getBoard().addStudentInTable(new Student(c));
            wizard4.getBoard().addStudentInTable(new Student(c));
            studentsOnTable = wizard4.getBoard().getTableOfStudent(c).getStudentsInTable().stream().toList();

            List<Student> fromA_2 = new ArrayList<>(studentsOnTable.subList(0,3));
            List<Student> toB_2 = new ArrayList<>(wizard4.getBoard().getStudentsInEntrance().stream().toList().subList(0,2));

            wizard4.addACoin();
            wizard4.addACoin();
            minstrel.setActiveWizard(wizard4);
            minstrel.setActiveStudents(fromA_2);
            minstrel.setPassiveStudents(toB_2);
            Assertions.assertThrows(ExceptionGame.class, ()-> minstrel.useCard(expertMatch));
            Assertions.assertEquals(2, minstrel.getCost());
            wizard2.addACoin();
            minstrel.setActiveWizard(wizard2);
            Student student1 = new Student(c);
            Student student2 = new Student(color);
            wizard2.getBoard().addStudentInTable(student1);
            wizard2.getBoard().addStudentInTable(student2);
            studentsOnTable= new ArrayList<>();
            studentsOnTable.add(student1);
            studentsOnTable.add(student2);
            fromA_2  = new ArrayList<>(studentsOnTable);
            toB_2 = new ArrayList<>(wizard2.getBoard().getStudentsInEntrance().stream().toList().subList(0,2));
            minstrel.setActiveStudents(fromA_2);
            minstrel.setPassiveStudents(toB_2);
            minstrel.useCard(expertMatch);

            List<Student> temp = new ArrayList<>();
            for(TableOfStudents t: wizard2.getBoard().getTables()) {
                temp.addAll(t.getStudentsInTable());
            }
            Assertions.assertFalse(temp.containsAll(fromA_2));
            Assertions.assertFalse(wizard2.getBoard().getStudentsInEntrance().containsAll(toB_2));
            Assertions.assertTrue(temp.containsAll(toB_2));
            Assertions.assertTrue(wizard2.getBoard().getStudentsInEntrance().containsAll(fromA_2));
            Assertions.assertEquals(0, wizard2.getCoins());
            Assertions.assertEquals(3, minstrel.getCost());
        });

    }


}