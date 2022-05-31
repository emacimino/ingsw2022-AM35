package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.SchoolsMembers.Color;

import java.io.Serial;
import java.util.Map;

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
