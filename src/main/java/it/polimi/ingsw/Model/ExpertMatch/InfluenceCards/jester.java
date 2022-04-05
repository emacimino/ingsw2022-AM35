package it.polimi.ingsw.Model.ExpertMatch.InfluenceCards;

import it.polimi.ingsw.Model.ExpertMatch.CharacterCard;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.SchoolsMembers.StudentBag;
import it.polimi.ingsw.Model.Wizard.Wizard;

import java.util.ArrayList;


public class jester implements CharacterCard {
    private int cost = 1;
    ArrayList<Student> StudentsOnCard = new ArrayList<>();

    //a inizio partita se pescata, pescare 6 carte da qui
    //manca la classe deck ed Expert, aggiungere dopo
    private void setStudentsOnCard(StudentBag studentBag){
        StudentsOnCard.add(studentBag.drawStudent());
        StudentsOnCard.add(studentBag.drawStudent());
        StudentsOnCard.add(studentBag.drawStudent());
        StudentsOnCard.add(studentBag.drawStudent());
        StudentsOnCard.add(studentBag.drawStudent());
        StudentsOnCard.add(studentBag.drawStudent());
    }

    @Override
    public void useCharacterCard(Wizard w) {

        w.getBoard().getStudentsInEntrance();
    }
}
