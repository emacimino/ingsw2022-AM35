package it.polimi.ingsw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.Professor.getColor;
import static org.junit.jupiter.api.Assertions.*;

public class IslandTest {
    @Test
    void islandTest(){
        Island island=new Island();
        Tower t=new Tower();
        island.setTower(t);
        for (Color c: Color.values()) {
            Student stud=new Student(c);
            island.getStudentInIsland().add(stud);
            Assertions.assertEquals(stud.getColor(), c);
            Assertions.assertEquals(island.getTower(), t);
        }
    }
}
