package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;

import java.util.List;

public class CurrentGameMessage extends Message {
    private static final long serialVersionUID = -9016040814084856548L;
    private Game game;
    public CurrentGameMessage(Game game) {
        this.game = game;
    }
}
