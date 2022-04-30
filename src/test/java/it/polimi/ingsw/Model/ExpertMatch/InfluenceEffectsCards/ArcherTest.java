package it.polimi.ingsw.Model.ExpertMatch.InfluenceEffectsCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.Match;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Professor;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.Wizard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/*class ArcherTest {
    Match testmatch = new Match();
    Wizard wizard1, wizard2;
    ArrayList<Player> players = new ArrayList<>();
    Player player1 = new Player("name1", "username1");
    Player player2 = new Player("name2", "username2");

    private void setPlayers(){
        players.add(player1);
        players.add(player2);
    }*/


   /* public void setTestmatch(Match testmatch) throws ExceptionGame {
        this.testmatch = testmatch;
        this.setPlayers();
        testmatch.setGame(players);
        wizard1 = testmatch.getGame().getWizardFromPlayer(player1);
        wizard2 = testmatch.getGame().getWizardFromPlayer(player2);
        System.out.println(wizard1.getBoard().getStudentsInEntrance());
        System.out.println("insert student color to be passed in table");
        Scanner sc = new Scanner(System.in);
        wizard1.getBoard().addStudentInTable();

    }
*/
  /*  public void testArchipelago(Archipelago archipelago, Color color) {
        Student student = new Student(color);

        this.archipelago = archipelago;
        archipelago.addStudentInArchipelago(student);
    }

    public void setWizard(Wizard wizard, Color color) throws ExceptionGame {
        Student student = new Student(color);
        Professor professor = new Professor(color);
        ArrayList<Student> students = new ArrayList<>();
        students.add(student);

        wizard.placeStudentInEntrance(students);
        wizard.getBoard().setProfessorInTable(professor);
    }

    @Test
    void useCharacterCardTest() throws ExceptionGame {
        this.setTestmatch(testmatch);
        this.setWizard(wizard,Color.GREEN);
        this.setArchipelago(archipelago,Color.GREEN);
        archipelago.placeWizardsTower(wizard);
        assertEquals(archipelago,wizard.getArchipelagosOfWizard());
    }
}*/