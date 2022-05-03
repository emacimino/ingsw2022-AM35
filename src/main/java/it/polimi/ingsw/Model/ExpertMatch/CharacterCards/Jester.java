package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.SchoolsMembers.Student;

import java.util.Collection;
import java.util.List;

public class Jester extends CharacterCard implements StudentEffect {

    public Jester(Game game, String name) {
        super(game, name);
        setCost(1);
        drawStudent(getStudentsOnCard(), 6, getGame().getStudentBag());
    }

    public void useCard(ExpertMatch match) throws ExceptionGame {
        super.useCard(match);
        useJesterCard();
        resetCard();
    }

    private void useJesterCard() throws ExceptionGame {
        if (getActiveStudents().size() == getPassiveStudents().size() && getActiveStudents().size() < 4) {
            if (getStudentsOnCard().containsAll(getActiveStudents())) {
                if (getActiveWizard().getBoard().getStudentsInEntrance().containsAll(getPassiveStudents())) {
                    tradeStudents(getActiveStudents(), getPassiveStudents(), getStudentsOnCard(), getActiveWizard().getBoard().getStudentsInEntrance());
                } else
                    throw new ExceptionGame("The entrance does not contains all the students selected");
            } else
                throw new ExceptionGame("The Jester Card does not contains all the students selected");
        } else
            throw new ExceptionGame("The list of students to trade have not the same number or there have been selected more than 3 students");
    }





}