package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.SchoolsMembers.Student;

public class Friar extends CharacterCard implements StudentEffectCard {

    public Friar(BasicMatch basicMatch, String name) {
        super(basicMatch, name);
        setCost(1);
        drawStudent(getStudentsOnCard(), 4, basicMatch.getGame().getStudentBag());
    }

    @Override
    public void useCard(ExpertMatch match) throws ExceptionGame{
        super.useCard(match);
        usedFriarCard(getActiveStudents().get(0));
        this.cost++;
    }


    private void usedFriarCard(Student chosenStudent) throws ExceptionGame {
        if(getStudentsOnCard().contains(chosenStudent)) {
            getArchipelagoEffected().addStudentInArchipelago(chosenStudent);
            getStudentsOnCard().remove(chosenStudent);

        }
        else
            throw new ExceptionGame("This student is not on the card");

        drawStudent( getStudentsOnCard(), 1, getBasicMatch().getGame().getStudentBag());

        resetCard();
    }
}

