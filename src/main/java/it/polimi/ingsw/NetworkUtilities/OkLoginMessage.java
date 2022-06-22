package it.polimi.ingsw.NetworkUtilities;

import java.io.Serial;

public class OkLoginMessage extends Message{
    @Serial
    private static final long serialVersionUID = 7359401583345899739L;

    public OkLoginMessage() {
        setType(TypeMessage.OK_LOGIN);
    }
}
