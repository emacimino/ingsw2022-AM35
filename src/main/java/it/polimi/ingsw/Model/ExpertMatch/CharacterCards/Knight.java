package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.Wizard.Wizard;
import it.polimi.ingsw.NetworkUtilities.CurrentGameMessage;

import java.io.Serial;
import java.io.Serializable;

/**Implements the effect from Character card
 * and uses InfluenceEffectCard interface
 */
public class Knight extends CharacterCard implements InfluenceEffectCard, Serializable {
    @Serial
    private final static long serialVersionUID = 5006606355033072195L;
    /**
     * Constructor of the class
     * @param basicMatch current match
     * @param name name of the card
     */
    public Knight(BasicMatch basicMatch, String name) {
        super(basicMatch, name);
        setCost(2);

    }

    /**
     * This method let the player use the card
     * @param match the current match
     * @throws ExceptionGame if the active wizard is not set
     */
    @Override
    public void useCard(ExpertMatch match) throws ExceptionGame{
        super.useCard(match);
        match.setActiveInfluenceCard(this);
        paymentOfTheCard();
    }

    /**
     * This method is used to calculate the card effect on the influence
     * @param wizard wizard using the card
     * @param archipelago targeted archipelago
     * @param normalInfluence influence before changes
     * @return modified influence
     * @throws ExceptionGame if a move that is not permitted is made or a method fails to return a value
     */
    public int calculateEffectInfluence(Wizard wizard, Archipelago archipelago,  int normalInfluence) throws ExceptionGame{
        Player activePlayer = getBasicMatch().getPlayerFromWizard(getActiveWizard());
        Player captainOfActivePlayer = getBasicMatch().getCaptainTeamOfPlayer(activePlayer);
        Wizard wizardCaptain = getBasicMatch().getGame().getWizardFromPlayer(captainOfActivePlayer);
        int add = 0;
        if(wizard.equals(wizardCaptain)){
            add = 2;
        }
        return normalInfluence + add;
    }

    /**
     * resets the card
     */
    @Override
    public void resetCard() {
        super.resetCard();
    }
}
