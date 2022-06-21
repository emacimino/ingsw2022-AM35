package it.polimi.ingsw.NetworkUtilities;

import java.io.Serial;

public class DisconnectMessage extends Message{
    @Serial
    private static final long serialVersionUID = 451445476138872766L;
    private String username;

    public DisconnectMessage() {
        setType(TypeMessage.DISCONNECT);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
