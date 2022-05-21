package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.Wizard.Wizard;

/**Implements the effect from Character card
 * Does not use Towers to calculate influence
 */
public class Archer extends CharacterCard implements InfluenceEffectCard{
    /**
     * Constructor of the class, the cost is set to 3 as indicated by the rules
     * @param basicMatch current match
     * @param name name of the card
     */
    public Archer(BasicMatch basicMatch, String name) {
        super(basicMatch, name);
        setCost(3);
    }

    /**
     * This method is the one used when a player wants to play this card
     * @param match current match
     * @throws ExceptionGame exception thrown if the active wizard is not set
     */
    public void useCard(ExpertMatch match) throws ExceptionGame {
        super.useCard(match);
        match.setActiveInfluenceCard(this);
        this.cost++;
    }

    /**
     * This method calculates the influence changes tied to the card effect
     * @param wizard wizard using the card
     * @param archipelago archipelago affected
     * @param normalInfluence influence before changes
     * @return influence modified by card
     */
    public int calculateEffectInfluence(Wizard wizard, Archipelago archipelago,  int normalInfluence){
        int towerInfluence = archipelago.calculateInfluenceTowers(wizard); //va bene perchè player p è il captain sempre
        return normalInfluence - towerInfluence;
    }

    @Override
    public void resetCard() {
        super.resetCard();
    }
}



