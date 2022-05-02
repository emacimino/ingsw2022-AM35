package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.Wizard;

import java.util.ArrayList;

public class Princess extends CharacterCard implements StudentEffect {

    public Princess(Game game, String name) {
        super(game, name);
        setCost(2);
        super.studentsOnCard = new ArrayList<>();
        setStudentsOnCard();
    }

    @Override
    public void useCard(ExpertMatch match) throws ExceptionGame{
        super.useCard(match);
        usedPrincessCard(activeWizard, activeStudents.get(0));
    }

    private void setStudentsOnCard() {
        StudentEffect.drawStudent(super.studentsOnCard, 4, game.getStudentBag());
    }

    private void usedPrincessCard(Wizard wizard, Student chosenStudent) throws ExceptionGame {
        if(super.studentsOnCard.contains(chosenStudent)) {
            wizard.getBoard().addStudentInTable(chosenStudent);
            super.studentsOnCard.remove(chosenStudent);
        }
        else
            throw new ExceptionGame("This student is not on the card");
        StudentEffect.drawStudent(super.studentsOnCard, 1, game.getStudentBag());
    }

}
