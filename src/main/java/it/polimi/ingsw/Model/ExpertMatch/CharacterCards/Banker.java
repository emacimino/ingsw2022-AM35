package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.Wizard;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

/**
 * Uses effects from CharacterCard and interface StudentEffectCard
 */
public class Banker extends CharacterCard implements StudentEffectCard, Serializable {
    @Serial
    private final static long serialVersionUID = -3412299212609450172L;
    /**
     * Constructor of the class
     * @param basicMatch current match
     * @param name name of the card
     */
    public Banker(BasicMatch basicMatch, String name){
        super(basicMatch, name);
        setCost(3);
    }

    /**
     * This method let the player use the card
     * @param match the current match
     * @throws ExceptionGame if the active wizard is not set
     */
    @Override
    public void useCard(ExpertMatch match) throws ExceptionGame {
        super.useCard(match);
        useBankerCard();
        paymentOfTheCard();
        resetCard();
    }

    /**
     * This method is used to facilitate the usage of the card
     * @throws ExceptionGame if the method studentOnTable fails to return a student
     */
    private void useBankerCard() throws ExceptionGame{
        Color colorEffected = getColorEffected();
        for(Wizard w : getBasicMatch().getGame().getWizards()){
            for(int i = 0; i<3; i++){
                Collection<Student> studentsOnTable = w.getBoard().getTableOfStudent(colorEffected).getStudentsInTable();
                if(!studentsOnTable.isEmpty()){
                    Iterator<Student> iterator = studentsOnTable.iterator();
                    iterator.next();
                    iterator.remove();

                }
            }
        }
    }
}
