package it.polimi.ingsw.NetworkUtilities.Message;

public class TowerBuilt extends Message {
    GameStateMessage type;


    public TowerBuilt(Object content) {
        type = GameStateMessage.TOWER_BUILT;
    }

    @Override
    public GameStateMessage getType() {
        return type;
    }

}
