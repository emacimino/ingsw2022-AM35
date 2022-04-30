package it.polimi.ingsw.Model.ExpertMatch.StudentEffectsCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.SchoolsMembers.StudentBag;
import it.polimi.ingsw.Model.Wizard.Wizard;

import java.util.ArrayList;

public class Princess extends StudentEffect{
    private int cost = 2;
    private ArrayList<Student> StudentsOnCard = new ArrayList<>();

    public Princess(Game game) {
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

    public void placeStudentOnTable(Wizard wizard, Student student) throws ExceptionGame {
        if(StudentsOnCard.contains(student))
            wizard.placeStudentOnTable(student);
        else
            throw new ExceptionGame("This student is not on the card");
    }

    public void useCharacterCard(Wizard wizard,Student chosenStudent) throws ExceptionGame {
        this.placeStudentOnTable(wizard, chosenStudent);
        this.drawStudent(1, game.getStudentBag());
    }

    @Override
    public void usedPrincessCard(Wizard wizard, Student chosenStudent) throws ExceptionGame {
        this.useCharacterCard(wizard, chosenStudent);
        cost++;
    }
}
