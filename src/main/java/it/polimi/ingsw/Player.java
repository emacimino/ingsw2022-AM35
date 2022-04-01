package it.polimi.ingsw;

public class Player {
    private String name;
    private String username;

    /**
     * constructor of Player Class
     * @param name  name of the player
     * @param username username of the player
     */
    public Player(String name, String username) {
        this.name = name;
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
    public String getName() {
        return name;
    }
}
