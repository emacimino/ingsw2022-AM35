package it.polimi.ingsw.Model.ExpertMatch.InfluenceCards;

import it.polimi.ingsw.Model.ExpertMatch.CharacterCard;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.Wizard.Wizard;

public class Messenger implements CharacterCard {

    @Override
    public void useCharacterCard(Wizard wizard, Archipelago archipelago) {
        archipelago.calculateInfluenceInArchipelago(wizard);
    }

    @Override
    public void usedCard() {

    }
}
