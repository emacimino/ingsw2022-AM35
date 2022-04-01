package it.polimi.ingsw.ModelTest.SchoolsMembersTest;

import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Professor;
import it.polimi.ingsw.Model.Wizard.Wizard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProfessorTest {
    int[] ints = {9, 4};
    @Test
    void IfProfessorExistsAsItIsSupposedToReturnsTrue() {
        Wizard pippo = new Wizard("pippo", ints[0], ints[1]);
        for (Color c: Color.values()) {
            Professor prof = new Professor(c);
            prof.setProperty(pippo);
            assertEquals(pippo, prof.getProperty());
            Assertions.assertEquals(c, prof.getColor());
        }

    }

}
