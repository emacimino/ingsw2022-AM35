package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;

import java.util.List;

public class MatchInfoMessage extends Message {
    public MatchInfoMessage(String server, List<List<Player>> players, List<Archipelago> archipelagos, GameStateMessage matchInfo) {
        super(server,players,archipelagos,GameStateMessage.MATCH_INFO);
    }
}
