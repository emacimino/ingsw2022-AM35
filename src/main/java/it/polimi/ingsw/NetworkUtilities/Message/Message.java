package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Controller.GameState;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public abstract class Message implements Serializable {
    //@Serial
    //private static final long serialVersionUID = 6345566953463396637L;

    private String username;
    private Object contentOne, contentTwo;
    private GameStateMessage type;
    private Player player;
    private GameState gamePhase;

    public Message(String username, Object content, GameStateMessage type) {
        this.username = username;
        this.contentOne = content;
        this.type = type;
    }

    public Message(String username, Object content, GameStateMessage type, GameState gamePhase) {
        this.username = username;
        this.contentOne = content;
        this.type = type;
        this.gamePhase = gamePhase;
    }

    public Message(String username, Object contentOne, Object contentTwo, GameStateMessage type, GameState gamePhase) {
        this.username = username;
        this.contentOne = contentOne;
        this.contentTwo = contentTwo;
        this.type = type;
        this.gamePhase = gamePhase;
    }

    public Message(String username, GameStateMessage Reply) {
        this.username = username;
        this.type = Reply;
    }

    public Message(Player player, Object content){
        this.player = player;
        this.contentOne = content;
    }

    public Message(String username, Object contentOne, Object contentTwo, GameStateMessage type) {
        this.username = username;
        this.contentOne = contentOne;
        this.contentTwo = contentTwo;
        this.type = type;
    }

    public Object getContentOne() {
        return contentOne;
    }

    public Object getContentTwo() {
        return contentTwo;
    }


    public GameStateMessage getType() {
        return type;
    }

    public GameState getGamePhase() {
        return gamePhase;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + contentOne + '\'' +
                ", type=" + type +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public Player getPlayer() {
        return player;
    }


}
