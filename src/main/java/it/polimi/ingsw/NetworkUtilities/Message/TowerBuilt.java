package it.polimi.ingsw.NetworkUtilities.Message;

public class TowerBuilt extends Message {
    public TowerBuilt(String nickname, Object content, GameStateMessage type) {
        super(nickname, content, GameStateMessage.TOWER_BUILT);
    }
}
