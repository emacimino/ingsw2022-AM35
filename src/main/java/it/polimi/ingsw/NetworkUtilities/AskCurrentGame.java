package it.polimi.ingsw.NetworkUtilities;

import java.io.Serial;
import java.io.Serializable;

/**
 *
 */
public class AskCurrentGame extends Message implements Serializable {
    @Serial
    private static final long serialVersionUID = -5639836402016085459L;

    public AskCurrentGame() {
        setType(TypeMessage.REQUEST_CURRENT_GAME);
    }
}
