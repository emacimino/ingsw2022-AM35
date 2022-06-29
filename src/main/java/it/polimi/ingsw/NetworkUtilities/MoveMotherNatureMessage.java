package it.polimi.ingsw.NetworkUtilities;

import java.io.Serial;

/**
 * Class used to send the target of the movement to mother nature
 */
public class MoveMotherNatureMessage extends Message{

        @Serial
        private static final long serialVersionUID = -9016040814084856548L;
        private Integer archipelago;

    /**
     * Constructor of the class
     * @param indexArchipelago archipelago target of mother nature
     */
        public MoveMotherNatureMessage(Integer indexArchipelago) {
            this.archipelago = indexArchipelago;
            setType(TypeMessage.MOVE_MOTHER_NATURE);
        }

    /**
     * Method that returns the archipelago contained in the message
     * @return an integer representing the index of the archipelago
     */
    public Integer getArchipelago() {
        return archipelago;
    }

    /**
     * toString() method for this class
     * @return
     */
    @Override
    public String toString() {
        return "MoveMotherNatureMessage{" +
                "archipelago=" + archipelago +
                '}';
    }
}

