package it.polimi.ingsw.Model.ExpertMatch.InfluenceEffectsCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.SchoolsLands.Island;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Professor;
import it.polimi.ingsw.Model.Wizard.Wizard;

/** Implements the effect from Character card Chef
 * It calculates the influence without the students of one color
 */
public class Chef {

    private int cost = 3;

    /**
     * Is called when the character card Chef is used
     * @param wizard that have to calculate the influence
     * @param island where the influence has to be calculated
     * @param color of the students that the method has to ignore
     * @return the correct influence
     */
    public int useCharacterCard(Wizard wizard, Island island, Color color) {

        int influence = 0;

        for (Professor professor : wizard.getBoard().getProfessorInTable()) {
            if (wizard.getBoard().isProfessorPresent(color) && !professor.getColor().equals(color)) {
                influence += wizard.getBoard().getProfessorInTable().stream().mapToInt(prof -> island.getStudentFilteredByColor(prof.getColor()).size()).sum();

            } else if (!wizard.getBoard().isProfessorPresent(color)) {

                influence += wizard.getBoard().getProfessorInTable().stream().mapToInt(prof -> island.getStudentFilteredByColor(prof.getColor()).size()).sum();
            }

        }
        return influence;
    }
}



