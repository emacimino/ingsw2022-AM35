package it.polimi.ingsw.NetworkUtilities;

import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;

import java.io.Serial;
import java.util.Map;

/**
 * Class used to send the character cards in game
 */
public class CharacterCardInGameMessage extends Message{
    @Serial
    private static final long serialVersionUID = -3374926525124994169L;
    private final Map<String, CharacterCard> characterCard;

    /**
     * Constructor of the class
     * @param characterCard a map of character cards
     */
    public CharacterCardInGameMessage(Map<String, CharacterCard> characterCard) {
        this.characterCard = characterCard;
        setType(TypeMessage.CHARACTER_CARD_IN_GAME);
    }

    /**
     * Method that returns the character cards contained in the message
     * @return a map of character cards
     */
    public Map<String, CharacterCard> getCharacterCard() {
        return characterCard;
    }
}
