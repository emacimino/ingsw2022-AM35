package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.SchoolsMembers.Student;


/**Implements the effect from Character card
 * and uses InfluenceEffectCard interface
 */

public class Friar extends CharacterCard implements StudentEffectCard {
    /**
     * Constructor of the class
     * @param basicMatch current match
     * @param name name of the card
     */
    public Friar(BasicMatch basicMatch, String name) {
        super(basicMatch, name);
        setCost(1);
        drawStudent(getStudentsOnCard(), 4, basicMatch.getGame().getStudentBag());
    }

    /**
     * This method let the player use the card
     * @param match the current match
     * @throws ExceptionGame if active wizard is not set
     */
    @Override
    public void useCard(ExpertMatch match) throws ExceptionGame{
        super.useCard(match);
        usedFriarCard(getActiveStudents().get(0));
        this.cost++;
    }

    /**
     * This method facilitates the usage of the card
     * @param chosenStudent targeted student
     * @throws ExceptionGame  if a move that is not permitted is made or a method fails to return a value
     */
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

