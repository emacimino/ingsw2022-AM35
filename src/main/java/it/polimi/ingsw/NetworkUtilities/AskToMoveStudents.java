package it.polimi.ingsw.NetworkUtilities;

import java.io.Serial;

public class AskToMoveStudents extends Message{
    @Serial
    private static final long serialVersionUID = 2236354021060235326L;

    public AskToMoveStudents() {
        setType(TypeMessage.ASK_TO_MOVE_STUDENT);
    }
}
