package it.polimi.ingsw.NetworkUtilities.Message;

import java.io.Serial;

public class MovedProfessor extends Message {
    @Serial
    private static final long serialVersionUID = -3805227333945220938L;
    public MovedProfessor(String nickname, Object content, GameStateMessage type) {
        super(nickname, content, type.MOVED_PROFESSOR);
    }
}
