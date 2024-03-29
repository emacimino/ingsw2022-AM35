package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Island;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.Wizard.Wizard;
import it.polimi.ingsw.NetworkUtilities.CurrentGameMessage;

import java.io.Serial;
import java.io.Serializable;

/** Implements the effect from Character card Chef
 * It calculates the influence without the students of one color
 */
public class Chef extends CharacterCard implements InfluenceEffectCard, Serializable {
    @Serial
    private final static long serialVersionUID = -90494329657295844L;

    /**
     * Constructor of the class
     * @param basicMatch current match
     * @param name name of the card
     */
    public Chef(BasicMatch basicMatch, String name) {
        super(basicMatch, name);
        setCost(3);

    }

    /**
     * This method let the player use the card
     * @param match the current match
     * @throws ExceptionGame if the card can't be used
     */
    @Override
    public void useCard(ExpertMatch match) throws ExceptionGame {
        super.useCard(match);
        match.setActiveInfluenceCard(this);
        paymentOfTheCard(match);
    }

    /**
     * This method is used to calculate the effect of the card on the influence
     * @param wizard wizard using the card
     * @param archipelago targeted archipelago
     * @param normalInfluence influence before changes
     * @return The modified influence
     * @throws ExceptionGame if a move that is not permitted is made or a method fails to return a value
     */
    public int calculateEffectInfluence(Wizard wizard, Archipelago archipelago, int normalInfluence) throws ExceptionGame{
        Color colorEffect = getColorEffected();
        int colorInfluence = 0;
        for (Island island : archipelago.getIsle()) {
            colorInfluence = island.getStudentFilteredByColor(colorEffect).size();
        }

        if (getBasicMatch().playerControlProfessor(getBasicMatch().getPlayerFromWizard(wizard), colorEffect)) //problema x 4
            normalInfluence = normalInfluence - colorInfluence;
        return normalInfluence;
    }


    /**
     * resets the card
     */
    @Override
    public void resetCard() {
        super.resetCard();
    }
}



