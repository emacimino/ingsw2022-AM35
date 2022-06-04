package it.polimi.ingsw.NetworkUtilities.Message;

public class DisconnectMessage extends Message{
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
