package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Model.SchoolsLands.Archipelago;

public class MoveMotherNatureMessage extends Message{

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

