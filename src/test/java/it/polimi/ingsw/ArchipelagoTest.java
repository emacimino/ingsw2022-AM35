package it.polimi.ingsw;

import it.polimi.ingsw.Charachter.Color;
import it.polimi.ingsw.Charachter.Professor;
import it.polimi.ingsw.Charachter.Student;
import it.polimi.ingsw.Exception.ExceptionGame;
import it.polimi.ingsw.SchoolsLands.Archipelago;
import it.polimi.ingsw.SchoolsLands.Island;
import it.polimi.ingsw.Wizard.Tower;
import it.polimi.ingsw.Wizard.Wizard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;


class ArchipelagoTest {
    int[] ints = {9, 4};

    @ParameterizedTest
    @EnumSource(Color.class)
    void calculationInfluenceInArchipelago_Test(Color c){
        Archipelago archipelago = new Archipelago();
        Wizard wizard = new Wizard("player_Test", ints[0], ints[1]);
        Assertions.assertEquals(0, archipelago.calculateInfluenceInArchipelago(wizard));
        Tower tower = new Tower();
        tower.setProperty(wizard);
        wizard.getBoard().getTowersInBoard().add(tower);
        try {
            archipelago.placeWizardsTower(wizard);
        }catch (ExceptionGame e){}
        Assertions.assertEquals(1, archipelago.calculateInfluenceInArchipelago(wizard));

        Professor prof = new Professor(c);
        Student student = new Student(c);
        wizard.getBoard().setProfessorInTable(prof);
        archipelago.addStudentInArchipelago(student);
        Assertions.assertEquals(2, archipelago.calculateInfluenceInArchipelago(wizard));
        for (Color color : Color.values())
            if(!color.equals(c)){
                archipelago.addStudentInArchipelago(new Student(color));
            }
        Assertions.assertEquals(2, archipelago.calculateInfluenceInArchipelago(wizard));
    }

    @Test
    void placeWizardsTower_Test(){
        Archipelago archipelago = new Archipelago();
        Wizard wizard = new Wizard("player_string", ints[0], ints[1]);
        Tower t1 = new Tower();
        t1.setProperty(wizard);
        wizard.getBoard().getTowersInBoard().add(t1);
        boolean isPresent = false;
        try{
            archipelago.placeWizardsTower(wizard);
            for(Island isle : archipelago.getIsle())
                if(isle.getTower().equals(t1))
                    isPresent = true;
            Assertions.assertTrue(isPresent);
        }catch(ExceptionGame e){}

        Tower t2 = new Tower();
        t2.setProperty(wizard);
        Island island = new Island();
        archipelago.getIsle().add(island);
        wizard.getBoard().getTowersInBoard().add(t1);
        isPresent = false;
        try{
            archipelago.placeWizardsTower(wizard);
            for(Island isle : archipelago.getIsle())
                if(isle.getTower().equals(t1))
                    isPresent = true;
            Assertions.assertTrue(isPresent);
        }catch(ExceptionGame e){}
    }

    @Test
    void placeWizardsTower_ExceptionTest(){
        Wizard wizard = new Wizard("player_test", ints[0], ints[1]);
        Archipelago archipelago = new Archipelago();
        Assertions.assertThrows(ExceptionGame.class, ()->archipelago.placeWizardsTower(wizard));
    }

    @Test
    void mergeArchipelago_Test(){
        Archipelago archipelago_1 = new Archipelago();
        Archipelago archipelago_2 = new Archipelago();
        Island isle = archipelago_2.getIsle().get(0);
        archipelago_1.mergeArchipelago(archipelago_2);
        Assertions.assertTrue(archipelago_2.getIsle().isEmpty());
        Assertions.assertEquals(2, archipelago_1.getIsle().size());
        Assertions.assertTrue(archipelago_1.getIsle().contains(isle));
    }

    @ParameterizedTest
    @EnumSource(Color.class)
    void getStudentFromArchipelago_Test(Color c){
        Student student = new Student(c);
        Archipelago archipelago = new Archipelago();
        Assertions.assertTrue(archipelago.getStudentFromArchipelago().isEmpty());
        archipelago.addStudentInArchipelago(student);
        Assertions.assertTrue(archipelago.getStudentFromArchipelago().contains(student));

    }

}