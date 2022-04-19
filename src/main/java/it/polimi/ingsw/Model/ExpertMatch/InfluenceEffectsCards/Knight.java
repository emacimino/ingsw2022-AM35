package it.polimi.ingsw.Model.ExpertMatch.InfluenceEffectsCards;

import it.polimi.ingsw.Model.ExpertMatch.CharacterCard;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.StudentBag;
import it.polimi.ingsw.Model.Wizard.Wizard;

/**
 * CharachterCard knight
 */
public class Knight extends CharacterCard {

    private int cost = 2;
    private Wizard wizard;
    private Archipelago archipelago;

    public Knight(Wizard wizard, Archipelago archipelago) {
        this.wizard = wizard;
        this.archipelago = archipelago;
    }

    /**
     * This method is used to calculate influence (relative to the Wizard w) on the archipelago and takes into account towers and students when the knight card is used
     * @param w is the wizard of which influence is going to be calculated
     * @param archipelago is the island on which we want to use the knight card
     * @return the influence modified through the effect
     */
    private int knightCalculateInfluenceInArchipelago(Wizard w, Archipelago archipelago){
        return archipelago.calculateInfluenceInArchipelago(w) + 2;
    }

    public void useCharacterCard(Wizard wizard, Archipelago archipelago) {
        knightCalculateInfluenceInArchipelago(wizard, archipelago);
        cost ++;
    }

    public int getCost() {
        return cost;
    }
}
