package it.polimi.ingsw.Model.ExpertMatch.InfluenceEffectsCards;

import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.StudentBag;
import it.polimi.ingsw.Model.Wizard.Wizard;

/** Implements the effect from Character card Messenger
 * Calculate influence on an additional archipelago
 */
public class Messenger extends InfluenceEffectCard {


    private int cost = 3;

    public Messenger(Game studentBag) {
        super(studentBag);
    }

    public void useCharacterCard(Wizard wizard, Archipelago archipelago) {
        archipelago.calculateInfluenceInArchipelago(wizard);
    }

    @Override
    public void usedMessengerCard(Wizard wizard, Archipelago archipelago) {
        this.useCharacterCard(wizard, archipelago);
    }
}
