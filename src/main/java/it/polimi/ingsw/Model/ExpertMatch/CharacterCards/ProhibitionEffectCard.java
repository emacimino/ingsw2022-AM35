package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;

public interface ProhibitionEffectCard {

    void useProhibitionEffect() throws ExceptionGame;
    void resetAProhibitionEffect(Archipelago archipelago) throws ExceptionGame;
    int getProhibitionPass();
}

