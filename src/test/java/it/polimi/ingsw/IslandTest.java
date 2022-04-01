package it.polimi.ingsw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class IslandTest {
    @Test
     void island_ExceptionTest() {
        Island island = new Island();
        Assertions.assertThrows(ExceptionGame.class, () -> island.getTower());
        Wizard w1 = new Wizard("player_test");
        Tower t = new Tower();
        t.setProperty(w1);
        island.setTower(t);
        try{
            System.out.println(island.getTower().getProperty());
        }catch (ExceptionGame e){}
        try {
            Assertions.assertEquals(w1, island.getTower().getProperty());
        } catch (ExceptionGame e) {
        }
    }

    @Test
    void setTower_Test() {
        Island island = new Island();
        Tower t1 = new Tower();
        Wizard wizard1 = new Wizard("player_test_1");
        t1.setProperty(wizard1);
        island.setTower(t1);
        try {
            Assertions.assertEquals(wizard1, island.getTower().getProperty());
        } catch (ExceptionGame e) {
        }

        Tower t2 = new Tower();
        Wizard wizard2 = new Wizard("player_test_2");
        t2.setProperty(wizard2);
        island.setTower(t2);
        try {
            Assertions.assertEquals(wizard2, island.getTower().getProperty());
        } catch (ExceptionGame e) {
        }
    }

    @Test
    void getStudentFilteredByColor_Test(){
        Island island = new Island();
        for (Color c: Color.values()) {
            Student stud=new Student(c);
            Student stud2 = new Student(c);
            island.getStudentInIsland().add(stud);
            island.getStudentInIsland().add(stud2);
        //    Assertions.assertEquals(stud.getColor(), c);
            Assertions.assertEquals(2, island.getStudentFilteredByColor(c).size());
        }
    }
}
