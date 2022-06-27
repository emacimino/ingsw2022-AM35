package it.polimi.ingsw.Model.FactoryMatch;


import java.io.Serial;
import java.io.Serializable;

/**
 * This class support the creation of the game through the creation of a username list
 */
public class Player implements Serializable {
    @Serial
    private final static long serialVersionUID = 6140152117226239022L;
    private final String username;

    /**
     * constructor of Player Class
     * @param username username of the player
     */
    public Player(String username) {
        this.username = username;
    }

    /**
     *
     * @return the username of the player
     */
    public String getUsername() {
        return username;
    }
    

    /**
     * Override of toString
     * @return the player username
     */
    @Override
    public String toString() {
        return "Player{" +
                "username='" + username + '\'' +
                '}';
    }


}
