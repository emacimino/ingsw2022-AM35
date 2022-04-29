package it.polimi.ingsw.Model.ExpertMatch.StudentEffectsCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.SchoolsMembers.StudentBag;
import it.polimi.ingsw.Model.Wizard.Wizard;

import java.util.ArrayList;

public class Friar extends StudentEffect {
    private String name;
    private int cost = 1;
    private ArrayList<Student> StudentsOnCard = new ArrayList<>();

    public Friar(Game game) {
        super(game);
        this.setStudentsOnCard();
    }

    @Override
    public void drawStudent(int numberOfStudent, StudentBag studentBag) {
        super.drawStudent(numberOfStudent, studentBag);
    }

    public void setStudentsOnCard() {
        StudentsOnCard.add(game.getStudentBag().drawStudent());
        StudentsOnCard.add(game.getStudentBag().drawStudent());
        StudentsOnCard.add(game.getStudentBag().drawStudent());
        StudentsOnCard.add(game.getStudentBag().drawStudent());
    }

    public void placeStudentOnIsland(Archipelago archipelago, Student student) throws ExceptionGame {
        if(StudentsOnCard.contains(student))
            archipelago.addStudentInArchipelago(student);
        else
            throw new ExceptionGame("This student is not on the card");
    }

    public void useCharacterCard(Archipelago archipelago,Student chosenStudent) throws ExceptionGame {
        this.placeStudentOnIsland(archipelago, chosenStudent);
        this.drawStudent(1, game.getStudentBag());
        this.cost++;
    }

    @Override
    public void usedFriarCard(Archipelago archipelago, Student chosenStudent) throws ExceptionGame {
        this.useCharacterCard(archipelago,chosenStudent);
    }
}

