package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.SchoolsMembers.StudentBag;
import it.polimi.ingsw.Model.Wizard.Wizard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Interface used to assign effects affecting students
 */
public interface StudentEffectCard {
    /**
     * Draw a student from a list
     * @param studentsOnCard list of students that are on card
     * @param numberOfStudent number of student to draw
     * @param studentBag student bag
     */
    default void drawStudent(List<Student> studentsOnCard, int numberOfStudent, StudentBag studentBag) {
        for (int i = 0; i < numberOfStudent; i++) {
            studentsOnCard.add(studentBag.drawStudent());
        }
    }

    /**
     * Trade students
     * @param fromA list of student to trade
     * @param toB list of student to get
     * @param studentsA support list of student to trade
     * @param studentsB support list of student to get
     * @throws ExceptionGame if student can be removed or too much student are removed or too much are added
     */
    default void tradeStudents(List<Student> fromA, List<Student> toB, Collection<Student> studentsA, Collection<Student> studentsB) throws ExceptionGame {
        studentsA.removeAll(fromA);
        studentsA.addAll(toB);

        studentsB.removeAll(toB);
        studentsB.addAll(fromA);
    }

}