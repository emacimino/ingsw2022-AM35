package it.polimi.ingsw.ModelTest.SchoolsLandsTest;

import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Professor;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Island;
import it.polimi.ingsw.Model.Wizard.Tower;
import it.polimi.ingsw.Model.Wizard.TowerColors;
import it.polimi.ingsw.Model.Wizard.Wizard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class that tests the archipelago class in the model
 */
class ArchipelagoTest {
    int[] ints = {9, 4};
    TowerColors towerColors = TowerColors.Black;
    /**
     * This methodTest tests the calculationInfluenceInArchipelago method and fails if the influence is not correct
     * @param c color to use as parameter
     */
    @ParameterizedTest
    @EnumSource(Color.class)
    void calculationInfluenceInArchipelago_Test(Color c){
        Archipelago archipelago = new Archipelago();
        Wizard wizard = new Wizard("player_Test", ints[0], ints[1]);
        Assertions.assertEquals(0, archipelago.calculateInfluenceInArchipelago(wizard));
        Tower tower = new Tower(wizard, towerColors);
        wizard.getBoard().getTowersInBoard().add(tower);
        Assertions.assertDoesNotThrow(()->
            archipelago.placeWizardsTower(wizard)
        );

        Assertions.assertEquals(1, archipelago.calculateInfluenceInArchipelago(wizard));
        Assertions.assertDoesNotThrow(()->Assertions.assertEquals(wizard, archipelago.getIsle().get(0).getTower().getProperty()));
        Professor prof = new Professor(c);
        Student student = new Student(c);
        wizard.getBoard().setProfessorInTable(prof);
        archipelago.addStudentInArchipelago(student);
        Assertions.assertEquals(1, archipelago.getStudentFromArchipelago().size());
        Assertions.assertEquals(2, archipelago.calculateInfluenceInArchipelago(wizard));
        for (Color color : Color.values())
            if(!color.equals(c)){
                archipelago.addStudentInArchipelago(new Student(color));
            }
        Assertions.assertEquals(2, archipelago.calculateInfluenceInArchipelago(wizard));
    }

    /**
     * This methodTest tests the placeWizardsTower method and fails if the tower is not present after its construction on an isle
     */
    @Test
    void placeWizardsTower_Test(){
        Archipelago archipelago = new Archipelago();
        Wizard wizard = new Wizard("player_string", ints[0], ints[1]);
        Tower t1 = new Tower(wizard, towerColors);
        wizard.getBoard().getTowersInBoard().add(t1);
        AtomicBoolean isPresent = new AtomicBoolean(false);
        Assertions.assertDoesNotThrow(()-> {
            archipelago.placeWizardsTower(wizard);
            for(Island isle : archipelago.getIsle())
                if(isle.getTower().equals(t1))
                    isPresent.set(true);
            Assertions.assertTrue(isPresent.get());
        });

        Tower t2 = new Tower(wizard, towerColors);
        Island island = new Island();
        archipelago.getIsle().add(island);
        wizard.getBoard().getTowersInBoard().add(t1);
        isPresent.set(false);
        Assertions.assertDoesNotThrow(()-> {
            archipelago.placeWizardsTower(wizard);
            for(Island isle : archipelago.getIsle())
                if(isle.getTower().equals(t1))
                    isPresent.set(true);
            Assertions.assertTrue(isPresent.get());
        });
    }

    /**
     * This methodTest tests placeWizardsTower method and fails if an exception is not thrown
     */
    @Test
    void placeWizardsTower_ExceptionTest(){
        Wizard wizard = new Wizard("player_test", ints[0], ints[1]);
        Archipelago archipelago = new Archipelago();
        Assertions.assertThrows(ExceptionGame.class, ()->archipelago.placeWizardsTower(wizard));
    }

    /**
     * This methodTest tests the mergeArchipelago method and fails if the merge of two islands fails
     */
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

    /**
     * This method tests the getStudentFromArchipelago method and fails when a student placement doesn't get counted
     * @param c is the color to pass to the method
     */
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