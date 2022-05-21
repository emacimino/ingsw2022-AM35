package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.Wizard.Wizard;

/** Implements the effect from Character card Messenger
 * Calculate influence on an additional archipelago
 */
public class Messenger extends CharacterCard implements InfluenceEffectCard{
    /**
     * Constructor of the class
     * @param basicMatch current match
     * @param name name of the class
     */
    public Messenger(BasicMatch basicMatch, String name) {
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
        match.setActiveInfluenceCard(this);
        this.cost++;
    }

    /**
     * This method is used to calculate the card effect on the influence
     * @param wizard wizard using the card
     * @param archipelago targeted archipelago
     * @param normalInfluence influence before changes
     * @return the modified influence
     * @throws ExceptionGame if a move that is not permitted is made or a method fails to return a value
     */
    public int calculateEffectInfluence(Wizard wizard, Archipelago archipelago, int normalInfluence) throws ExceptionGame{ //wizard del capitano
        Player activePlayer = getBasicMatch().getPlayerFromWizard(getActiveWizard());
        Player captainOfActivePlayer = getBasicMatch().getCaptainTeamOfPlayer(activePlayer);
        Wizard wizardCaptain = getBasicMatch().getGame().getWizardFromPlayer(captainOfActivePlayer);
        int influenceArchipelago = getBasicMatch().getWizardInfluenceInArchipelago(captainOfActivePlayer, getArchipelagoEffected());
        if(wizard.equals(wizardCaptain)){
            normalInfluence += influenceArchipelago;
        }
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
