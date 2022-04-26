package it.polimi.ingsw.Model.ExpertMatch.InfluenceEffectsCards;

import it.polimi.ingsw.Model.SchoolsLands.Island;
import it.polimi.ingsw.Model.SchoolsMembers.StudentBag;
import it.polimi.ingsw.Model.Wizard.Wizard;

/**Implements the effect from Character card
 * Does not use Towers to calculate influence
 */
public class Archer extends InfluenceEffectCard{

    private int cost = 3;

    public Archer(StudentBag studentBag) {
        super(studentBag);
    }

    public int useCharacterCard(Wizard w, Island island) {

            int influence=0;

                if(!w.getBoard().getProfessorInTable().isEmpty()) {
                    influence += w.getBoard().getProfessorInTable().stream().mapToInt(prof -> island.getStudentFilteredByColor(prof.getColor()).size()).sum();
                }
        this.usedCard();
        return influence;

    }

}



