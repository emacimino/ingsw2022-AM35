package it.polimi.ingsw.NetworkUtilities;

import java.io.Serial;

/**
 * Class used in the login process
 */
public class LoginRequest extends Message{
    @Serial
    private static final long serialVersionUID = -5889180209787581414L;

    /**
     * Constructor of the class
     */
    public LoginRequest() {
        setType(TypeMessage.REQUEST_LOGIN);
    }
}
