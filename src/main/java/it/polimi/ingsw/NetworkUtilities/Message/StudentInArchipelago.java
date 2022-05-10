package it.polimi.ingsw.NetworkUtilities.Message;


import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Student;

public class StudentInArchipelago extends Message{
    private static final long serialVersionUID = -6291426948366264479L;

    public StudentInArchipelago(Student student, Archipelago archipelago){
        super(student, GameStateMessage.STUDENTINARCHIPELAGO);
    }
}
