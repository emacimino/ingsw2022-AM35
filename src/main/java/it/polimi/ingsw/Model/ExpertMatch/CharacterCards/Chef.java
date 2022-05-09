package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Island;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.Wizard.Wizard;

/** Implements the effect from Character card Chef
 * It calculates the influence without the students of one color
 */
public class Chef extends CharacterCard implements InfluenceEffectCard{

    public Chef(BasicMatch basicMatch, String name) {
        super(basicMatch, name);
        setCost(3);
    }

    @Override
    public void useCard(ExpertMatch match) throws ExceptionGame {
        super.useCard(match);
        match.setActiveInfluenceCard(this);
        this.cost++;
    }

    public int calculateEffectInfluence(Wizard wizard, Archipelago archipelago, int normalInfluence) throws ExceptionGame{
        Color colorEffect = getColorEffected();
        int colorInfluence = 0;
        for (Island island : archipelago.getIsle()) {
            colorInfluence = island.getStudentFilteredByColor(colorEffect).size();
        }

        if (getBasicMatch().playerControlProfessor(getBasicMatch().getPlayerFromWizard(wizard), colorEffect)) //problema x 4
            normalInfluence = normalInfluence - colorInfluence;
        return normalInfluence;
    }

    @Override
    public void resetCard() {
        super.resetCard();
    }
}



