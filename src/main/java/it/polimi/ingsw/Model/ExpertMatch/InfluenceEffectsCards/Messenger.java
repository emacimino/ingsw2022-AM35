package it.polimi.ingsw.Model.ExpertMatch.InfluenceEffectsCards;

import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.Wizard.Wizard;

/** Implements the effect from Character card Messenger
 * Calculate influence on an additional archipelago
 */
public class Messenger {

    private int cost = 3;

    public void useCharacterCard(Wizard wizard, Archipelago archipelago) {
        archipelago.calculateInfluenceInArchipelago(wizard);
    }
}
