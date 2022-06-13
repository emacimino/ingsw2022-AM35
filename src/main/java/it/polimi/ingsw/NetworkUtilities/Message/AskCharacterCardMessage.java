package it.polimi.ingsw.NetworkUtilities.Message;

import java.io.Serial;

public class AskCharacterCardMessage extends Message{
    @Serial
    private static final long serialVersionUID = -3512710218645978684L;
    private final String characterCardName;

    public AskCharacterCardMessage(String characterCards) {
        this.characterCardName = characterCards;
        setType(TypeMessage.ASK_CHARACTER_CARD);
    }

    public String getCharacterCardName() {
        return characterCardName;
    }
}
