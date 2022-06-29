package it.polimi.ingsw.NetworkUtilities;

import it.polimi.ingsw.Model.FactoryMatch.Player;

import java.io.Serial;
import java.util.List;

/**
 * Class used to communicate the end of the match
 */
public class EndMatchMessage extends Message{
    @Serial
    private static final long serialVersionUID = 5766057707166659044L;

    private final List<Player> winners;

    /**
     * Constructor of the class
     * @param winners a list containing all the winners
     */
    public EndMatchMessage(List<Player> winners) {
        this.winners = winners;
        setType(TypeMessage.END_MATCH);
    }

    /**
     * Method that returns a list of winners
     * @return a list of player who won the game
     */
    public List<Player> getWinners() {
        return winners;
    }
}
