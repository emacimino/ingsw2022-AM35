package it.polimi.ingsw.NetworkUtilities;

import java.io.Serial;

/**
 * Class used to communicate the student movement
 */
public class MoveStudentMessage extends Message{
    @Serial
    private static final long serialVersionUID = 3712768545549517262L;
    private final Integer student;
    private Integer archipelago;
    private boolean moveToBoard;

    /**
     * Constructor of the class
     * @param student the student to be moved
     * @param archipelago the archipelago target
     */
    public MoveStudentMessage(Integer student, Integer archipelago) {
        this.student = student;
        setType(TypeMessage.MOVE_STUDENT);
        if(archipelago != null) {
            this.archipelago = archipelago;
            moveToBoard = false;
        }else {
            moveToBoard = true;
        }
    }

    /**
     * Method that returns a boolean indicating the target of the movement
     * @return a boolean
     */
    public boolean moveToBoard() {
        return moveToBoard;
    }

    public Integer getStudent() {
        return student;
    }

    public Integer getArchipelago() {
        return archipelago;
    }

    @Override
    public String toString() {
        return "MoveStudentMessage{" +
                "student=" + student +
                ", archipelago=" + archipelago +
                ", moveToBoard=" + moveToBoard +
                '}';
    }
}
