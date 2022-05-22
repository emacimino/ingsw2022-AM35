package it.polimi.ingsw.CLIView;
import it.polimi.ingsw.Client.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CLITest {
    @Test
    public void test1(){
        System.out.println(Printable.bigTitle);
        Printable.printBoard(3);
    }

}