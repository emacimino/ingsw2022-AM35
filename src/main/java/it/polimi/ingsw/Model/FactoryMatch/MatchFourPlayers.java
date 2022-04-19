package it.polimi.ingsw.Model.FactoryMatch;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.Model.Wizard.Wizard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MatchFourPlayers extends Match{
    private final int numberOfPlayers;
    private final Game game;
    private final List<Player> players = new ArrayList<>();
    private List<Player> actionPhaseOrderOfPlayers = new ArrayList<>();
    private final int numberOfMovableStudents;
    private final int numberOfClouds;
    private final int numberOfStudentsOnCLoud;
    private final int numberOfStudentInEntrance;
    private final int numberOfTowers;
    private  Player captainTeamOne;
    private  Player captainTeamTwo;
    private List<Player> teamOne = new ArrayList<>();
    private List<Player> teamTwo = new ArrayList<>();

    public MatchFourPlayers(){
        numberOfPlayers = 4;
        numberOfMovableStudents = 3;
        numberOfClouds = 4;
        numberOfTowers = 8;
        numberOfStudentInEntrance = 7;
        numberOfStudentsOnCLoud = 3;
        game = new Game(numberOfStudentInEntrance, numberOfMovableStudents);
    }

    @Override
    public void setGame(List<Player> players) throws ExceptionGame {
        List<Player> captains = new ArrayList<>();
        captains.add(captainTeamOne);
        captains.add(captainTeamTwo);
        game.setWizards(captains);
        game.setTowers(numberOfTowers);
        game.getWizards().add(new Wizard(teamOne.get(1).getUsername(), numberOfStudentInEntrance, numberOfMovableStudents));
        game.getWizards().add(new Wizard(teamTwo.get(1).getUsername(), numberOfStudentInEntrance, numberOfMovableStudents));
        game.setRandomlyStudentsInEntrance();
        game.setArchipelagos();
        game.setProfessors();
        game.setClouds(numberOfClouds, numberOfStudentsOnCLoud);
        game.setRandomlyFirstPlayer();
    }

    public void setTeamsOne(Player player1, Player player2){
        captainTeamOne = player1;
        teamOne.add(player1);
        teamOne.add(player2);
    }
    public void setTeamsTwo(Player player1, Player player2){
        captainTeamTwo = player1;
        teamTwo.add(player1);
        teamTwo.add(player2);
    }
    public Player getCaptainOfTheTeam(Collection<Player> team) throws ExceptionGame{
        if(team.equals(teamOne))
            return captainTeamOne;
        if (team.equals(teamTwo))
            return captainTeamTwo;
        throw new ExceptionGame("This team is not in this match");
    }
    public Collection<Player> getTeamOfPlayer(Player player) throws ExceptionGame{
        if(teamOne.contains(player))
            return teamOne;
        if( teamTwo.contains(player))
            return  teamTwo;
        throw new ExceptionGame("Player has no teams");
    }
    private Player getCaptainTeamOfPlayer(Player player) throws ExceptionGame{
        return getCaptainOfTheTeam(getTeamOfPlayer(player));
    }






}
