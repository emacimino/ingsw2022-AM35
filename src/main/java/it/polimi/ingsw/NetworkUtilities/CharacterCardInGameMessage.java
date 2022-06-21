package it.polimi.ingsw.NetworkUtilities;

import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;

import java.io.Serial;
import java.util.Map;

public class CharacterCardInGameMessage extends Message{
    @Serial
    private static final long serialVersionUID = -3374926525124994169L;
    private final Map<String, CharacterCard> characterCard;

    public CharacterCardInGameMessage(Map<String, CharacterCard> characterCard) {
        this.characterCard = characterCard;
        setType(TypeMessage.CHARACTER_CARD_IN_GAME);
    }

    public Map<String, CharacterCard> getCharacterCard() {
        return characterCard;
    }
}
