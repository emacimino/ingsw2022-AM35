package it.polimi.ingsw.Model.ExpertMatch.StudentEffectsCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCard;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.SchoolsMembers.StudentBag;
import it.polimi.ingsw.Model.Wizard.Wizard;

import java.util.ArrayList;

public class Friar extends StudentEffect {
    private String name;
    private int cost = 1;
    private ArrayList<Student> StudentsOnCard = new ArrayList<>();

    public Friar(StudentBag studentBag) {
        super();
    }

    public void placeStudentOnIsland(boolean studentIsHere, Archipelago archipelago, Student student) throws ExceptionGame {
        if(studentIsHere)
            archipelago.addStudentInArchipelago(student);
        else
            throw new ExceptionGame("This student is not on the card");
    }

    public void callFromDeck(Friar friar,StudentBag studentBag){
        friar.drawStudent(4,studentBag);
    }

    public void useCharacterCard(Wizard w, Color c, Friar friar,StudentBag studentBag, Archipelago archipelago,Student chosenStudent) throws ExceptionGame {
        friar.placeStudentOnIsland(StudentsOnCard.contains(chosenStudent),archipelago, chosenStudent);
        friar.drawStudent(1,studentBag);
        cost++;
    }

    public int getCost() {
        return cost;
    }
}

