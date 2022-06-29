package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;

/**
 * Interface used to assign effects affecting mother nature
 */

public interface MotherNatureEffectCard {
    /**
     * Calculate the mother nature effect of the card
     * @param player player which call the card
     * @param archipelago archipelago effected
     * @throws ExceptionGame if the effect can't be applied
     */
    void effectMotherNatureCard(Player player, Archipelago archipelago) throws ExceptionGame;
    /**
     * reset the card parameters
     */
    void resetCard();
}
