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
        usedChefCard(getActiveWizard(), getColorEffected(), match);
    }

    /**
     * Is called when the character card Chef is used
     * @param wizard that have to calculate the influence
     * @param color of the students that the method has to ignore
     */

    private void usedChefCard(Wizard wizard, Color color, ExpertMatch match) {
        match.setChefEffect(color);
        resetCard();
    }


}



