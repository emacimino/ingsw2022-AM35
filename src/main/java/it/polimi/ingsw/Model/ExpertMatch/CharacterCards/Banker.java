package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.Wizard;

import java.util.Collection;
import java.util.Iterator;

public class Banker extends CharacterCard implements StudentEffectCard{

    public Banker(BasicMatch basicMatch, String name){
        super(basicMatch, name);
        setCost(3);
    }

    @Override
    public void useCard(ExpertMatch match) throws ExceptionGame {
        super.useCard(match);
        useBankerCard();
        this.cost++;
    }

    private void useBankerCard() throws ExceptionGame{
        Color colorEffected = getColorEffected();
        for(Wizard w : getBasicMatch().getGame().getWizards()){
            for(int i = 0; i<3; i++){
                Collection<Student> studentsOnTable = w.getBoard().getTableOfStudent(colorEffected).getStudentsInTable();
                if(!studentsOnTable.isEmpty()){
                    Iterator<Student> iterator = studentsOnTable.iterator();
                    iterator.next();
                    iterator.remove();

                }
            }
        }
        resetCard();
    }
}
