package it.polimi.ingsw.Model.FactoryMatch;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Cloud;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.Model.Wizard.Wizard;

import java.util.List;

public interface MatchInterface {

    public void setGame(List<Player> players) throws ExceptionGame;
    public Player getPlayerFromWizard(Wizard wizard) throws ExceptionGame;
    public void playAssistantsCard(Player player, AssistantsCards assistantsCards) throws ExceptionGame;
    public void moveStudentOnBoard(Player player, Student student) throws ExceptionGame;
    public void moveStudentOnArchipelago(Player player, Student student, Archipelago archipelago) throws ExceptionGame;
    public void moveMotherNature(Player player, Archipelago archipelago) throws ExceptionGame;
    public void chooseCloud(Player player, Cloud cloud) throws ExceptionGame;
    public int getNumberOfPlayers();
    public Game getGame();
    public List<Player> getPlayers();
    public List<Player> getActionPhaseOrderOfPlayers();
    public int getNumberOfMovableStudents();
    public int getNumberOfClouds();
    public int getNumberOfStudentsOnCLoud();
    public int getNumberOfStudentInEntrance();
    public int getNumberOfTowers();
    public int getPositionOfMotherNature();
    public void setTeamsOne(Player player1, Player player2) throws ExceptionGame;
    public void setTeamsTwo(Player player1, Player player2) throws ExceptionGame;
    public List<List<Player>> getTeams() throws ExceptionGame;
}
