package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.Wizard;


public class Princess extends CharacterCard implements StudentEffect {

    public Princess(Game game, String name) {
        super(game, name);
        setCost(2);
        setStudentsOnCard();
    }

    @Override
    public void useCard(ExpertMatch match) throws ExceptionGame{
        super.useCard(match);
        usedPrincessCard(getActiveWizard(), getActiveStudents().get(0));
        resetCard();
    }

    private void setStudentsOnCard() {
        drawStudent(getStudentsOnCard(), 4, getGame().getStudentBag());
    }

    private void usedPrincessCard(Wizard wizard, Student chosenStudent) throws ExceptionGame {
        if(getStudentsOnCard().contains(chosenStudent)) {
            wizard.getBoard().addStudentInTable(chosenStudent);
            getStudentsOnCard().remove(chosenStudent);
        }
        else
            throw new ExceptionGame("This student is not on the card");
        drawStudent(getStudentsOnCard(), 1, getGame().getStudentBag());
    }

}
