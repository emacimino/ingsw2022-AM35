package it.polimi.ingsw.Model.ExpertMatch.InfluenceEffectsCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.SchoolsLands.Island;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Professor;
import it.polimi.ingsw.Model.Wizard.Wizard;

public class Chef {

    private int cost = 3;


    public int useCharacterCard(Wizard w,Island island, Color color) {

        Wizard wizard = selectWizardWithMostStudents(color);
        for(Professor p : professors)
            if(p.getColor().equals(c)) {
                wizard.getBoard().setProfessorInTable(p);
                professors.remove(p);
                return;
            }
        for(Wizard w : wizards)
            if(w.getBoard().isProfessorPresent(c)){
                try {
                    Professor professor = w.getBoard().removeProfessorFromTable(c);
                    wizard.getBoard().setProfessorInTable(professor);
                }catch(ExceptionGame e){
                    throw new ExceptionGame("This wizard:" + w + ", does not have the professor of color" + c, e);
                }
            }





        Professor professor = new Professor(color);
        int influence=0;

            try{
                if(island.getTower().getProperty().equals(w)){
                    influence++;
                }
            }
            catch(ExceptionGame ignored){}

            if(!w.getBoard().getProfessorInTable().contains(professor.getColor())) {

                influence += w.getBoard().getProfessorInTable().stream().mapToInt(prof -> island.getStudentFilteredByColor(prof.getColor()).size()).sum();
            }

        return influence;
        }

    }



