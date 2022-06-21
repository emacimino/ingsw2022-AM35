package it.polimi.ingsw.NetworkUtilities;

import it.polimi.ingsw.Model.FactoryMatch.Game;

import java.io.Serial;

public class CurrentGameMessage extends Message {
    @Serial
    private static final long serialVersionUID = -9016040814084856548L;
    private Game game;

    public CurrentGameMessage(Game game) {
        this.game = game;
        setType(TypeMessage.GAME_INFO);
    }

    public Game getGame() {
        return game;
    }


}
