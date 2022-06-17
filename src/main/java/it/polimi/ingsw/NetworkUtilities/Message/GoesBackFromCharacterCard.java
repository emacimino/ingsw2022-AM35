package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Controller.TurnPhase;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;

public class GoesBackFromCharacterCard extends Message implements Serializable {
    @Serial
    private final static long serialVersionUID = -5051477534321127050L;

    private final TurnPhase precedentTurnPhase;
    public GoesBackFromCharacterCard(TurnPhase precedentTurnPhase) {
        super.setType(TypeMessage.GET_BACK_FROM_USED_CHARACTER_CARD);
        this.precedentTurnPhase = precedentTurnPhase;
    }

    public TurnPhase getPrecedentTurnPhase() {
        return precedentTurnPhase;
    }
}
