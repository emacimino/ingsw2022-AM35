package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Observer.Observable;

public class LoginController extends Observable {
    private String name;
    private String username;
    private boolean joinAMatch = false, createAMatch = false;
    private MatchObserver matchObserver;

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setJoinAMatch(boolean joinAMatch) {
        this.joinAMatch = joinAMatch;
    }

    public void setCreateAMatch(boolean createAMatch) {
        this.createAMatch = createAMatch;
    }
}
