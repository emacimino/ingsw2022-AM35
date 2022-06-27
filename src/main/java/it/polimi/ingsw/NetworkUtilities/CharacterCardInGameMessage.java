package it.polimi.ingsw.NetworkUtilities;

import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;

import java.io.Serial;
import java.util.Map;

/**
 * Current state of the character cards that are used in the current game
 */
public class CharacterCardInGameMessage extends Message{
    @Serial
    private static final long serialVersionUID = -3374926525124994169L;
    private final Map<String, CharacterCard> characterCard;

    /**
     * Constructor of the class
     * @param characterCard list of character cards that are used in the current game
     */
    public CharacterCardInGameMessage(Map<String, CharacterCard> characterCard) {
        this.characterCard = characterCard;
        setType(TypeMessage.CHARACTER_CARD_IN_GAME);
    }
    /**
     * Getter of the list of character card used in this game
     * @return list of character cards that are used in the current game
     */
    public Map<String, CharacterCard> getCharacterCard() {
        return characterCard;
    }
}
