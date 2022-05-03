package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.Wizard.Wizard;

/**Implements the effect from Character card
 * Does not use Towers to calculate influence
 */
public class Archer extends CharacterCard {

    public Archer(Game game, String name) {
        super(game, name);
        setCost(3);
    }

    public void useCard(ExpertMatch match) throws ExceptionGame {
        super.useCard(match);
        usedArcherCard(match);
        resetCard();

    }


    private void usedArcherCard(ExpertMatch match) {
        match.setArcherEffect(true);
    }
}



