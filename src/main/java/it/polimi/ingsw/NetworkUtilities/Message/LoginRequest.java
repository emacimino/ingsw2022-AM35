package it.polimi.ingsw.NetworkUtilities.Message;

public class LoginRequest extends Message{
    private static final long serialVersionUID = -5889180209787581414L;

    public LoginRequest() {
        setType(TypeMessage.REQUEST_LOGIN);
    }
}
