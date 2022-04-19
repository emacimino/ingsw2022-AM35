package it.polimi.ingsw.Model.ExpertMatch.InfluenceEffectsCards;

import it.polimi.ingsw.Model.SchoolsLands.Island;
import it.polimi.ingsw.Model.Wizard.Wizard;

public class Archer{
    private int cost = 3;

    public int useCharacterCard(Wizard w, Island island) {

            int influence=0;

                if(!w.getBoard().getProfessorInTable().isEmpty()) {
                    influence += w.getBoard().getProfessorInTable().stream().mapToInt(prof -> island.getStudentFilteredByColor(prof.getColor()).size()).sum();
                }
        return influence;
    }
}



