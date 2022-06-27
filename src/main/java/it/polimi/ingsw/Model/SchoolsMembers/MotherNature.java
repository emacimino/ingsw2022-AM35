package it.polimi.ingsw.Model.SchoolsMembers;

import it.polimi.ingsw.Model.Exception.ExceptionGame;

import java.io.Serial;
import java.io.Serializable;

/**
 * MotherNature, she moves around the island and decides where the action of the game stand
 */
public class MotherNature implements Serializable {
    @Serial
    private static final long serialVersionUID = -9004028422001487757L;
    private int position;

    /**
     *
     * @return the position of mother nature on the islands
     */
    public int getPosition() {
        return position;
    }

    /**
     *
     * @param position indicate where to place mother nature
     */
    public void setPosition(int position) throws ExceptionGame {
            this.position = position;
    }
}
