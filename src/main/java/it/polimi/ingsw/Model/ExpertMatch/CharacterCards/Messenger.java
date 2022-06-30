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

/** Implements the effect from Character card Messenger
 * Calculate influence on an additional archipelago
 */
public class Messenger extends CharacterCard implements Serializable {
    @Serial
    private final static long serialVersionUID = 174160886834745807L;
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
        calculateEffectInfluence(getArchipelagoEffected());
        paymentOfTheCard(match);
    }

    /**
     * This method is used to calculate the card effect on the influence
     * @param archipelago targeted archipelago
     * @throws ExceptionGame if a move that is not permitted is made or a method fails to return a value
     */
    public void calculateEffectInfluence(Archipelago archipelago) throws ExceptionGame{ //wizard del capitano
        Player activePlayer = getBasicMatch().getPlayerFromWizard(getActiveWizard());
        Player captainOfActivePlayer = getBasicMatch().getCaptainTeamOfPlayer(activePlayer);

        int indexArchipelagoEffected = getBasicMatch().getGame().getArchipelagos().indexOf(archipelago);
        int originalMnPosition = getBasicMatch().getPositionOfMotherNature();
        int numArchipelagosBefore = getBasicMatch().getGame().getArchipelagos().size();
        Archipelago archipelagoMN = getBasicMatch().getGame().getArchipelagos().get(originalMnPosition);

        archipelagoMN.setMotherNaturePresence(false);

        getBasicMatch().buildTower(captainOfActivePlayer, archipelago);
        getBasicMatch().getGame().takeCareOfTheMerge(archipelago);

        int numArchipelagosAfter = getBasicMatch().getGame().getArchipelagos().size();

        if(originalMnPosition > indexArchipelagoEffected){
            int difference = numArchipelagosBefore - numArchipelagosAfter;
            int newMnPosition = originalMnPosition - difference;
            getBasicMatch().getGame().getArchipelagos().get(newMnPosition).setMotherNaturePresence(true);
            getBasicMatch().getGame().getMotherNature().setPosition(newMnPosition);


        }else if(originalMnPosition == indexArchipelagoEffected){
            int newIndexArchipelagoEffected = getBasicMatch().getGame().getArchipelagos().indexOf(archipelago);

            getBasicMatch().getGame().getArchipelagos().get(newIndexArchipelagoEffected).setMotherNaturePresence(true);
            getBasicMatch().getGame().getMotherNature().setPosition(newIndexArchipelagoEffected);


        }else
            archipelagoMN.setMotherNaturePresence(true);

    }

    /**
     * resets the card
     */
    @Override
    public void resetCard() {
        super.resetCard();
    }
}
