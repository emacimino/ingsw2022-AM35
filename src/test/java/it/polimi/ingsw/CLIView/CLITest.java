package it.polimi.ingsw.CLIView;
import it.polimi.ingsw.Client.Cli.Printable;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Island;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Professor;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.Tower;
import it.polimi.ingsw.Model.Wizard.TowerColors;
import it.polimi.ingsw.Model.Wizard.Wizard;
import org.junit.jupiter.api.Test;

import java.util.List;

class CLITest {
    @Test
    public void test1(){
        List<Professor> p = List.of(new Professor(Color.GREEN), new Professor(Color.PINK));
        List<Student> s = List.of(new Student(Color.GREEN), new Student(Color.PINK),new Student(Color.GREEN), new Student(Color.PINK),new Student(Color.GREEN), new Student(Color.PINK),new Student(Color.GREEN), new Student(Color.PINK));
        System.out.println(Printable.bigTitle);
        Printable.printBoardTowers(7, "Gray");
        //Printable.printBoardProfessorAndTables(p, s);
        Printable.printEntranceAndCoins(s, 5);
    }

    @Test
    public void test2(){
        List<Professor> p = List.of(new Professor(Color.GREEN), new Professor(Color.PINK));
        List<Student> s = List.of(new Student(Color.GREEN), new Student(Color.PINK),new Student(Color.GREEN), new Student(Color.PINK),new Student(Color.GREEN), new Student(Color.PINK),new Student(Color.GREEN), new Student(Color.PINK));
        System.out.println(Printable.bigTitle);
        Archipelago archipelago = new Archipelago();
        archipelago.setMotherNaturePresence(true);
        Wizard w = new Wizard("Wizard1", 7 , 4);

        Island island = new Island();
        island.setTower(new Tower(w, TowerColors.Black));
        island.getStudentInIsland().add(new Student(Color.PINK));
        island.getStudentInIsland().add(new Student(Color.PINK));
        island.getStudentInIsland().add(new Student(Color.PINK));


        archipelago.getIsle().add(island);

        Island island1 = new Island();
        island.setTower(new Tower(w, TowerColors.Gray));
        island.getStudentInIsland().add(new Student(Color.PINK));
        island.getStudentInIsland().add(new Student(Color.PINK));
        island.getStudentInIsland().add(new Student(Color.PINK));


        archipelago.getIsle().add(island1);
        Printable.printArchipelago(archipelago);
    }
}