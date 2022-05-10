package it.polimi.ingsw.Model.FactoryMatch;

public class Player {
    private final String name;
    private final String username;
    private MatchObserver observer;

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

    /**
     *
     * @return the observer associated to the player for the controller
     */
    public MatchObserver getObserver() {
        return observer;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public void setObserver(MatchObserver observer) {
        this.observer = observer;
    }
}
