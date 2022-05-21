package it.polimi.ingsw.NetworkUtilities.Message;

public class RequestLoginMessage extends Message{
    private static final long serialVersionUID = 2277665904129335384L;

    public RequestLoginMessage() {
        setType(TypeMessage.REQUEST_LOGIN);
    }
}
