package it.polimi.ingsw.ModelTest.SchoolsLandsTest;

import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.SchoolsLands.Island;
import it.polimi.ingsw.Model.Wizard.Tower;
import it.polimi.ingsw.Model.Wizard.Wizard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class IslandTest {
    int[] ints = {3, 7};

    /**
     * This methodTest tests that the exception thrown by the methods of the class are correct
     */
    @Test
     void island_ExceptionTest() {
        Island island = new Island();
        Assertions.assertThrows(ExceptionGame.class, island::getTower);
        Wizard w1 = new Wizard("player_test", ints[0], ints[1]);
        Tower t = new Tower(w1, towerColors);
        island.setTower(t);
        Assertions.assertDoesNotThrow(()->island.getTower().getProperty());
        Assertions.assertDoesNotThrow(()->
            Assertions.assertEquals(w1, island.getTower().getProperty())
        );
    }

    /**
     * This methodTest tests the setTower method
     */
    @Test
    void setTower_Test() {
        Island island = new Island();
        Wizard wizard1 = new Wizard("player_test_1", ints[0], ints[1]);
        Tower t1 = new Tower(wizard1, towerColors);
        island.setTower(t1);
        Assertions.assertDoesNotThrow(()->
            Assertions.assertEquals(wizard1, island.getTower().getProperty()));

        Wizard wizard2 = new Wizard("player_test_2", ints[0], ints[1] );
        Tower t2 = new Tower(wizard2, towerColors);
        island.setTower(t2);
        Assertions.assertDoesNotThrow(()->
            Assertions.assertEquals(wizard2, island.getTower().getProperty())
        );
    }

    /**
     * This methodTest tests the getStudentFilteredByColor method
     */
    @Test
    void getStudentFilteredByColor_Test(){
        Island island = new Island();
        for (Color c: Color.values()) {
            Student stud=new Student(c);
            Student stud2 = new Student(c);
            island.getStudentInIsland().add(stud);
            island.getStudentInIsland().add(stud2);
            Assertions.assertEquals(2, island.getStudentFilteredByColor(c).size());
        }
    }
}
