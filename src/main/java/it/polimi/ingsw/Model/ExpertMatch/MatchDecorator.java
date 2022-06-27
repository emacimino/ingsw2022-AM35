package it.polimi.ingsw.Model.ExpertMatch;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Cloud;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.Model.Wizard.Wizard;

import java.util.ArrayList;
import java.util.List;

/**
 * Match decorator used in the ExpertMatch to extend the BasicMatch
 */
public abstract class MatchDecorator extends BasicMatch {
    protected BasicMatch basicMatch;

    /**
     * Constructor of the match
     * @param basicMatch match that ha to be set in expert mode
     */
    public MatchDecorator(BasicMatch basicMatch){
        this.basicMatch = basicMatch;
    }

    /**
     * Setter for the game
     * @param players is the players of the match
     * @throws ExceptionGame if the game could not be created
     */
    @Override
    public void setGame(List<Player> players) throws ExceptionGame{
        basicMatch.setGame(players);
    }

    /**
     * Getter for wizard
     * @param wizard is the wizard
     * @return player associated to the wizard
     * @throws ExceptionGame if the player does not exist
     */
    @Override
    public Player getPlayerFromWizard(Wizard wizard) throws ExceptionGame{
        return basicMatch.getPlayerFromWizard(wizard);
    }

    /**
     * Play an assistant card
     * @param player          is the current player
     * @param assistantsCards is the round's assistant's card of the player
     * @throws ExceptionGame if the card can't be played
     */
    @Override
    public void playAssistantsCard(Player player, AssistantsCards assistantsCards) throws ExceptionGame{
        basicMatch.playAssistantsCard(player,assistantsCards);
    }

    /**
     * Move a student to an archipelago
     * @param player      is the player which moves the student
     * @param student     is the student
     * @param archipelago is the archipelago
     * @throws ExceptionGame if the student can't be moved
     */
    @Override
    public void moveStudentOnArchipelago(Player player, Student student, Archipelago archipelago) throws ExceptionGame{
        basicMatch.moveStudentOnArchipelago(player,student,archipelago);
    }

    /**
     * Choose a cloud
     * @param player is the player
     * @param cloud  is the cloud
     * @throws ExceptionGame if the cloud can't be picked
     */
    @Override
    public void chooseCloud(Player player, Cloud cloud) throws ExceptionGame{
        basicMatch.chooseCloud(player,cloud);
    }

    /**
     * Getter for number of players of the game
     * @return number of players
     */
    @Override
    public int getNumberOfPlayers(){
        return basicMatch.getNumberOfPlayers();
    }

    /**
     * Getter for players
     * @return players
     */
    @Override
    public List<Player> getPlayers(){
        return basicMatch.getPlayers();
    }

    /**
     * Getter for the Game
     * @return game
     */
    @Override
    public Game getGame(){
        return basicMatch.getGame();
    }

    /**
     * Getter for the action phase order
     * @return order of players in turn
     */
    @Override
    public List<Player> getActionPhaseOrderOfPlayers(){
        return basicMatch.getActionPhaseOrderOfPlayers();
    }

    /**
     * Getter for number of student that can be moved
     * @return number of student that can be moved
     */
    @Override
    public int getNumberOfMovableStudents(){
        return basicMatch.getNumberOfMovableStudents();
    }
    /**
     * Getter for number of clouds
     * @return number of clouds
     */
    @Override
    public int getNumberOfClouds(){
        return basicMatch.getNumberOfClouds();
    }
    /**
     * Getter for number of student on cloud
     * @return number of student on cloud
     */
    @Override
    public int getNumberOfStudentsOnCLoud(){
        return basicMatch.getNumberOfStudentsOnCLoud();
    }
    /**
     * Getter for number of student on entrance
     * @return number of student on entrance
     */
    @Override
    public int getNumberOfStudentInEntrance(){
        return basicMatch.getNumberOfStudentInEntrance();
    }
    /**
     * Getter for number of towers
     * @return number of towers
     */
    @Override
    public int getNumberOfTowers(){
        return basicMatch.getNumberOfTowers();
    }

    /**
     * Getter for position of mother nature
     * @return position of mother nature
     */
    @Override
    public int getPositionOfMotherNature(){
        return basicMatch.getPositionOfMotherNature();
    }

    /**
     * Setter for teams
     * @param players a list of players
     * @throws ExceptionGame if the teams could not be created
     */
    @Override
    public void setTeams(List<Player> players) throws ExceptionGame{
        basicMatch.setTeams(players);
    }

    /**
     * Getter for teams
     * @return a list of players
     * @throws ExceptionGame  if the teams are not created
     */
    @Override
    public List<List<Player>> getTeams() throws ExceptionGame{
        return basicMatch.getTeams();
    }
    /**
     * Getter for rivals
     * @return a list of players
     * @throws ExceptionGame if the teams are not created
     */
    @Override
    public List<Player> getRivals(Player captain) throws ExceptionGame{
        return basicMatch.getRivals(captain);
    }
    /**
     * Getter for captain
     * @return a player which is captain
     * @throws ExceptionGame if the teams are not created
     */
    @Override
    public Player getCaptainTeamOfPlayer(Player player) throws ExceptionGame{
        return basicMatch.getCaptainTeamOfPlayer(player);
    }


}