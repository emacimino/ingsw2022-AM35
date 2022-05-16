package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Model.FactoryMatch.Player;

public class TeamCaptainForEveryPlayerInfo extends Message {
    public TeamCaptainForEveryPlayerInfo(String server, Player captainTeamOfPlayer, GameStateMessage matchInfo) {
        super(server,captainTeamOfPlayer,matchInfo);
    }
}
