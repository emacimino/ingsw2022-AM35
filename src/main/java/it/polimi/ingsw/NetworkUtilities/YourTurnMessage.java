package it.polimi.ingsw.NetworkUtilities;

import java.io.Serial;

/**
 * Class used to communicate the start of the player's turn
 */
public class YourTurnMessage extends Message{
    @Serial
    private static final long serialVersionUID = -6588876372077057029L;
    private final String message = "It's your turn!";

    /**
     * Constructor of the class
     */
    public YourTurnMessage() {
        setType(TypeMessage.YOUR_TURN);
    }

    /**
     * Method that returns the string contained in the message
     * @return a string
     */
    public String getContent() {
        return message;
    }
}


