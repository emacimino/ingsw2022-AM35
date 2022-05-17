package it.polimi.ingsw.NetworkUtilities.Message;


import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import javafx.scene.shape.Arc;

public class MoveStudentInArchipelago extends Message{
    private static final long serialVersionUID = -6291426948366264479L;
    private final Student student;
    private final Archipelago archipelago;

    public MoveStudentInArchipelago(String nickname, Student student, Archipelago archipelago){
        setType(GameStateMessage.STUDENT_IN_ARCHIPELAGO);
        this.student = student;
        this.archipelago = archipelago;
    }

    public Student getStudent() {
        return student;
    }

    public Archipelago getArchipelago() {
        return archipelago;
    }
}
