package it.polimi.ingsw.NetworkUtilities.Message;

import java.io.Serial;
import java.io.Serializable;

public abstract class Message implements Serializable {
    @Serial
    private static final long serialVersionUID = 6345566953463396637L;

    private Object message;
    private GameStateMessage type;

    public Message(Object message, GameStateMessage type) {
        this.message = message;
        this.type = type;
    }



    public Object getMessage() {
        return message;
    }

    public GameStateMessage getType() {
        return type;
    }


    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", type=" + type +
                '}';
    }
}
