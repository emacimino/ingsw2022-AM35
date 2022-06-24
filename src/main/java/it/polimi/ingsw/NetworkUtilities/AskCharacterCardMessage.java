package it.polimi.ingsw.NetworkUtilities;

import java.io.Serial;
/**
 Used to ask the usage of a CharacterCard
 */
public class AskCharacterCardMessage extends Message{
    @Serial
    private static final long serialVersionUID = -3512710218645978684L;
    private final String characterCardName;
    /**
     * constructor of the class
     * @param characterCards character in game
     */
    public AskCharacterCardMessage(String characterCards) {
        this.characterCardName = characterCards;
        setType(TypeMessage.ASK_CHARACTER_CARD);
    }

    public String getCharacterCardName() {
        return characterCardName;
    }
}
