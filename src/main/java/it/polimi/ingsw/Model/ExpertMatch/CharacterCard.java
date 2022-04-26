package it.polimi.ingsw.Model.ExpertMatch;

import it.polimi.ingsw.Model.ExpertMatch.InfluenceEffectsCards.Knight;
import it.polimi.ingsw.Model.ExpertMatch.StudentEffectsCards.Friar;
import it.polimi.ingsw.Model.SchoolsMembers.StudentBag;

import java.util.ArrayList;

public abstract class CharacterCard extends FactoryCharacterCard {
    protected int cost;
    protected StudentBag studentBag;
    public CharacterCard(StudentBag studentBag) {
        this.studentBag = studentBag;
    }

    protected void usedCard(){
        cost++;
    }
}
