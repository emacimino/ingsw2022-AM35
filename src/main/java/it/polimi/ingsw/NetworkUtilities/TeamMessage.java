package it.polimi.ingsw.NetworkUtilities;

import it.polimi.ingsw.Model.FactoryMatch.Player;

import java.io.Serial;
import java.util.List;

/**
 * Class used to send the teams
 */
public class TeamMessage extends Message{
    @Serial
    private static final long serialVersionUID = 18630890841218448L;
    final private List<Player> teamOne;
    final private List<Player> teamTwo;

    /**
     * Constructor of the class
     * @param teamOne a list of players in team one
     * @param teamTwo a list of players in team two
     */
    public TeamMessage(List<Player> teamOne, List<Player> teamTwo) {
        setType(TypeMessage.TEAM_MESSAGE);
        this.teamOne = teamOne;
        this.teamTwo = teamTwo;
    }

    /**
     * Method that returns a list of players in team one
     * @return a list of players in team one
     */
    public List<Player> getTeamOne() {
        return teamOne;
    }

    /**
     * Method that returns a list of players in team two
     * @return a list of players in team two
     */
    public List<Player> getTeamTwo() {
        return teamTwo;
    }
}
