package it.polimi.ingsw.Model.ExpertMatch.StudentEffectsCards;

import it.polimi.ingsw.Model.ExpertMatch.CharacterCard;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.SchoolsMembers.StudentBag;

import java.util.ArrayList;

public abstract class StudentEffect extends CharacterCard {
    private int cost;
    protected ArrayList<Student> StudentsOnCard = new ArrayList<>();

    public StudentEffect(StudentBag studentBag) {
        super(studentBag);
    }


    public void drawStudent(int numberOfStudent, StudentBag studentBag){
        for (int i = 0; i < numberOfStudent; i++) {
            StudentsOnCard.add(studentBag.drawStudent());
        }
    }

    public void placeStudentOn(){

    }

    public int getCost() {
        return cost;
    }
}
