package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Student;

import java.io.Serial;

public class MoveMotherNature extends Message{
    @Serial
    private static final long serialVersionUID = 5541410460045942543L;
    private final Archipelago archipelago;

    public MoveMotherNature(Archipelago archipelago){
        setType(GameStateMessage.MOVE_MOTHER_NATURE);
        this.archipelago = archipelago;
    }

    public Archipelago getArchipelago() {
        return archipelago;
    }
}
