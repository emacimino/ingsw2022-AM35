package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Model.FactoryMatch.Player;

import java.io.Serial;
import java.io.Serializable;

public abstract class Message implements Serializable {
    @Serial
    private static final long serialVersionUID = 6345566953463396637L;
    private GameStateMessage type;

    public void setType(GameStateMessage type) {
        this.type = type;
    }

    public GameStateMessage getType() {
        return type;
    }


}
