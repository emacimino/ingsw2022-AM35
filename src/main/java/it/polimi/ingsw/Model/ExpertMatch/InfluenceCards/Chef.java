package it.polimi.ingsw.Model.ExpertMatch.InfluenceCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCard;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Island;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Professor;
import it.polimi.ingsw.Model.Wizard.Wizard;

public class Chef implements CharacterCard {

    private int cost = 3;

    @Override
    public int useCharacterCard(Wizard w,Island island, Color color) {

        Professor professor = new Professor(color);
        int influence=0;

            try{
                if(island.getTower().getProperty().equals(w)){
                    influence++;
                }
            }
            catch(ExceptionGame ignored){}

            if(!w.getBoard().getProfessorInTable().isEmpty() && !w.getBoard().getProfessorInTable().contains(professor.getColor())) {
                influence += w.getBoard().getProfessorInTable().stream().mapToInt(prof -> island.getStudentFilteredByColor(prof.getColor()).size()).sum();
            }
        return influence;
        }

    }



    @Override
    public void usedCard() {

    }
}
