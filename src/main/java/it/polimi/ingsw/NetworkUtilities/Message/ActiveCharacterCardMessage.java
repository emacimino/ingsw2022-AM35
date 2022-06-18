package it.polimi.ingsw.NetworkUtilities.Message;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;

public class ActiveCharacterCardMessage extends Message implements Serializable {
    @Serial
    private final static long serialVersionUID = -3373172779781769129L;
    private final String activeCharacterCardName;



    public ActiveCharacterCardMessage(String activeCharacterCardName) {
        super.setType(TypeMessage.ACTIVE_CHARACTER_CARD);
        this.activeCharacterCardName = activeCharacterCardName;
    }

    public String getActiveCharacterCardName() {
        return activeCharacterCardName;
    }
}
