package it.polimi.ingsw;

import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.Archer;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.Princess;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.StudentEffectCard;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import org.junit.jupiter.api.Assertions;

public class Test {

    @org.junit.jupiter.api.Test
    void TestInstanceInterface(){
        BasicMatch match = new BasicMatch();
        CharacterCard characterCard = new Princess(match,"Princess");
        CharacterCard c2 = new Archer(match, "Archer");
        Assertions.assertTrue(characterCard instanceof StudentEffectCard);
        Assertions.assertFalse(c2 instanceof StudentEffectCard);

    }
}
