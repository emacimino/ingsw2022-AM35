package it.polimi.ingsw.NetworkUtilities;

import java.io.Serial;

/**
 * Class used to comunicate the end of the turn
 */
public class EndTurnMessage extends Message{
    @Serial
    private static final long serialVersionUID = 3002290940031968259L;
    private final String message = "Please, wait for your turn";

    /**
     * Constructor of the class
     */
    public EndTurnMessage() {
        setType(TypeMessage.END_OF_TURN);
    }

    /**
     * Method that returns a string contained in the message
     * @return a string
     */
    public String getContent() {
        return message;
    }
}
