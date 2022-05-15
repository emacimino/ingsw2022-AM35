package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Model.SchoolsLands.Archipelago;

public class MoveMotherNature extends Message{
    private static final long serialVersionUID = 5541410460045942543L;

    public MoveMotherNature(String username, Archipelago archipelago, GameStateMessage moveMotherNature) {
        super(username,archipelago,GameStateMessage.MOVE_MOTHER_NATURE);
    }
}
