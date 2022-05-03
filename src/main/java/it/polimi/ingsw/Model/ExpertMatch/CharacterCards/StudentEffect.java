package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.SchoolsMembers.StudentBag;
import it.polimi.ingsw.Model.Wizard.Wizard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface StudentEffect{

    default void drawStudent(List<Student> studentsOnCard, int numberOfStudent, StudentBag studentBag){
       for (int i = 0; i < numberOfStudent; i++) {
           studentsOnCard.add(studentBag.drawStudent());
       }
   }

    default void tradeStudentsFromEntrance(List<Student> fromA, List<Student> toB, List<Student> studentsOnCard, Collection<Student> studentsInEntrance){
        studentsOnCard.removeAll(fromA);
        studentsOnCard.addAll(toB);

        studentsInEntrance.removeAll(toB);
        studentsInEntrance.addAll(fromA);
   }



}
