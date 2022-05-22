package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Model.Wizard.Board;

import java.io.Serializable;

public class BoardMessage extends Message {
    private static final long serialVersionUID = -7571399754483223952L;
    private final Board board;

    public BoardMessage(Board board) {
        this.board = board;
        setType(TypeMessage.BOARD);
    }

    public Board getBoard() {
        return board;
    }
}
