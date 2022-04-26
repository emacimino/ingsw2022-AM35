package it.polimi.ingsw.Model.ExpertMatch.StudentEffectsCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.SchoolsMembers.StudentBag;

import java.util.ArrayList;

public class Friar extends StudentEffect {
    private String name;
    private int cost = 1;
    private ArrayList<Student> StudentsOnCard = new ArrayList<>();

    public Friar(StudentBag studentBag) {
        super(studentBag);
    }

    public void placeStudentOnIsland(Archipelago archipelago, Student student) throws ExceptionGame {
        if(StudentsOnCard.contains(student))
            archipelago.addStudentInArchipelago(student);
        else
            throw new ExceptionGame("This student is not on the card");
    }

    public void callFromDeck(Friar friar,StudentBag studentBag){
        friar.drawStudent(4,studentBag);
    }

    public void useCharacterCard(StudentBag studentBag, Archipelago archipelago,Student chosenStudent) throws ExceptionGame {
        this.placeStudentOnIsland(archipelago, chosenStudent);
        this.drawStudent(1,studentBag);
        cost++;
    }

    public int getCost() {
        return cost;
    }
}

