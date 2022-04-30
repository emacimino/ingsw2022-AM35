package it.polimi.ingsw.Model.ExpertMatch.InfluenceEffectsCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.Match;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Professor;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.Wizard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BakerTest {

    Match match = new Match();
    ExpertMatch testmatch = new ExpertMatch(match);
    Wizard wizard1, wizard2;
    ArrayList<Player> players = new ArrayList<>();
    Player player1 = new Player("name1", "username1");
    Player player2 = new Player("name2", "username2");
    Student stud1 = new Student(Color.GREEN);
    Student stud2 = new Student(Color.GREEN);
    Student stud3 = new Student(Color.GREEN);
    Student stud4 = new Student(Color.GREEN);

    Professor greenProfessor = new Professor(Color.GREEN);

    private List<Player> setPlayers(Player player1, Player player2){
        players.add(player1);
        players.add(player2);
        return players;
    }

    private void setATestMatch() throws ExceptionGame {
        testmatch.setGame(this.setPlayers(player1,player2));
        testmatch.getGame().setWizards(this.setPlayers(player1,player2));
    }

    @Test
    public void setTestmatch() throws ExceptionGame {
        this.setATestMatch();
        wizard1 = testmatch.getGame().getWizardFromPlayer(player1);
        wizard2 = testmatch.getGame().getWizardFromPlayer(player2);
        testmatch.moveStudentOnBoard(player1,stud1);
        testmatch.moveStudentOnBoard(player1,stud2);
        testmatch.moveStudentOnBoard(player2,stud3);
        testmatch.moveStudentOnBoard(player2,stud4);
        assertEquals(greenProfessor,wizard1.getBoard().getProfessorInTable().get(1));
    }

    @Test
    void getInstanceTest() {
    }

    @Test
    void useBakerCharacterCardTest() {
    }
}