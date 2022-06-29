package it.polimi.ingsw.NetworkUtilities;

import java.io.Serial;

/**
 * Class used to communicate errors
 */
public class ErrorMessage extends Message {
    @Serial
    private static final long serialVersionUID = 8580493153335998493L;
    /**
     * Constructor of the class
     */
    private final String error;
    public ErrorMessage(String message) {
        setType(TypeMessage.ERROR);
        this.error = message;
    }

    /**
     * Method that returns the error contained in the message
     * @return a string
     */
    public String getError() {
        return error;
    }
}
