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

class MagicianTest {
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
        System.out.println("numbero of archipelagos " + expertMatch.getGame().getArchipelagos().size());

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

    @RepeatedTest(10)
    public void setTestMatch() {
        assertDoesNotThrow(() -> {
            this.setATestMatch();
            wizard1 = expertMatch.getGame().getWizardFromPlayer(player1);
            wizard2 = expertMatch.getGame().getWizardFromPlayer(player2);
            expertMatch.playAssistantsCard(player1, wizard1.getAssistantsDeck().getPlayableAssistants().get(0));
            expertMatch.playAssistantsCard(player2, wizard2.getAssistantsDeck().getPlayableAssistants().get(3));
            //sets a Chef card in the game in position 0
            CharacterCard magician = new Magician( basicMatch2Players,"Magician");
            expertMatch.getCharactersForThisGame().put(0, magician);
            assertEquals(1, magician.getCost());
            assertEquals(1, wizard1.getCoins());
            assertEquals(1, wizard2.getCoins());
            int cost = magician.getCost();

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
            Archipelago effectArchipelago = expertMatch.getGame().getArchipelagos().get((positionMN + getSteps(player1) + 2) % expertMatch.getGame().getArchipelagos().size());

            assertEquals(1, wizard1.getCoins());

            magician.setActiveWizard(wizard1);
            magician.useCard(expertMatch);
            assertEquals(0, wizard1.getCoins());
            assertEquals(cost + 1, magician.getCost());

           expertMatch.moveMotherNature(player1, effectArchipelago);

            int newPositionMN = expertMatch.getPositionOfMotherNature();
            int stepsDone = (newPositionMN + expertMatch.getGame().getArchipelagos().size() - positionMN) %  expertMatch.getGame().getArchipelagos().size();
            Assertions.assertEquals(stepsDone, getSteps(player1) + 2);

            Assertions.assertNull(expertMatch.getActiveMotherNatureCard());

            positionMN = expertMatch.getPositionOfMotherNature();
            Archipelago secondArchipelago = expertMatch.getGame().getArchipelagos().get((positionMN + getSteps(player2) + 3) % expertMatch.getGame().getArchipelagos().size());
            wizard2.addACoin();
            magician.setActiveWizard(wizard2);
            magician.useCard(expertMatch);
            Archipelago finalSecondArchipelago = secondArchipelago;
            Assertions.assertThrows(ExceptionGame.class, ()->expertMatch.moveMotherNature(player2, finalSecondArchipelago));
            secondArchipelago = expertMatch.getGame().getArchipelagos().get((positionMN + getSteps(player2) + 2) % expertMatch.getGame().getArchipelagos().size());
            expertMatch.moveMotherNature(player2, secondArchipelago);

            Archipelago finalArchipelago = expertMatch.getGame().getArchipelagos().get((positionMN + getSteps(player1) + 1) % expertMatch.getGame().getArchipelagos().size());
            Assertions.assertThrows(ExceptionGame.class, ()->expertMatch.moveMotherNature(player1, finalArchipelago));

        });
    }

    @RepeatedTest(10)
    public void match4player_Test(){
        BasicMatch match4players = factoryMatch.newMatch(4);
        Player player3 = new Player("username3");
        Player player4 = new Player("username4");
        List<Player> players = setPlayers(player1, player2);
        players.add(player3);
        players.add(player4);
        ExpertMatch expertMatch4Players = new ExpertMatch(match4players);

        Assertions.assertThrows(ExceptionGame.class, ()-> expertMatch4Players.setGame(players));

        Assertions.assertDoesNotThrow( ()->{
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
            CharacterCard magician = new Magician(match4players,"magician");
            expertMatch4Players.getCharactersForThisGame().put(0, magician);
            assertEquals(1, magician.getCost());
            assertEquals(1, wizard1.getCoins());
            assertEquals(1, wizard2.getCoins());
            assertEquals(1, wizard3.getCoins());
            assertEquals(1, wizard4.getCoins());
            int cost = magician.getCost();
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
            expertMatch4Players.getGame().getArchipelagos().get((positionMN + getSteps(expertMatch4Players, player1)) % expertMatch4Players.getGame().getArchipelagos().size());
            Archipelago effectArchipelago = expertMatch4Players.getGame().getArchipelagos().get((positionMN + getSteps(expertMatch4Players, player1) + 2) % expertMatch4Players.getGame().getArchipelagos().size());

            assertEquals(1, wizard1.getCoins());

            magician.setActiveWizard(wizard1);
            magician.useCard(expertMatch4Players);
            assertEquals(0, wizard1.getCoins());
            assertEquals(cost + 1, magician.getCost());

            expertMatch4Players.moveMotherNature(player1, effectArchipelago);

            int newPositionMN = expertMatch4Players.getPositionOfMotherNature();
            int stepsDone = (newPositionMN + expertMatch4Players.getGame().getArchipelagos().size() - positionMN) %  expertMatch4Players.getGame().getArchipelagos().size();
            Assertions.assertEquals(stepsDone, getSteps(expertMatch4Players, player1) + 2);

            Assertions.assertNull(expertMatch4Players.getActiveMotherNatureCard());

            positionMN = expertMatch4Players.getPositionOfMotherNature();
            Archipelago secondArchipelago = expertMatch4Players.getGame().getArchipelagos().get((positionMN + getSteps(expertMatch4Players, player3) + 3) % expertMatch4Players.getGame().getArchipelagos().size());
            wizard3.addACoin();
            magician.setActiveWizard(wizard3);
            magician.useCard(expertMatch4Players);
            Archipelago finalSecondArchipelago = secondArchipelago;
            Assertions.assertThrows(ExceptionGame.class, ()->expertMatch4Players.moveMotherNature(player3, finalSecondArchipelago));
            secondArchipelago = expertMatch4Players.getGame().getArchipelagos().get((positionMN + getSteps(expertMatch4Players, player3) + 2) % expertMatch4Players.getGame().getArchipelagos().size());
            expertMatch4Players.moveMotherNature(player3, secondArchipelago);

            Archipelago finalArchipelago = expertMatch4Players.getGame().getArchipelagos().get((positionMN + getSteps(expertMatch4Players, player1) + 1) % expertMatch4Players.getGame().getArchipelagos().size());
            Assertions.assertThrows(ExceptionGame.class, ()->expertMatch4Players.moveMotherNature(player1, finalArchipelago));

        });

    }

    public int getSteps(ExpertMatch expertMatch, Player player) throws ExceptionGame{
        Wizard wizard = expertMatch.getGame().getWizardFromPlayer(player);
        return wizard.getRoundAssistantsCard().getStep();
    }

}