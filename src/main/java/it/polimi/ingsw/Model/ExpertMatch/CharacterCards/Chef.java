package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.Wizard.Wizard;

/** Implements the effect from Character card Chef
 * It calculates the influence without the students of one color
 */
public class Chef extends CharacterCard {

    public Chef(Game game, String name) {
        super(game, name);
        setCost(3);
    }

    @Override
    public void useCard(ExpertMatch match) throws ExceptionGame {
        super.useCard(match);
        usedChefCard(match);
        resetCard();
    }

    /**
     * Is called when the character card Chef is used
     */

    private void usedChefCard(ExpertMatch match) {
        match.setChefEffect(getColorEffected());
    }


}


