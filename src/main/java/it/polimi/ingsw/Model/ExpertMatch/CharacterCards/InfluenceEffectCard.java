package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.Wizard.Wizard;

/**
 * Interface used to simplify the cards effects
 */

public interface InfluenceEffectCard {

    /**
     * Calculate the influence effect of the card
     * @param wizard wizard that play the card
     * @param archipelago archipelago effected by the card
     * @param normalInfluence usual calculation influence
     * @return the effected influence
     * @throws ExceptionGame if the card could not be used
     */
    int calculateEffectInfluence(Wizard wizard,Archipelago archipelago, int normalInfluence) throws ExceptionGame; //Wizard is the captain

    /**
     * reset the card parameters
     */
    void resetCard();
}
