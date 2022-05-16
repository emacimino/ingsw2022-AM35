package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Model.FactoryMatch.Player;

import java.util.List;

public class MatchFourPlayersInfoMessage extends Message {
    public MatchFourPlayersInfoMessage(String server, List<List<Player>> teams, GameStateMessage matchInfo) {
        super(server,teams,matchInfo);
    }
}
