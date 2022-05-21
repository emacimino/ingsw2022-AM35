package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.Wizard;

public class MoveStudentMessage extends Message{
    private static final long serialVersionUID = 3712768545549517262L;
    private final Student student;
    private Archipelago archipelago;
    private boolean moveToBoard;

    public MoveStudentMessage(Student student, Archipelago archipelago) {
        this.student = student;
        setType(TypeMessage.MOVE_STUDENT);
        if(archipelago != null) {
            this.archipelago = archipelago;
            moveToBoard = false;
        }else {
            moveToBoard = true;
        }
    }

    public boolean moveToBoard() {
        return moveToBoard;
    }

    public Student getStudent() {
        return student;
    }

    public Archipelago getArchipelago() {
        return archipelago;
    }


}
