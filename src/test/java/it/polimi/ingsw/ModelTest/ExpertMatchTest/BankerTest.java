package it.polimi.ingsw.ModelTest.ExpertMatchTest;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.Banker;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.FactoryMatch;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.Wizard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BankerTest {
    private final FactoryMatch factoryMatch = new FactoryMatch();
    private final BasicMatch basicMatch2Players = factoryMatch.newMatch(2);
    private final ExpertMatch expertMatch = new ExpertMatch(basicMatch2Players);
    Wizard wizard1, wizard2;
    private final Player player1 = new Player("username1");
    private final Player player2 = new Player("username2");

    private List<Player> setPlayers(Player player1, Player player2){
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        return players;
    }
    public int getSteps(Player player) throws ExceptionGame {
        Wizard wizard = expertMatch.getGame().getWizardFromPlayer(player);
        return wizard.getRoundAssistantsCard().getStep();
    }
    private void setATestMatch() throws ExceptionGame {
        expertMatch.setGame(setPlayers(player1, player2));
    }
    public void printGame(){
        System.out.println("\n PRINTING STATE OF GAME: ");
        System.out.println("number of archipelagos " + expertMatch.getGame().getArchipelagos().size());

        System.out.println("professor in game: "+ expertMatch.getGame().getProfessors());
        System.out.println("action order in round: " + expertMatch.getActionPhaseOrderOfPlayers());
        System.out.println("position of MN " + expertMatch.getPositionOfMotherNature());
        for( Wizard w : expertMatch.getGame().getWizards()){
            System.out.println("--->"+w);
            System.out.println("towers " + w.getBoard().getTowersInBoard().size());
            System.out.println("assistantCard " + w.getRoundAssistantsCard());
            System.out.println("number of students in entrance " + w.getBoard().getStudentsInEntrance().size());
            System.out.println("professors in table : " +w.getBoard().getProfessorInTable());
        }
        System.out.println("END OF PRINT \n");
    }

    @ParameterizedTest
    @EnumSource(Color.class)
    public void setTestMatch(Color color) {
        assertDoesNotThrow(() -> {
            this.setATestMatch();
            wizard1 = expertMatch.getGame().getWizardFromPlayer(player1);
            wizard2 = expertMatch.getGame().getWizardFromPlayer(player2);
            expertMatch.playAssistantsCard(player1, wizard1.getAssistantsDeck().getPlayableAssistants().get(0));
            expertMatch.playAssistantsCard(player2, wizard2.getAssistantsDeck().getPlayableAssistants().get(3));
            //sets a Chef card in the game in position 0
            CharacterCard banker = new Banker( basicMatch2Players,"Banker");
            expertMatch.getCharactersForThisGame().put(banker.getName(), banker);
            assertEquals(3, banker.getCost());
            assertEquals(1, wizard1.getCoins());
            assertEquals(1, wizard2.getCoins());
            int cost = banker.getCost();

            Collection<Student> studentsPlayer1 = expertMatch.getGame().getWizardFromPlayer(player1).getBoard().getStudentsInEntrance();
            Collection<Student> studentsPlayer2 = expertMatch.getGame().getWizardFromPlayer(player2).getBoard().getStudentsInEntrance();
            Student[] students1 = new Student[studentsPlayer1.size()];
            Student[] students2 = new Student[studentsPlayer2.size()];
            studentsPlayer1.toArray(students1);
            studentsPlayer2.toArray(students2);
            expertMatch.moveStudentOnBoard(player1, students1[0]);
            expertMatch.moveStudentOnBoard(player1, students1[1]);
            expertMatch.moveStudentOnBoard(player2, students2[0]);
            expertMatch.moveStudentOnBoard(player2, students2[1]);
            wizard1.getBoard().addStudentInTable(new Student(color));
            wizard1.getBoard().addStudentInTable(new Student(color));
            wizard1.getBoard().addStudentInTable(new Student(color));
            wizard1.getBoard().addStudentInTable(new Student(color));
            wizard2.getBoard().addStudentInTable(new Student(color));
            wizard2.getBoard().addStudentInTable(new Student(color));
            wizard2.getBoard().addStudentInTable(new Student(color));

            int numOfStudOnTableBefore_1 = wizard1.getBoard().getStudentsFromTable(color).size();
            int numOfStudOnTableBefore_2 = wizard2.getBoard().getStudentsFromTable(color).size();

            wizard1.addACoin();
            wizard1.addACoin();
            banker.setActiveWizard(wizard1);
            banker.setColorEffected(color);
            banker.useCard(expertMatch);
            assertEquals(cost + 1, banker.getCost());
            assertNull(banker.getColorEffected());

            int numOfStudOnTableAfter_1 = wizard1.getBoard().getStudentsFromTable(color).size();
            int numOfStudOnTableAfter_2 = wizard2.getBoard().getStudentsFromTable(color).size();

            if(numOfStudOnTableBefore_1 <= 3)
                Assertions.assertEquals(0, numOfStudOnTableAfter_1);
            else
                Assertions.assertEquals(numOfStudOnTableBefore_1 - 3, numOfStudOnTableAfter_1);
            if(numOfStudOnTableBefore_2 <= 3)
                Assertions.assertEquals(0, numOfStudOnTableAfter_2);
            else
                Assertions.assertEquals(numOfStudOnTableBefore_2 - 3, numOfStudOnTableAfter_2);
        });
    }

    @ParameterizedTest
    @EnumSource(Color.class)
    public void match4player_Test (Color color) {
        BasicMatch match4players = factoryMatch.newMatch(4);
        Player player3 = new Player("username3");
        Player player4 = new Player("username4");
        List<Player> players = setPlayers(player1, player2);
        players.add(player3);
        players.add(player4);
        ExpertMatch expertMatch4Players = new ExpertMatch(match4players);
        Assertions.assertThrows(ExceptionGame.class, () -> expertMatch4Players.setGame(players));
        Assertions.assertDoesNotThrow(() -> {
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
            CharacterCard banker = new Banker(match4players, "Banker");
            expertMatch4Players.getCharactersForThisGame().put(banker.getName(), banker);
            assertEquals(3, banker.getCost());
            assertEquals(1, wizard1.getCoins());
            assertEquals(1, wizard2.getCoins());
            assertEquals(1, wizard3.getCoins());
            assertEquals(1, wizard4.getCoins());
            int cost = banker.getCost();
            Collection<Student> studentsPlayer3 = expertMatch4Players.getGame().getWizardFromPlayer(player3).getBoard().getStudentsInEntrance();
            Collection<Student> studentsPlayer4 = expertMatch4Players.getGame().getWizardFromPlayer(player4).getBoard().getStudentsInEntrance();
            List<Student> students3 = new ArrayList<>(studentsPlayer3);
            List<Student> students4 = new ArrayList<>(studentsPlayer4);
            expertMatch4Players.moveStudentOnBoard(player3, students3.get(0));
            expertMatch4Players.moveStudentOnBoard(player3, students3.get(1));
            expertMatch4Players.moveStudentOnBoard(player4, students4.get(0));
            expertMatch4Players.moveStudentOnBoard(player4, students4.get(1));
            Collection<Student> studentsPlayer1 = expertMatch4Players.getGame().getWizardFromPlayer(player1).getBoard().getStudentsInEntrance();
            Collection<Student> studentsPlayer2 = expertMatch4Players.getGame().getWizardFromPlayer(player2).getBoard().getStudentsInEntrance();
            Student[] students1 = new Student[studentsPlayer1.size()];
            Student[] students2 = new Student[studentsPlayer2.size()];
            studentsPlayer1.toArray(students1);
            studentsPlayer2.toArray(students2);
            expertMatch4Players.moveStudentOnBoard(player1, students1[0]);
            expertMatch4Players.moveStudentOnBoard(player1, students1[1]);
            expertMatch4Players.moveStudentOnBoard(player2, students2[0]);
            expertMatch4Players.moveStudentOnBoard(player2, students2[1]);

            wizard4.getBoard().addStudentInTable(new Student(color));
            wizard4.getBoard().addStudentInTable(new Student(color));
            wizard4.getBoard().addStudentInTable(new Student(color));
            wizard4.getBoard().addStudentInTable(new Student(color));
            wizard2.getBoard().addStudentInTable(new Student(color));
            wizard3.getBoard().addStudentInTable(new Student(color));
            wizard1.getBoard().addStudentInTable(new Student(color));

            wizard4.addACoin();
            wizard4.addACoin();
            int numOfStudOnTableBefore_1 = wizard1.getBoard().getStudentsFromTable(color).size();
            int numOfStudOnTableBefore_2 = wizard2.getBoard().getStudentsFromTable(color).size();
            int numOfStudOnTableBefore_3 = wizard3.getBoard().getStudentsFromTable(color).size();
            int numOfStudOnTableBefore_4 = wizard4.getBoard().getStudentsFromTable(color).size();

            banker.setActiveWizard(wizard4);
            banker.setColorEffected(color);
            banker.useCard(expertMatch4Players);
            assertEquals(cost + 1, banker.getCost());

            int numOfStudOnTableAfter_1 = wizard1.getBoard().getStudentsFromTable(color).size();
            int numOfStudOnTableAfter_2 = wizard2.getBoard().getStudentsFromTable(color).size();
            int numOfStudOnTableAfter_3 = wizard3.getBoard().getStudentsFromTable(color).size();
            int numOfStudOnTableAfter_4 = wizard4.getBoard().getStudentsFromTable(color).size();

            if (numOfStudOnTableBefore_1 <= 3)
                Assertions.assertEquals(0, numOfStudOnTableAfter_1);
            else
                Assertions.assertEquals(numOfStudOnTableBefore_1 - 3, numOfStudOnTableAfter_1);

            if (numOfStudOnTableBefore_2 <= 3)
                Assertions.assertEquals(0, numOfStudOnTableAfter_2);
            else
                Assertions.assertEquals(numOfStudOnTableBefore_2 - 3, numOfStudOnTableAfter_2);

            if (numOfStudOnTableBefore_3 <= 3)
                Assertions.assertEquals(0, numOfStudOnTableAfter_3);
            else
                Assertions.assertEquals(numOfStudOnTableBefore_3 - 3, numOfStudOnTableAfter_3);

            if (numOfStudOnTableBefore_4 <= 3)
                Assertions.assertEquals(0, numOfStudOnTableAfter_4);
            else
                Assertions.assertEquals(numOfStudOnTableBefore_4 - 3, numOfStudOnTableAfter_4);

        });

    }
}