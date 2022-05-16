package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Model.FactoryMatch.Player;

import java.io.Serial;
import java.io.Serializable;

public abstract class Message implements Serializable {
    @Serial
    private static final long serialVersionUID = 6345566953463396637L;
    private String userName;
    private Object content;
    private GameStateMessage type;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public void setType(GameStateMessage type) {
        this.type = type;
    }

    public GameStateMessage getType() {
        return type;
    }
}
