package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.Wizard;
import it.polimi.ingsw.NetworkUtilities.CurrentGameMessage;

import java.io.Serial;
import java.io.Serializable;

/**Implements the effect from Character card
 * and uses StudentEffectCard interface
 */

public class Princess extends CharacterCard implements StudentEffectCard , Serializable {
    @Serial
    private final static long serialVersionUID = 8092247049282770460L;
    /**
     * Constructor of the class
     * @param basicMatch current match
     * @param name name of the card
     */
    public Princess(BasicMatch basicMatch, String name) {
        super(basicMatch, name);
        setCost(2);
        drawStudent(getStudentsOnCard(), 4, basicMatch.getGame().getStudentBag());
    }

    /**
     * This method let the player use the card
     * @param match the current match
     * @throws ExceptionGame if the active wizard is not set or a method call fails
     */
    @Override
    public void useCard(ExpertMatch match) throws ExceptionGame{
        super.useCard(match);
        usedPrincessCard(getActiveWizard(), getActiveStudents().get(0));
        paymentOfTheCard(match);
    }

    /**
     * This method facilitates the usage of the card
     * @param wizard wizard using the card
     * @param chosenStudent the targeted student
     * @throws ExceptionGame if a move that is not permitted is made or a method fails to return a value
     */
    private void usedPrincessCard(Wizard wizard, Student chosenStudent) throws ExceptionGame {
        if(getStudentsOnCard().contains(chosenStudent)) {
            wizard.getBoard().addStudentInTable(chosenStudent);
            getStudentsOnCard().remove(chosenStudent);
            getBasicMatch().lookUpProfessor(chosenStudent.getColor());
        }
        else
            throw new ExceptionGame("This student is not on the card");
        drawStudent(getStudentsOnCard(), 1, getBasicMatch().getGame().getStudentBag());
    }

}
