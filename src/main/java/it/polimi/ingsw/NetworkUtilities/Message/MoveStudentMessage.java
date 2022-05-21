package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Student;

public class MoveStudentMessage extends Message{
    private static final long serialVersionUID = 3712768545549517262L;
    private final Student student;
    private final Archipelago archipelago;

    public MoveStudentMessage(Student student, Archipelago archipelago) {
        this.student = student;
        this.archipelago = archipelago;
        setType(TypeMessage.MOVE_STUDENT);
    }

    public Student getStudent() {
        return student;
    }

    public Archipelago getArchipelago() {
        return archipelago;
    }


}
