package it.polimi.ingsw.NetworkUtilities;

import it.polimi.ingsw.Model.Wizard.Board;

import java.io.Serial;

/**
 * Current board Message
 */
public class BoardMessage extends Message {
    @Serial
    private static final long serialVersionUID = -7571399754483223952L;
    private final Board board;

    /**
     * Constructor class
     * @param board current board
     */
    public BoardMessage(Board board) {
        this.board = board;
        setType(TypeMessage.BOARD);
    }

    /**
     * Getter of the board
     * @return board in the message
     */
    public Board getBoard() {
        return board;
    }
}
