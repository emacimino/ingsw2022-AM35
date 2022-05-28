package it.polimi.ingsw.NetworkUtilities.Message;

public class OkLoginMessage extends Message{
    private static final long serialVersionUID = 7359401583345899739L;

    public OkLoginMessage() {
        setType(TypeMessage.OK_LOGIN);
    }
}
