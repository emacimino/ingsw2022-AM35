package it.polimi.ingsw.Model.FactoryMatch;

public class BasicMatchThreePlayers extends BasicMatch {

    public BasicMatchThreePlayers() {
        super.setNumberOfPlayers(3);
        super.setNumberOfMovableStudents(4);
        super.setNumberOfClouds(3);
        super.setNumberOfTowers(6);
        super.setNumberOfStudentInEntrance(9);
        super.setNumberOfStudentsOnCLoud(4);
        super.setGame(new Game(getNumberOfStudentInEntrance(), getNumberOfMovableStudents()));
    }











}
