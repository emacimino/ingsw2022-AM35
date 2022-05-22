package it.polimi.ingsw.Model.SchoolsMembers;

import it.polimi.ingsw.Model.Exception.ExceptionGame;

import java.io.Serializable;

public class MotherNature implements Serializable {
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
       // if(this.position != position)
            this.position = position;
      //  else
        //    throw new ExceptionGame("Mother nature is already placed in this archipelago");
    }
}
