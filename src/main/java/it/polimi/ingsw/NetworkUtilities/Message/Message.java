package it.polimi.ingsw.NetworkUtilities.Message;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 6345566953463396637L;

    private String message;
    private GameStateMessage type;

    public Message(String message, GameStateMessage type) {
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
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
