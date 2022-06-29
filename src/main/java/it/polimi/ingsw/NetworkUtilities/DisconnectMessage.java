package it.polimi.ingsw.NetworkUtilities;

import java.io.Serial;

/**
 * Class used to send disconnection messages
 */
public class DisconnectMessage extends Message{
    @Serial
    private static final long serialVersionUID = 451445476138872766L;
    private String username;

    /**
     * Constructor of the class
     */
    public DisconnectMessage() {
        setType(TypeMessage.DISCONNECT);
    }

    /**
     * Set method for username
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Method that returns the username in the message
     * @return a username
     */
    public String getUsername() {
        return username;
    }
}
