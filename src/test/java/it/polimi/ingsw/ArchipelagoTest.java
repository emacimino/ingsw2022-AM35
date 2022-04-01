package it.polimi.ingsw;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class ArchipelagoTest {

    @Test
    void archipelagoCreation(){
        Archipelago a= new Archipelago();
        Assertions.assertTrue(!a.getIsle().isEmpty());
    }
    @Test
    void mergeArchipelago(){
        Archipelago a = new Archipelago();
        Archipelago b = new Archipelago();
        for (Color c:
             Color.values()) {
            Student s1= new Student(c);
            a.addStudentInArchipelago(s1);
            Student s2= new Student(c);
            b.addStudentInArchipelago(s2);
        }
        a.mergeArchipelago(b);
        Assertions.assertEquals(2, a.getIsle().size());
        Assertions.assertEquals(0, b.getIsle().size());
    }
    @Test
    void mergeAndCalculateInfluence(){
        Wizard player1 = new Wizard("player1");
        Wizard player2 = new Wizard("player2");
        Archipelago a = new Archipelago();
        Archipelago b = new Archipelago();
        Professor p = new Professor(Color.YELLOW);
        for (Color c:
                Color.values()) {
            Student s1= new Student(c);
            a.addStudentInArchipelago(s1);
            Student s2= new Student(c);
            b.addStudentInArchipelago(s2);
        }
        player1.board.setProfessorInTable(p);
        Assertions.assertEquals(1, a.calculateInfluenceInArchipelago(player1));
        a.mergeArchipelago(b);
        Assertions.assertEquals(2, a.getIsle().size());
        Assertions.assertEquals(0, b.getIsle().size());
        Assertions.assertEquals(2, a.calculateInfluenceInArchipelago(player1));
    }

}
