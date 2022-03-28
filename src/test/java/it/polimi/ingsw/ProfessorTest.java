package it.polimi.ingsw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.Professor.getColor;
import static org.junit.jupiter.api.Assertions.*;

public class ProfessorTest {
    @test
    public boolean IfProfessorExistsAsItIsSupposedToReturnsTrue() {
        for (Color c: Color.values()) {
            Professor prof = new Professor(c, null);
            Assertions.assertEquals(c, Professor.getColor());
        }

    }

}
