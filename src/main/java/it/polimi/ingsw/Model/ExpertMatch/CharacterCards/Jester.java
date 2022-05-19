package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.Game;

/**Implements the effect from Character card
 * implements the interface StudentEffectCard
 */
public class Jester extends CharacterCard implements StudentEffectCard {
    /**
     * Constructor of the class
     * @param basicMatch current match
     * @param name name of the class
     */
    public Jester(BasicMatch basicMatch, String name) {
        super(basicMatch, name);
        setCost(1);
        drawStudent(getStudentsOnCard(), 6, basicMatch.getGame().getStudentBag());
    }

    /**
     * This method let the player use the card
     * @param match the current match
     * @throws ExceptionGame if the active wizard is not set
     */
    public void useCard(ExpertMatch match) throws ExceptionGame {
        super.useCard(match);
        useJesterCard();
        this.cost++;
    }

    /**
     * This method facilitates the usage of the card
     * @throws ExceptionGame if a move that is not permitted is made or a method fails to return a value
     */
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

    resetCard();
    }





}