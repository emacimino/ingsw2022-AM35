package it.polimi.ingsw.NetworkUtilities;

import it.polimi.ingsw.Model.FactoryMatch.Player;

import java.io.Serial;
import java.util.List;

public class TeamMessage extends Message{
    @Serial
    private static final long serialVersionUID = 18630890841218448L;
    final private List<Player> teamOne;
    final private List<Player> teamTwo;

    public TeamMessage(List<Player> teamOne, List<Player> teamTwo) {
        setType(TypeMessage.TEAM_MESSAGE);
        this.teamOne = teamOne;
        this.teamTwo = teamTwo;
    }

    public List<Player> getTeamOne() {
        return teamOne;
    }

    public List<Player> getTeamTwo() {
        return teamTwo;
    }
}
