package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.NetworkUtilities.CurrentGameMessage;

import java.io.Serial;
import java.io.Serializable;

/**Implements the effect from Character card
 * and uses MotherNatureEffect interface
 */

public class Magician extends CharacterCard implements MotherNatureEffectCard, Serializable {
    @Serial
    private final static long serialVersionUID = 6530887498702475208L;
    /**
     * Constructor of the class
      * @param basicMatch current match
     * @param name name of the card
     */

    public Magician(BasicMatch basicMatch, String name){
        super(basicMatch, name);
        setCost(1);
    }

    /**
     * This method let the player use the card
     * @param match the current match
     * @throws ExceptionGame
     */
    @Override
    public void useCard(ExpertMatch match) throws ExceptionGame {
        super.useCard(match);
        match.setActiveMotherNatureCard(this);
        paymentOfTheCard();
    }

    /**
     * This method calculates the effect of the card on mother nature
     * @param player player using the card
     * @param archipelago targeted archipelago
     * @throws ExceptionGame if a move that is not permitted is made or a method fails to return a value
     */

    public void effectMotherNatureCard(Player player, Archipelago archipelago) throws ExceptionGame{
        if (getBasicMatch().getGame().getArchipelagos().contains(archipelago)) {
            int positionMotherNature = getBasicMatch().getPositionOfMotherNature();
            int positionOfArchipelago = getBasicMatch().getGame().getArchipelagos().indexOf(archipelago);
            int stepsAllowed = getBasicMatch().getGame().getWizardFromPlayer(player).getRoundAssistantsCard().getStep();
            int numberOfArchipelagos = getBasicMatch().getGame().getArchipelagos().size();
            int numberOfSteps = ((positionOfArchipelago + numberOfArchipelagos) - positionMotherNature) % numberOfArchipelagos;
            if(getBasicMatch().getGame().getWizardFromPlayer(player).equals(getActiveWizard())){
                    if ((numberOfSteps >0 && numberOfSteps <= stepsAllowed + 2 )|| (positionMotherNature == positionOfArchipelago && numberOfArchipelagos <= stepsAllowed + 2)) {
                        getBasicMatch().getGame().getArchipelagos().get(positionMotherNature).setMotherNaturePresence(false);
                        getBasicMatch().getGame().getMotherNature().setPosition(positionOfArchipelago);
                        archipelago.setMotherNaturePresence(true);
                    }else
                        throw new ExceptionGame("This archipelago is not allowed by the assistant's card");
                }else
                    throw new ExceptionGame("Wizard does not have an Assistant's card");
        }else
            throw new ExceptionGame("This archipelago is not part of the game");

        }

    /**
     * resets the card
     */
    @Override
    public void resetCard() {
        super.resetCard();
    }
}
