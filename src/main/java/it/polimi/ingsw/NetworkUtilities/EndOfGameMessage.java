package it.polimi.ingsw.NetworkUtilities;

import java.io.Serial;

public class EndOfGameMessage extends Message{
    @Serial
    private static final long serialVersionUID = 5432746622257658252L;
    public String content = "A client is unreachable, the game ends now";

    public EndOfGameMessage() {
        setType(TypeMessage.CLIENT_UNREACHABLE);
    }
}
