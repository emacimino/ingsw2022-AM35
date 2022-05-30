package it.polimi.ingsw.NetworkUtilities.Message;

import java.io.Serial;

public class Ping extends Message{
    @Serial
    private static final long serialVersionUID = 8550122873407340272L;

    public Ping() {
        super.setType(TypeMessage.PING);
    }
}
