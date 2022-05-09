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

    public Archer(BasicMatch basicMatch, String name) {
        super(basicMatch, name);
        setCost(3);
    }

    public void useCard(ExpertMatch match) throws ExceptionGame {
        super.useCard(match);
        match.setActiveInfluenceCard(this);
        this.cost++;
    }

    public int calculateEffectInfluence(Wizard wizard, Archipelago archipelago,  int normalInfluence) throws ExceptionGame{
        int towerInfluence = archipelago.calculateInfluenceTowers(wizard); //va bene perchè player p è il captain sempre
        return normalInfluence - towerInfluence;
    }

    @Override
    public void resetCard() {
        super.resetCard();
    }
}



