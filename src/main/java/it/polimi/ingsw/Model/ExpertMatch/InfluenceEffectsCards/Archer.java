package it.polimi.ingsw.Model.ExpertMatch.InfluenceEffectsCards;

import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.FactoryMatch.Match;
import it.polimi.ingsw.Model.SchoolsLands.Island;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.StudentBag;
import it.polimi.ingsw.Model.Wizard.Wizard;

/**Implements the effect from Character card
 * Does not use Towers to calculate influence
 */
public class Archer extends InfluenceEffectCard{

    private int cost = 3;

    public Archer(Game game) {
        super(game);
    }

    public int useCharacterCard(Wizard w, Island island) {

            int influence=0;

                if(!w.getBoard().getProfessorInTable().isEmpty()) {
                    influence += w.getBoard().getProfessorInTable().stream().mapToInt(prof -> island.getStudentFilteredByColor(prof.getColor()).size()).sum();
                }
        this.cost++;
        return influence;

    }

    @Override
    public int usedArcherCard(Wizard wizard, Island island) {
        return this.useCharacterCard(wizard, island);
    }
}



