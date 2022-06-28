package it.polimi.ingsw.NetworkUtilities;

import java.io.Serial;

/**
 * CLass used in the login process
 */
public class LoginResponse extends Message{
    @Serial
    private static final long serialVersionUID = -5937280514385176931L;
    private final String name;
    private final int numberOfPlayer;
    private final boolean isExpertMatch;

    /**
     * Constructor of the class
     * @param name name of the player
     * @param numberOfPlayer number of players
     * @param isExpertMatch expert match or not
     */
    public LoginResponse(String name, int numberOfPlayer, boolean isExpertMatch) {
        this.name = name;
        this.numberOfPlayer = numberOfPlayer;
        this.isExpertMatch = isExpertMatch;
        setType(TypeMessage.LOGIN_RESPONSE);
    }

    /**
     * Method that returns the name of the player
     * @return a string
     */
    public String getName() {
        return name;
    }

    /**
     * Method that returns the number of the players
     * @return an integer
     */
    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }

    /**
     * Method that returns a boolean contained inside the message
     * @return a boolean (true if match is in expert mode)
     */
    public boolean isExpertMatch() {
        return isExpertMatch;
    }

    /**
     * toString() method for this class
     * @return
     */
    @Override
    public String toString() {
        return "LoginResponse{" +
                "name='" + name + '\'' +
                ", numberOfPlayer=" + numberOfPlayer +
                ", isExpertMatch=" + isExpertMatch +
                '}';
    }
}
