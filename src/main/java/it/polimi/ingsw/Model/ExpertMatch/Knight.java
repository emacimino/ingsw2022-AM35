package it.polimi.ingsw.Model.ExpertMatch;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Island;
import it.polimi.ingsw.Model.Wizard.Wizard;


import java.util.ArrayList;
import java.util.List;

public class Knight {

    private List<Island> isle= new ArrayList<>();

    /**
     * This method is used to calculate influence (relative to the Wizard w) on the archipelago and takes into account towers and students when the knight card is used
     * @param w is the wizard of which influence is going to be calculated
     * @return and int that is the influence itself
     */
    public int calculateInfluenceInArchipelago(Wizard w){
        int influence = 2;
        for (Island island: isle) {
            try{
                if(island.getTower().getProperty().equals(w)){
                    influence++;
                }
            }
            catch(ExceptionGame ignored){}

            if(!w.getBoard().getProfessorInTable().isEmpty()) {
                influence += w.getBoard().getProfessorInTable().stream().mapToInt(prof -> island.getStudentFilteredByColor(prof.getColor()).size()).sum();
            }
        }
        return influence;
    }
}
