package it.polimi.ingsw.NetworkUtilities;

import java.io.Serial;

/**
 * Class used to communicate the end of the game
 */
public class EndOfGameMessage extends Message{
    @Serial
    private static final long serialVersionUID = 5432746622257658252L;
    public String content = "A client is unreachable, the game ends now";

    /**
     * Constructor of the class
     */
    public EndOfGameMessage() {
        setType(TypeMessage.CLIENT_UNREACHABLE);
    }
}
