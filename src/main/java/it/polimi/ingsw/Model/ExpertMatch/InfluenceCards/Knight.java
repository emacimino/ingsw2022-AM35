package it.polimi.ingsw.Model.ExpertMatch.InfluenceCards;

import it.polimi.ingsw.Model.Coin;
import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCard;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Island;
import it.polimi.ingsw.Model.Wizard.Wizard;


import java.util.ArrayList;
import java.util.List;

/**
 * CharachterCard knight
 */
public class Knight implements CharacterCard {

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

    @Override
    public void useCharacterCard(Wizard wizard, Archipelago archipelago) {
        knightCalculateInfluenceInArchipelago(wizard, archipelago);
    }

    @Override
    public void usedCard() {

    }


}
