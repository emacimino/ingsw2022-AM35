package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Student;

import java.util.ArrayList;

public class Friar extends CharacterCard implements StudentEffect {

    public Friar(Game game, String name) {
        super(game, name);
        setCost(1);
        setStudentsOnCard();
    }

    @Override
    public void useCard(ExpertMatch match) throws ExceptionGame{
        super.useCard(match);
        usedFriarCard(getActiveStudents().get(0));
        resetCard();
    }

    private void setStudentsOnCard() {
        drawStudent(getStudentsOnCard(), 4, getGame().getStudentBag());
    }


    private void usedFriarCard(Student chosenStudent) throws ExceptionGame {
        if(getStudentsOnCard().contains(chosenStudent)) {
            getArchipelagoEffected().addStudentInArchipelago(chosenStudent);
            getStudentsOnCard().remove(chosenStudent);

        }
        else
            throw new ExceptionGame("This student is not on the card");

        drawStudent( getStudentsOnCard(), 1, getGame().getStudentBag());
    }
}

