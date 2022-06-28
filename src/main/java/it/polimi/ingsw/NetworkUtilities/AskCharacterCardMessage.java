package it.polimi.ingsw.NetworkUtilities;

import java.io.Serial;

/**
 * Class used to send a character card's name
 */
public class AskCharacterCardMessage extends Message{
    @Serial
    private static final long serialVersionUID = -3512710218645978684L;
    private final String characterCardName;

    /**
     * Constructor of the class
     * @param characterCards Name of the card
     */
    public AskCharacterCardMessage(String characterCards) {
        this.characterCardName = characterCards;
        setType(TypeMessage.ASK_CHARACTER_CARD);
    }

    /**
     * Method that returns the name of the character card
     * @return a string containing a character card's name
     */
    public String getCharacterCardName() {
        return characterCardName;
    }
}
