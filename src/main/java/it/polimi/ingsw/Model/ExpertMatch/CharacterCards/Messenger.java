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

    public Messenger(BasicMatch basicMatch, String name) {
        super(basicMatch, name);
        setCost(3);
    }

    @Override
    public void useCard(ExpertMatch match) throws ExceptionGame {
        super.useCard(match);
        match.setActiveInfluenceCard(this);
        this.cost++;
    }

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

    @Override
    public void resetCard() {
        super.resetCard();
    }
}
