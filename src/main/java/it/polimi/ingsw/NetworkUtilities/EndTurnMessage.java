package it.polimi.ingsw.NetworkUtilities;

import java.io.Serial;

public class EndTurnMessage extends Message{
    @Serial
    private static final long serialVersionUID = 3002290940031968259L;
    private final String message = "Please, wait for your turn";

    public EndTurnMessage() {
        setType(TypeMessage.END_OF_TURN);
    }

    public String getContent() {
        return message;
    }
}
