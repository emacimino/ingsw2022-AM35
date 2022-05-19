package it.polimi.ingsw.Model.FactoryMatch;

public class Player {
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
     *
     * @return the name of the player
     */


    @Override
    public String toString() {
        return "Player{" +
                ", username='" + username + '\'' +
                '}';
    }

}
