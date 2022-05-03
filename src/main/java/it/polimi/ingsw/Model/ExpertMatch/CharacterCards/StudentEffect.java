package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.SchoolsMembers.StudentBag;
import it.polimi.ingsw.Model.Wizard.Wizard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface StudentEffect {

    default void drawStudent(List<Student> studentsOnCard, int numberOfStudent, StudentBag studentBag) {
        for (int i = 0; i < numberOfStudent; i++) {
            studentsOnCard.add(studentBag.drawStudent());
        }
    }

    default void tradeStudents(List<Student> fromA, List<Student> toB, Collection<Student> studentsA, Collection<Student> studentsB) throws ExceptionGame {
        studentsA.removeAll(fromA);
        studentsA.addAll(toB);

        studentsB.removeAll(toB);
        studentsB.addAll(fromA);
    }

}