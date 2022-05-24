package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.FactoryMatch;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.Wizard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HerbalistTest {
    private final FactoryMatch factoryMatch = new FactoryMatch();
    private final BasicMatch basicMatch2Players = factoryMatch.newMatch(2);
    private final ExpertMatch expertMatch = new ExpertMatch(basicMatch2Players);
    Wizard wizard1, wizard2;
    private final Player player1 = new Player("username1");
    private final Player player2 = new Player("username2");

    private List<Player> setPlayers(Player player1, Player player2) {
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

    public void printGame() {
        System.out.println("\n PRINTING STATE OF GAME: ");
        System.out.println("numbero of archipelagos " + expertMatch.getGame().getArchipelagos().size());

        System.out.println("professor in game: " + expertMatch.getGame().getProfessors());
        System.out.println("action order in round: " + expertMatch.getActionPhaseOrderOfPlayers());
        System.out.println("position of MN " + expertMatch.getPositionOfMotherNature());
        for (Wizard w : expertMatch.getGame().getWizards()) {
            System.out.println("--->" + w);
            System.out.println("towers " + w.getBoard().getTowersInBoard().size());
            System.out.println("assistantCard " + w.getRoundAssistantsCard());
            System.out.println("number of students in entrance " + w.getBoard().getStudentsInEntrance().size());
            System.out.println("professors in table : " + w.getBoard().getProfessorInTable());
        }
        System.out.println("END OF PRINT \n");
    }

    @RepeatedTest(10)
    public void setTestMatch() {
        assertDoesNotThrow(() -> {
            this.setATestMatch();
            wizard1 = expertMatch.getGame().getWizardFromPlayer(player1);
            wizard2 = expertMatch.getGame().getWizardFromPlayer(player2);
            expertMatch.playAssistantsCard(player1, wizard1.getAssistantsDeck().getPlayableAssistants().get(0));
            expertMatch.playAssistantsCard(player2, wizard2.getAssistantsDeck().getPlayableAssistants().get(1));
            //sets a Chef card in the game in position 0
            CharacterCard herbalist = new Herbalist(basicMatch2Players, "Herbalist");
            expertMatch.getCharactersForThisGame().put(herbalist.getName(), herbalist);
            assertEquals(2, herbalist.getCost());
            assertEquals(1, wizard1.getCoins());
            assertEquals(1, wizard2.getCoins());
            int cost = herbalist.getCost();

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

            int positionMN = expertMatch.getPositionOfMotherNature();
            Archipelago effectArchipelago_1 = expertMatch.getGame().getArchipelagos().get((positionMN + getSteps(player1)) % expertMatch.getGame().getArchipelagos().size());
            Archipelago effectArchipelago_2 = expertMatch.getGame().getArchipelagos().get((positionMN + getSteps(player1) + 1) % expertMatch.getGame().getArchipelagos().size());
            Archipelago effectArchipelago_3 = expertMatch.getGame().getArchipelagos().get((positionMN + getSteps(player1) + 2) % expertMatch.getGame().getArchipelagos().size());
            Archipelago effectArchipelago_4 = expertMatch.getGame().getArchipelagos().get((positionMN + getSteps(player1) + 3) % expertMatch.getGame().getArchipelagos().size());
            Archipelago effectArchipelago_5 = expertMatch.getGame().getArchipelagos().get((positionMN + getSteps(player1) + 4) % expertMatch.getGame().getArchipelagos().size());

            wizard1.addACoin();
            herbalist.setActiveWizard(wizard1);
            herbalist.setArchipelagoEffected(effectArchipelago_1);
            herbalist.useCard(expertMatch);
            assertEquals(cost + 1, herbalist.getCost());
            Assertions.assertTrue(effectArchipelago_1.isProhibition());
            Assertions.assertEquals(3, expertMatch.getActiveProhibitionCard().getProhibitionPass());

            wizard2.addACoin();
            wizard2.addACoin();
            herbalist.setActiveWizard(wizard2);
            herbalist.setArchipelagoEffected(effectArchipelago_2);
            herbalist.useCard(expertMatch);
            Assertions.assertTrue(effectArchipelago_2.isProhibition());

            wizard1.addACoin();
            wizard1.addACoin();
            wizard1.addACoin();
            wizard1.addACoin();
            herbalist.setActiveWizard(wizard1);
            herbalist.setArchipelagoEffected(effectArchipelago_3);
            herbalist.useCard(expertMatch);

            wizard2.addACoin();
            wizard2.addACoin();
            wizard2.addACoin();
            wizard2.addACoin();
            wizard2.addACoin();
            herbalist.setActiveWizard(wizard2);
            herbalist.setArchipelagoEffected(effectArchipelago_4);
            herbalist.useCard(expertMatch);

            wizard1.addACoin();
            wizard1.addACoin();
            wizard1.addACoin();
            wizard1.addACoin();
            wizard1.addACoin();
            wizard1.addACoin();
            herbalist.setActiveWizard(wizard1);
            herbalist.setArchipelagoEffected(effectArchipelago_5);
            Assertions.assertThrows(ExceptionGame.class, () -> herbalist.useCard(expertMatch));

            Assertions.assertEquals(0, expertMatch.getActiveProhibitionCard().getProhibitionPass());
            Assertions.assertTrue(effectArchipelago_1.isProhibition());
            expertMatch.moveMotherNature(player1, effectArchipelago_1);
            Assertions.assertEquals(8, wizard1.getBoard().getTowersInBoard().size());
            Assertions.assertEquals(8, wizard2.getBoard().getTowersInBoard().size());
            Assertions.assertEquals(1, expertMatch.getActiveProhibitionCard().getProhibitionPass());

            Assertions.assertTrue(effectArchipelago_2.isProhibition());
            expertMatch.moveMotherNature(player2, effectArchipelago_2);
            Assertions.assertEquals(8, wizard1.getBoard().getTowersInBoard().size());
            Assertions.assertEquals(8, wizard2.getBoard().getTowersInBoard().size());
            Assertions.assertEquals(2, expertMatch.getActiveProhibitionCard().getProhibitionPass());

            expertMatch.playAssistantsCard(player1, wizard1.getAssistantsDeck().getPlayableAssistants().get(0));
            expertMatch.playAssistantsCard(player2, wizard2.getAssistantsDeck().getPlayableAssistants().get(1));
            Assertions.assertTrue(effectArchipelago_3.isProhibition());
            expertMatch.moveMotherNature(player1, effectArchipelago_3);
            Assertions.assertEquals(8, wizard1.getBoard().getTowersInBoard().size());
            Assertions.assertEquals(8, wizard2.getBoard().getTowersInBoard().size());
            Assertions.assertEquals(3, expertMatch.getActiveProhibitionCard().getProhibitionPass());

            Assertions.assertTrue(effectArchipelago_4.isProhibition());
            expertMatch.moveMotherNature(player1, effectArchipelago_4);
            Assertions.assertEquals(8, wizard1.getBoard().getTowersInBoard().size());
            Assertions.assertEquals(8, wizard2.getBoard().getTowersInBoard().size());
            Assertions.assertEquals(4, expertMatch.getActiveProhibitionCard().getProhibitionPass());


        });
    }


    @RepeatedTest(10)
    public void match4player_Test() {
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
            expertMatch4Players.playAssistantsCard(player2, wizard2.getAssistantsDeck().getPlayableAssistants().get(1));
            Wizard wizard3 = expertMatch4Players.getGame().getWizardFromPlayer(player3);
            Wizard wizard4 = expertMatch4Players.getGame().getWizardFromPlayer(player4);
            expertMatch4Players.playAssistantsCard(player3, wizard3.getAssistantsDeck().getPlayableAssistants().get(2));
            expertMatch4Players.playAssistantsCard(player4, wizard4.getAssistantsDeck().getPlayableAssistants().get(3));
            CharacterCard herbalist = new Herbalist(match4players, "Herbalist");
            expertMatch4Players.getCharactersForThisGame().put(herbalist.getName(), herbalist);
            assertEquals(2, herbalist.getCost());
            assertEquals(1, wizard1.getCoins());
            assertEquals(1, wizard2.getCoins());
            assertEquals(1, wizard3.getCoins());
            assertEquals(1, wizard4.getCoins());
            int cost = herbalist.getCost();
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

            int positionMN = expertMatch4Players.getPositionOfMotherNature();
            Archipelago effectArchipelago_1 = expertMatch4Players.getGame().getArchipelagos().get((positionMN + getSteps(expertMatch4Players, player1)) % expertMatch4Players.getGame().getArchipelagos().size());
            Archipelago effectArchipelago_2 = expertMatch4Players.getGame().getArchipelagos().get((positionMN + getSteps(expertMatch4Players, player1) + 1) % expertMatch4Players.getGame().getArchipelagos().size());
            Archipelago effectArchipelago_3 = expertMatch4Players.getGame().getArchipelagos().get((positionMN + getSteps(expertMatch4Players, player1) + 2) % expertMatch4Players.getGame().getArchipelagos().size());
            Archipelago effectArchipelago_4 = expertMatch4Players.getGame().getArchipelagos().get((positionMN + getSteps(expertMatch4Players, player1) + 3) % expertMatch4Players.getGame().getArchipelagos().size());
            Archipelago effectArchipelago_5 = expertMatch4Players.getGame().getArchipelagos().get((positionMN + getSteps(expertMatch4Players, player1) + 4) % expertMatch4Players.getGame().getArchipelagos().size());

            wizard1.addACoin();
            herbalist.setActiveWizard(wizard1);
            herbalist.setArchipelagoEffected(effectArchipelago_1);
            herbalist.useCard(expertMatch4Players);
            assertEquals(cost + 1, herbalist.getCost());
            Assertions.assertTrue(effectArchipelago_1.isProhibition());
            Assertions.assertEquals(3, expertMatch4Players.getActiveProhibitionCard().getProhibitionPass());

            wizard2.addACoin();
            wizard2.addACoin();
            herbalist.setActiveWizard(wizard2);
            herbalist.setArchipelagoEffected(effectArchipelago_2);
            herbalist.useCard(expertMatch4Players);
            Assertions.assertTrue(effectArchipelago_2.isProhibition());

            wizard3.addACoin();
            wizard3.addACoin();
            wizard3.addACoin();
            wizard3.addACoin();
            herbalist.setActiveWizard(wizard3);
            herbalist.setArchipelagoEffected(effectArchipelago_3);
            herbalist.useCard(expertMatch4Players);

            wizard4.addACoin();
            wizard4.addACoin();
            wizard4.addACoin();
            wizard4.addACoin();
            wizard4.addACoin();
            herbalist.setActiveWizard(wizard4);
            herbalist.setArchipelagoEffected(effectArchipelago_4);
            herbalist.useCard(expertMatch4Players);

            wizard1.addACoin();
            wizard1.addACoin();
            wizard1.addACoin();
            wizard1.addACoin();
            wizard1.addACoin();
            wizard1.addACoin();
            herbalist.setActiveWizard(wizard1);
            herbalist.setArchipelagoEffected(effectArchipelago_5);
            Assertions.assertThrows(ExceptionGame.class, () -> herbalist.useCard(expertMatch4Players));

            Assertions.assertEquals(0, expertMatch4Players.getActiveProhibitionCard().getProhibitionPass());
            Assertions.assertTrue(effectArchipelago_1.isProhibition());
            expertMatch4Players.moveMotherNature(player1, effectArchipelago_1);
            Assertions.assertEquals(8, wizard1.getBoard().getTowersInBoard().size());
            Assertions.assertEquals(0, wizard2.getBoard().getTowersInBoard().size());
            Assertions.assertEquals(8, wizard3.getBoard().getTowersInBoard().size());
            Assertions.assertEquals(0, wizard4.getBoard().getTowersInBoard().size());
            Assertions.assertEquals(1, expertMatch4Players.getActiveProhibitionCard().getProhibitionPass());

            Assertions.assertTrue(effectArchipelago_2.isProhibition());
            expertMatch4Players.moveMotherNature(player2, effectArchipelago_2);
            Assertions.assertEquals(8, wizard1.getBoard().getTowersInBoard().size());
            Assertions.assertEquals(0, wizard2.getBoard().getTowersInBoard().size());
            Assertions.assertEquals(8, wizard3.getBoard().getTowersInBoard().size());
            Assertions.assertEquals(0, wizard4.getBoard().getTowersInBoard().size());
            Assertions.assertEquals(2, expertMatch4Players.getActiveProhibitionCard().getProhibitionPass());

            Assertions.assertTrue(effectArchipelago_3.isProhibition());
            expertMatch4Players.moveMotherNature(player3, effectArchipelago_3);
            Assertions.assertEquals(8, wizard1.getBoard().getTowersInBoard().size());
            Assertions.assertEquals(0, wizard2.getBoard().getTowersInBoard().size());
            Assertions.assertEquals(8, wizard3.getBoard().getTowersInBoard().size());
            Assertions.assertEquals(0, wizard4.getBoard().getTowersInBoard().size());
            Assertions.assertEquals(3, expertMatch4Players.getActiveProhibitionCard().getProhibitionPass());

            Assertions.assertTrue(effectArchipelago_4.isProhibition());
            expertMatch4Players.moveMotherNature(player4, effectArchipelago_4);
            Assertions.assertEquals(8, wizard1.getBoard().getTowersInBoard().size());
            Assertions.assertEquals(0, wizard2.getBoard().getTowersInBoard().size());
            Assertions.assertEquals(8, wizard3.getBoard().getTowersInBoard().size());
            Assertions.assertEquals(0, wizard4.getBoard().getTowersInBoard().size());
            Assertions.assertEquals(4, expertMatch4Players.getActiveProhibitionCard().getProhibitionPass());



        });


    }
    public int getSteps(ExpertMatch expertMatch, Player player) throws ExceptionGame{
        Wizard wizard = expertMatch.getGame().getWizardFromPlayer(player);
        return wizard.getRoundAssistantsCard().getStep();
    }
}