package it.polimi.ingsw.Model.ExpertMatch.InfluenceCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCard;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Island;
import it.polimi.ingsw.Model.Wizard.Wizard;

public class Archer implements CharacterCard {
    @Override
    public int useCharacterCard(Wizard w, Island island) {

            int influence=0;

                if(!w.getBoard().getProfessorInTable().isEmpty()) {
                    influence += w.getBoard().getProfessorInTable().stream().mapToInt(prof -> island.getStudentFilteredByColor(prof.getColor()).size()).sum();
                }
        return influence;
    }

    @Override
    public void usedCard() {

    }
        }



