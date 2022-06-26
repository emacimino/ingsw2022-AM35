package it.polimi.ingsw.Model.FactoryMatch;


import it.polimi.ingsw.Observer.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Class containing the base setting for a 3 players match
 */
public class BasicMatchThreePlayers extends BasicMatch {
    private  int numberOfPlayers;
    private final Game game;
    private final List<Player> players = new ArrayList<>();
    private List<Player> actionPhaseOrderOfPlayers = new ArrayList<>();

    /**
     * Constructor of the class
     */
    public BasicMatchThreePlayers() {
        super.setNumberOfPlayers(3);
        super.setNumberOfMovableStudents(4);
        super.setNumberOfClouds(3);
        super.setNumberOfTowers(6);
        super.setNumberOfStudentInEntrance(9);
        super.setNumberOfStudentsOnCLoud(4);
        game = new Game(getNumberOfStudentInEntrance(), getNumberOfMovableStudents());
        super.setActualGame(game);
    }











}
