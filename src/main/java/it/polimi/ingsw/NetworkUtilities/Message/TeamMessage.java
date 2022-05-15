package it.polimi.ingsw.NetworkUtilities.Message;

public class TeamMessage extends Message {
    public TeamMessage(String nickname, Object content, GameStateMessage type) {
        super(nickname, content, GameStateMessage.TEAM);
    }
}
