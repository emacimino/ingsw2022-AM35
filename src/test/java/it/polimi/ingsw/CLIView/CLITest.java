package it.polimi.ingsw.CLIView;
import it.polimi.ingsw.Client.*;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Professor;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CLITest {
    @Test
    public void test1(){
        List<Professor> p = List.of(new Professor(Color.GREEN), new Professor(Color.PINK));
        List<Student> s = List.of(new Student(Color.GREEN), new Student(Color.PINK),new Student(Color.GREEN), new Student(Color.PINK),new Student(Color.GREEN), new Student(Color.PINK),new Student(Color.GREEN), new Student(Color.PINK));
        System.out.println(Printable.bigTitle);
        Printable.printBoardTowers(7, "Gray");
        //Printable.printBoardProfessorAndTables(p, s);
        Printable.printEntrance(s);
    }

}