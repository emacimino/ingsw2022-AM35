package it.polimi.ingsw.NetworkUtilities;

import java.io.Serial;

/**
 * Class used to send generic messages
 */
public class GenericMessage extends Message{
    @Serial
    private static final long serialVersionUID = 3876787195775446713L;
    private final Object content;

    /**
     * Constructor of the class
     * @param content the content of the message
     */
    public GenericMessage(Object content) {
        this.content = content;
        setType(TypeMessage.GENERIC_MESSAGE);
    }

    /**
     * Method that returns the content inside the message
     * @return an object
     */
    public Object getContent() {
        return content;
    }
}
