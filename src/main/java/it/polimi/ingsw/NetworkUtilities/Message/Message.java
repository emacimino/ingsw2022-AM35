package it.polimi.ingsw.NetworkUtilities.Message;

import java.io.Serializable;

public class Message {
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
