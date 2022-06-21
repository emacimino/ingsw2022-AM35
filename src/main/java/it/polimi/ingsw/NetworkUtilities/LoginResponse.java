package it.polimi.ingsw.NetworkUtilities;

import java.io.Serial;

public class LoginResponse extends Message{
    @Serial
    private static final long serialVersionUID = -5937280514385176931L;
    private final String name;
    private final int numberOfPlayer;
    private final boolean isExpertMatch;

    public LoginResponse(String name, int numberOfPlayer, boolean isExpertMatch) {
        this.name = name;
        this.numberOfPlayer = numberOfPlayer;
        this.isExpertMatch = isExpertMatch;
        setType(TypeMessage.LOGIN_RESPONSE);
    }

    public String getName() {
        return name;
    }

    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }

    public boolean isExpertMatch() {
        return isExpertMatch;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "name='" + name + '\'' +
                ", numberOfPlayer=" + numberOfPlayer +
                ", isExpertMatch=" + isExpertMatch +
                '}';
    }
}
