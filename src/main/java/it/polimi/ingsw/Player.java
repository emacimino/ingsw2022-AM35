package it.polimi.ingsw;

public class Player {
    private String name;
    private String username;

    public Player(String name, String username) {
        this.name = name;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }
}
