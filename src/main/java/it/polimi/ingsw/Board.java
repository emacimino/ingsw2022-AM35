package it.polimi.ingsw;

import java.util.*;

public class Board {
    private Collection<Tower> TowersInBoard = new HashSet<Tower>(){};
    private Collection<Professor> ProfessorInBoard = new HashSet<Professor>(){};
    private Collection<Student> GreenStudentInBoard = new HashSet<Student>(){};
    private Collection<Student> BlueStudentInBoard = new HashSet<Student>(){};
    private Collection<Student> RedStudentInBoard = new HashSet<Student>(){};
    private Collection<Student> YellowStudentInBoard = new HashSet<Student>(){};
    private Collection<Student> PinkStudentInBoard = new HashSet<Student>(){};
    private Collection<Student> StudentInEntrance = new HashSet<Student>(){};

    public Collection<Tower> getTowersInBoard() {
        return TowersInBoard;
    }

    public void setTowersInBoard(Collection<Tower> towersInBoard) {
        TowersInBoard = towersInBoard;
    }

    public Collection<Professor> getProfessorInBoard() {
        return ProfessorInBoard;
    }

    public void setProfessorInBoard(Collection<Professor> professorInBoard) {
        ProfessorInBoard = professorInBoard;
    }

    public Collection<Student> getGreenStudentInBoard() {
        return GreenStudentInBoard;
    }

    public void setGreenStudentInBoard(Collection<Student> greenStudentInBoard) {
        GreenStudentInBoard = greenStudentInBoard;
    }

    public Collection<Student> getBlueStudentInBoard() {
        return BlueStudentInBoard;
    }

    public void setBlueStudentInBoard(Collection<Student> blueStudentInBoard) {
        BlueStudentInBoard = blueStudentInBoard;
    }

    public Collection<Student> getRedStudentInBoard() {
        return RedStudentInBoard;
    }

    public void setRedStudentInBoard(Collection<Student> redStudentInBoard) {
        RedStudentInBoard = redStudentInBoard;
    }

    public Collection<Student> getYellowStudentInBoard() {
        return YellowStudentInBoard;
    }

    public void setYellowStudentInBoard(Collection<Student> yellowStudentInBoard) {
        YellowStudentInBoard = yellowStudentInBoard;
    }

    public Collection<Student> getPinkStudentInBoard() {
        return PinkStudentInBoard;
    }

    public void setPinkStudentInBoard(Collection<Student> pinkStudentInBoard) {
        PinkStudentInBoard = pinkStudentInBoard;
    }

    public Collection<Student> getStudentInEntrance() {
        return StudentInEntrance;
    }

    public void setStudentInEntrance(Collection<Student> studentInEntrance) {
        StudentInEntrance = studentInEntrance;
    }

    public Board(Collection<Tower> towersInBoard, Collection<Professor> professorInBoard, Collection<Student> greenStudentInBoard, Collection<Student> blueStudentInBoard, Collection<Student> redStudentInBoard, Collection<Student> yellowStudentInBoard, Collection<Student> pinkStudentInBoard, Collection<Student> studentInEntrance) {
        TowersInBoard = towersInBoard;
        ProfessorInBoard = professorInBoard;
        GreenStudentInBoard = greenStudentInBoard;
        BlueStudentInBoard = blueStudentInBoard;
        RedStudentInBoard = redStudentInBoard;
        YellowStudentInBoard = yellowStudentInBoard;
        PinkStudentInBoard = pinkStudentInBoard;
        StudentInEntrance = studentInEntrance;
    }
}
