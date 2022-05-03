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
        usedFriarCard(getArchipelagoEffected(), getActiveStudents().get(0));
    }

    private void setStudentsOnCard() {
        StudentEffect.drawStudent(getStudentsOnCard(), 4, getGame().getStudentBag());
    }


    private void usedFriarCard(Archipelago archipelago, Student chosenStudent) throws ExceptionGame {
        if(getStudentsOnCard().contains(chosenStudent)) {
            archipelago.addStudentInArchipelago(chosenStudent);
            getStudentsOnCard().remove(chosenStudent);
        }
        else
            throw new ExceptionGame("This student is not on the card");

        StudentEffect.drawStudent( getStudentsOnCard(), 1, getGame().getStudentBag());
    }
}

