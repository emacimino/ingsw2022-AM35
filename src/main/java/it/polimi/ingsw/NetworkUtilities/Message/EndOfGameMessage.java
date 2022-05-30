package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Model.FactoryMatch.Player;

import java.util.List;

public class EndOfGameMessage extends Message{
    private static final long serialVersionUID = 5432746622257658252L;
    public String content = "A client is unreachable, the game ends now";
}
