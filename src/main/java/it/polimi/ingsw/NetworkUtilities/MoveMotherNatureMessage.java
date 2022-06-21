package it.polimi.ingsw.NetworkUtilities;

import java.io.Serial;

public class MoveMotherNatureMessage extends Message{

        @Serial
        private static final long serialVersionUID = -9016040814084856548L;
        private Integer archipelago;

        public MoveMotherNatureMessage(Integer indexArchipelago) {
            this.archipelago = indexArchipelago;
            setType(TypeMessage.MOVE_MOTHER_NATURE);
        }

    public Integer getArchipelago() {
        return archipelago;
    }

    @Override
    public String toString() {
        return "MoveMotherNatureMessage{" +
                "archipelago=" + archipelago +
                '}';
    }
}

