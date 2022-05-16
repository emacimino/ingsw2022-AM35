package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;

import java.util.List;

public class CurrentGameMessage extends Message {
    private Game game;
    public CurrentGameMessage(Game game) {
        this.game = game;
    }
}
