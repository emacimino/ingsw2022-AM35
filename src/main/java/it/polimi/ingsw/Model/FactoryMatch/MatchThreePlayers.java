package it.polimi.ingsw.Model.FactoryMatch;


import it.polimi.ingsw.Model.Exception.ExceptionGame;

import java.util.ArrayList;
import java.util.List;

public class MatchThreePlayers extends Match{
    private  int numberOfPlayers;
    private final Game game;
    private final List<Player> players = new ArrayList<>();
    private List<Player> actionPhaseOrderOfPlayers = new ArrayList<>();


    public MatchThreePlayers() {
        super.setNumberOfPlayers(3);
        super.setNumberOfMovableStudents(4);
        super.setNumberOfClouds(3);
        super.setNumberOfTowers(6);
        super.setNumberOfStudentInEntrance(9);
        super.setNumberOfStudentsOnCLoud(4);
        game = new Game(getNumberOfStudentInEntrance(), getNumberOfMovableStudents());
        super.setGame(game);
    }











}
