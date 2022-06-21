package it.polimi.ingsw.NetworkUtilities;

import java.io.Serial;

public class LoginRequest extends Message{
    @Serial
    private static final long serialVersionUID = -5889180209787581414L;

    public LoginRequest() {
        setType(TypeMessage.REQUEST_LOGIN);
    }
}
