package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;

/**
 * Interface used to assign effects to prohibition cards
 */
public interface ProhibitionEffectCard {
    /**
     * Use the prohibition effect
     * @throws ExceptionGame if the effect can't be played
     */
    void useProhibitionEffect() throws ExceptionGame;

    /**
     * Reset the prohibition effect
     * @param archipelago archipelago effected
     * @throws ExceptionGame if the reset fail
     */
    void resetProhibitionEffect(Archipelago archipelago) throws ExceptionGame;

    /**
     * Getter for the prohibition pass
     * @return prohibition pass remained
     */
    int getProhibitionPass();
}

