package it.polimi.ingsw.Model.ExpertMatch;

import it.polimi.ingsw.Model.Coin;
import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.Wizard.Wizard;

import java.util.ArrayList;

public interface StudentEffectsCard  {

    public void useCharacterCard();

    public void useCharacterCard(Wizard wizard, Archipelago archipelago);

    public void usedCard();

}
