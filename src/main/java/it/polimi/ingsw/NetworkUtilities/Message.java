package it.polimi.ingsw.NetworkUtilities;

import java.io.Serial;
import java.io.Serializable;

/**
 * Generic class that is extended by other messages
 */
public abstract class Message implements Serializable {
    @Serial
    private static final long serialVersionUID = 6345566953463396637L;
    private TypeMessage type;
    private String message = "";

    /**
     * Method that returns the string contained in the message
     * @return a string
     */
    public String getMessage() {
        return message;
    }

    /**
     * Method used to set the message contained in the string
     * @param message a string to be encapsulated in the message
     */
    protected void setMessage(String message) {
        this.message = message;
    }

    /**
     * Method used to set the type of message
     * @param type a type from the enumeration TypeMessage
     */
    public void setType(TypeMessage type) {
        this.type = type;
    }

    /**
     * Method that returns the type of the messsage
     * @return a TypeMessage
     */
    public TypeMessage getType() {
        return type;
    }


}
