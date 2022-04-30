package it.polimi.ingsw.Model.ExpertMatch.StudentEffectsCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCard;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Island;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Professor;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.SchoolsMembers.StudentBag;
import it.polimi.ingsw.Model.Wizard.Wizard;

import java.util.ArrayList;
import java.util.List;

public abstract class StudentEffect extends CharacterCard {
    protected ArrayList<Student> StudentsOnCard = new ArrayList<>();

    public StudentEffect(Game game) {
        super(game);
    }


    public void drawStudent(int numberOfStudent, StudentBag studentBag){
        for (int i = 0; i < numberOfStudent; i++) {
            StudentsOnCard.add(studentBag.drawStudent());
        }
    }

    public int getCost() {
        return cost;
    }

    @Override
    public void usedFriarCard(Archipelago archipelago, Student chosenStudent) throws ExceptionGame {
        super.usedFriarCard(archipelago, chosenStudent);
    }

    @Override
    public void usedPrincessCard(Wizard wizard, Student chosenStudent) throws ExceptionGame {
        super.usedPrincessCard(wizard, chosenStudent);
    }

    @Override
    public void usedJesterCard(Wizard wizard, List<Student> StudentOnEntranceToBeSwitched, List<Student> StudentOnCardToBeSwitched) throws ExceptionGame {
        super.usedJesterCard(wizard, StudentOnEntranceToBeSwitched, StudentOnCardToBeSwitched);
    }
}
