package it.polimi.ingsw.Model.ExpertMatch.InfluenceEffectsCards;

import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.Wizard.Wizard;

public class Messenger {

    private int cost = 3;

    public void useCharacterCard(Wizard wizard, Archipelago archipelago) {
        archipelago.calculateInfluenceInArchipelago(wizard);
    }
}
