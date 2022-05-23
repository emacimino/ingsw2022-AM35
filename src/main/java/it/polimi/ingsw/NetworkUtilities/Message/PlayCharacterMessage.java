package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;

import java.io.Serial;
import java.util.List;

public class PlayCharacterMessage extends Message{
    @Serial
    private static final long serialVersionUID = -7642905185840013305L;
    private final CharacterCard characterCard;

    public PlayCharacterMessage(CharacterCard characterCard) {
        this.characterCard = characterCard;
    }

    public CharacterCard getCharacterCard() {
        return characterCard;
    }

}
