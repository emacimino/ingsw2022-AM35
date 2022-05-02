package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;


import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.SchoolsMembers.StudentBag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface StudentEffect{

   static void drawStudent(List<Student> studentsOnCard, int numberOfStudent, StudentBag studentBag){
       for (int i = 0; i < numberOfStudent; i++) {
           studentsOnCard.add(studentBag.drawStudent());
       }
   }

   static Collection<Student> getStudents(CharacterCard characterCard) throws ExceptionGame {
       if(characterCard.getStudentsOnCard() != null)
           return characterCard.getStudentsOnCard();
       else throw new ExceptionGame("this card doesn't have students on it");
   }



}
