package it.polimi.ingsw.NetworkUtilities;

import java.io.Serial;

/**
 * Method used to ask the current game
 */
public class AskCurrentGame extends Message {
    @Serial
    private static final long serialVersionUID = -5639836402016085459L;

    /**
     * Constructor of the class
     */
    public AskCurrentGame() {
        setType(TypeMessage.REQUEST_CURRENT_GAME);
    }
}
