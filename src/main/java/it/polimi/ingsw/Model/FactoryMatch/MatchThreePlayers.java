package it.polimi.ingsw.Model.FactoryMatch;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;

import java.util.ArrayList;
import java.util.List;

public class MatchThreePlayers extends Match{
    private final int numberOfPlayers;
    private final Game game;
    private final List<Player> players = new ArrayList<>();
    private List<Player> actionPhaseOrderOfPlayers = new ArrayList<>();
    private final int numberOfMovableStudents;
    private final int numberOfClouds;
    private final int numberOfStudentsOnCLoud;
    private final int numberOfStudentInEntrance;
    private final int numberOfTowers;

    public MatchThreePlayers() {
        numberOfPlayers = 3;
        numberOfMovableStudents = 4;
        numberOfClouds = 3;
        numberOfTowers = 6;
        numberOfStudentInEntrance = 9;
        numberOfStudentsOnCLoud = 4;
        game = new Game(numberOfStudentInEntrance, numberOfMovableStudents);

    }


}
