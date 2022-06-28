package it.polimi.ingsw.NetworkUtilities;

import it.polimi.ingsw.Model.Wizard.Board;

import java.io.Serial;

/**
 * Class used to send a Board
 */
public class BoardMessage extends Message {
    @Serial
    private static final long serialVersionUID = -7571399754483223952L;
    private final Board board;

    /**
     * Constructor of the class
     * @param board the board to send
     */
    public BoardMessage(Board board) {
        this.board = board;
        setType(TypeMessage.BOARD);
    }

    /**
     * Method that returns the board contained in the message
     * @return a board
     */
    public Board getBoard() {
        return board;
    }
}
