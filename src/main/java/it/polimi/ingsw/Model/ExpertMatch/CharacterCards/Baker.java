package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
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
public class Baker extends CharacterCard implements InfluenceEffectCard, Serializable {
    @Serial
    private final static long serialVersionUID = 7241213580535659234L;
    /**
     * constructor
     * @param basicMatch current match
     * @param name name of the card
     */
    public Baker(BasicMatch basicMatch, String name) {
        super(basicMatch, name);
        setCost(2);

    }

    /**
     * This method lets the player use the card
     * @param match the current match
     * @throws ExceptionGame
     */
    @Override
    public void useCard(ExpertMatch match) throws ExceptionGame {
        super.useCard(match);
        match.setActiveInfluenceCard(this);
        paymentOfTheCard();

    }

    /**
     * This method is used to calculate influence after the card is played
     * @param wizard wizard using the card
     * @param archipelago targeted archipelago
     * @param normalInfluence influence before card usage
     * @return the modified influence
     * @throws ExceptionGame signals a move that is not permitted
     */
    public int calculateEffectInfluence(Wizard wizard, Archipelago archipelago, int normalInfluence) throws ExceptionGame{
        Player activePlayer = getBasicMatch().getPlayerFromWizard(getActiveWizard());
        Player captainOfActivePlayer = getBasicMatch().getCaptainTeamOfPlayer(activePlayer);
        Wizard wizardCaptain = getBasicMatch().getGame().getWizardFromPlayer(captainOfActivePlayer);
        int influence;
        if(wizard.equals(wizardCaptain)){
            influence = archipelago.getStudentFromArchipelago().size() + archipelago.calculateInfluenceTowers(wizard);
        } else {
            influence = archipelago.calculateInfluenceTowers(wizard);
        }
        return influence;
    }


    /**
     * resets the card
     */
    @Override
    public void resetCard() {
        super.resetCard();
    }

}
