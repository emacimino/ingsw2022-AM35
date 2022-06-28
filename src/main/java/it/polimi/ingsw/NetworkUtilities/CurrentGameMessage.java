package it.polimi.ingsw.NetworkUtilities;

import it.polimi.ingsw.Model.FactoryMatch.Game;

import java.io.Serial;

/**
 * Class used to send the game class
 */
public class CurrentGameMessage extends Message {
    @Serial
    private static final long serialVersionUID = -9016040814084856548L;
    private Game game;

    /**
     * Constructor of the class
     * @param game the current game
     */
    public CurrentGameMessage(Game game) {
        this.game = game;
        setType(TypeMessage.GAME_INFO);
    }

    /**
     * Method that returns the game contained in the message
     * @return a game
     */
    public Game getGame() {
        return game;
    }


}
