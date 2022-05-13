package it.polimi.ingsw.NetworkUtilities.Message;

import java.io.Serial;
import java.io.Serializable;

public abstract class Message implements Serializable {
    @Serial
    private static final long serialVersionUID = 6345566953463396637L;

    private String nickname;
    private Object content;
    private GameStateMessage type;

    public Message(String nickname, Object content, GameStateMessage type) {
        this.nickname = nickname;
        this.content = content;
        this.type = type;
    }

    public Message(String nickname, GameStateMessage Reply) {
        this.nickname = nickname;
        this.type = Reply;
    }


    public Object getContent() {
        return content;
    }

    public GameStateMessage getType() {
        return type;
    }


    @Override
    public String toString() {
        return "Message{" +
                "message='" + content + '\'' +
                ", type=" + type +
                '}';
    }
}
