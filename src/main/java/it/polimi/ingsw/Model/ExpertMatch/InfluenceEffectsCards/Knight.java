package it.polimi.ingsw.Model.ExpertMatch.InfluenceEffectsCards;

import it.polimi.ingsw.Model.ExpertMatch.CharacterCard;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.FactoryMatch.Match;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Island;
import it.polimi.ingsw.Model.SchoolsMembers.StudentBag;
import it.polimi.ingsw.Model.Wizard.Wizard;

/**
 * CharachterCard knight
 */
public class Knight extends InfluenceEffectCard{

    public Knight(Game studentBag) {
        super(studentBag);
    }

    private int cost = 2;

    /**
     * This method is used to calculate influence (relative to the Wizard w) on the archipelago and takes into account towers and students when the knight card is used
     * @param w is the wizard of which influence is going to be calculated
     * @param archipelago is the island on which we want to use the knight card
     * @return the influence modified through the effect
     */
    private int knightCalculateInfluenceInArchipelago(Wizard w, Archipelago archipelago){
        return archipelago.calculateInfluenceInArchipelago(w) + 2;
    }

    public int useCharacterCard(Wizard wizard, Archipelago archipelago) {
        cost++;
        return knightCalculateInfluenceInArchipelago(wizard, archipelago);
    }

    @Override
    public int usedKnightCard(Wizard wizard, Archipelago archipelago) {
        return this.useCharacterCard(wizard,archipelago);
    }

    public int getCost() {
        return cost;
    }
}
