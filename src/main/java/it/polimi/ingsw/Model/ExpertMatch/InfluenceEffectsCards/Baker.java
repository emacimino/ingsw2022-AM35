package it.polimi.ingsw.Model.ExpertMatch.InfluenceEffectsCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Professor;
import it.polimi.ingsw.Model.SchoolsMembers.StudentBag;
import it.polimi.ingsw.Model.Wizard.Wizard;

public class Baker extends InfluenceEffectCard{


    public Baker(StudentBag studentBag) {
        super(studentBag);
    }

    public void useCharacterCard(Wizard wizardWithoutProfessor, Wizard wizardWithProfessor, Color color, Professor professor) throws ExceptionGame {


        if (wizardWithoutProfessor.getBoard().getStudentsFromTable(color).size() >= wizardWithProfessor.getBoard().getStudentsFromTable(color).size()) {
            wizardWithProfessor.getBoard().removeProfessorFromTable(color);
            wizardWithoutProfessor.getBoard().setProfessorInTable(professor);
        }
    }
}
