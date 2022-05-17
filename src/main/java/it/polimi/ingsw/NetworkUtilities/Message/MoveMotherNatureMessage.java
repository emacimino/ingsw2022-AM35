package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;

public class MoveMotherNatureMessage extends Message{

        private static final long serialVersionUID = -9016040814084856548L;
        private Archipelago archipelago;

        public MoveMotherNatureMessage(Archipelago archipelago) {
            this.archipelago = archipelago;
            setType(GameStateMessage.MOVE_MOTHER_NATURE);
        }

    public Archipelago getArchipelago() {
        return archipelago;
    }
}

