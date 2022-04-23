package it.polimi.ingsw.Model.ExpertMatch;

import it.polimi.ingsw.Model.ExpertMatch.InfluenceEffectsCards.Knight;
import it.polimi.ingsw.Model.ExpertMatch.StudentEffectsCards.Friar;
import it.polimi.ingsw.Model.SchoolsMembers.StudentBag;

import java.util.ArrayList;

public class CharacterCard extends DeckCharacterCard {

    StudentBag studentBag;
    public CharacterCard(StudentBag studentBag) {
        this.studentBag = studentBag;
    }

    public void createDeck(){

    }
}
