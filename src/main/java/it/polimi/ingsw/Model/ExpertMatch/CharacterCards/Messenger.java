package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.Wizard.Wizard;

/** Implements the effect from Character card Messenger
 * Calculate influence on an additional archipelago
 */
public class Messenger extends CharacterCard {

    public Messenger(Game game, String name) {
        super(game, name);
        super.cost = 3;
    }

    @Override
    public void useCard(ExpertMatch match) throws ExceptionGame {
        super.useCard(match);
        usedMessengerCard(getActiveWizard(), getArchipelagoEffected(), match);
    }


    private void usedMessengerCard(Wizard wizard, Archipelago archipelago, ExpertMatch match) {
        int addInfluence = archipelago.calculateInfluenceInArchipelago(wizard);
        wizard.setMessageEffect(addInfluence);
    }
}
