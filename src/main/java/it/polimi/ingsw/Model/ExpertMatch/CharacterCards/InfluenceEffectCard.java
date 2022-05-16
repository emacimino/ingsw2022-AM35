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

    int calculateEffectInfluence(Wizard wizard,Archipelago archipelago, int normalInfluence) throws ExceptionGame; //Wizard is the captain

    void resetCard();
}
