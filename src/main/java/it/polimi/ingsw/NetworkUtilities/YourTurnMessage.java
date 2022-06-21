package it.polimi.ingsw.NetworkUtilities;

import java.io.Serial;

public class YourTurnMessage extends Message{
    @Serial
    private static final long serialVersionUID = -6588876372077057029L;
    private final String message = "It's your turn!";

    public YourTurnMessage() {
        setType(TypeMessage.YOUR_TURN);
    }

    public String getContent() {
        return message;
    }
}


