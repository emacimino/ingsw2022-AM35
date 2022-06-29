package it.polimi.ingsw.NetworkUtilities;

import java.io.Serial;

/**
 * Class used to signal that the login was successful
 */
public class OkLoginMessage extends Message{
    @Serial
    private static final long serialVersionUID = 7359401583345899739L;

    /**
     * Constructor of the class
     */
    public OkLoginMessage() {
        setType(TypeMessage.OK_LOGIN);
    }
}
