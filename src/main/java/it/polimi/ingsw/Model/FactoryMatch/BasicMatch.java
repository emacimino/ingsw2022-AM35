package it.polimi.ingsw.Model.FactoryMatch;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
//import it.polimi.ingsw.Model.Exception.ExceptionStudentBagEmpty;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Cloud;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.Model.Wizard.Wizard;
import it.polimi.ingsw.NetworkUtilities.CurrentGameMessage;
import it.polimi.ingsw.NetworkUtilities.EndMatchMessage;
import it.polimi.ingsw.NetworkUtilities.GenericMessage;
import it.polimi.ingsw.Observer.Observable;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * Class containing the base settings for a match
 */
public class BasicMatch extends Observable implements Serializable {

    @Serial
    private final static long serialVersionUID = 9167805616236002194L;
    private int numberOfPlayers;
    private Game game;
    private List<Player> players = new ArrayList<>();
    private List<Player> actionPhaseOrderOfPlayers = new ArrayList<>();
    private int numberOfMovableStudents;
    private int numberOfClouds;
    private int numberOfStudentsOnCLoud;
    private int numberOfStudentInEntrance;
    private int numberOfTowers;

    /**
     * The constructor of the Class Match which implements a match for two players
     */
    public BasicMatch() {
        numberOfPlayers = 2;
        numberOfMovableStudents = 3;
        numberOfClouds = 2;
        numberOfTowers = 8;
        numberOfStudentInEntrance = 7;
        numberOfStudentsOnCLoud = 3;
        game = new Game(numberOfStudentInEntrance, numberOfMovableStudents);
    }

    /**
     * This method sets the Game for a two players' match
     * @param players is the players of the match
     * @throws ExceptionGame is thrown if this method is called during an execution of a match
     */
    public void setGame(List<Player> players) throws ExceptionGame {
        if(players.size()>numberOfPlayers)
            throw new ExceptionGame("There are more player than allowed in this match");
        this.players.addAll(players);
        game.setWizards(players);
        game.setArchipelagos();
        game.setTowers(numberOfTowers);
        game.setRandomlyStudentsInEntrance();
        game.setProfessors();
        game.setClouds(numberOfClouds, numberOfStudentsOnCLoud);
        game.setRandomlyFirstPlayer();
        notifyObserver(new CurrentGameMessage(game));
      //  notifyObserver(new GenericMessage("Order of the players: \n" + players));

    }

    /**
     * This method returns the player which is related to the wizard passed as a parameter
     * the method throws an exception if there is no player matched with the wizard
     *
     * @param wizard is the wizard
     * @return the player related to the wizard
     * @throws ExceptionGame is thrown if there is no relation between the wizard passed and the players of the match
     */
    public Player getPlayerFromWizard(Wizard wizard) throws ExceptionGame {
        for (Player p : players) {
            if (p.getUsername().equals(wizard.getUsername()))
                return p;
        }
        throw new ExceptionGame("Player not founded");

    }

    /**
     * With this method the player can play his round's assistant's card, then the player is placed in the list of players of the action phase
     * where his position on the list represents his order position in the action phase
     *
     * @param player          is the current player
     * @param assistantsCard is the round's assistant's card of the player
     * @throws ExceptionGame is thrown whenever there is an issue with the assistant's card played or the current player
     */
    public void playAssistantsCard(Player player, AssistantsCards assistantsCard) throws ExceptionGame {
        if (actionPhaseOrderOfPlayers.contains(player))
            throw new ExceptionGame("Player already played an assistant's Card");
        Wizard wizard = game.getWizardFromPlayer(player);
        wizard.playAssistantsCard(assistantsCard, game.getAssistantsCardsPlayedInRound());
        setPlayerInActionPhase(player, assistantsCard);
        notifyObserver(new CurrentGameMessage(game));
     //   notifyObserver(new GenericMessage("Player " + player +" has played "+ assistantsCard));
    }

    /**
     * This method sets the player passed as a parameter in the correct position of the list of players of the action phase

     * @param player          is the current player
     * @param assistantsCards is the round's assistant's card played by the player
     * @throws ExceptionGame is thrown if the player is an irregular player
     */
    protected void setPlayerInActionPhase(Player player, AssistantsCards assistantsCards) throws ExceptionGame {
        Collection<AssistantsCards> cardsPlayedInRound = game.getAssistantsCardsPlayedInRound();
        int position = 0;
        for (AssistantsCards a : cardsPlayedInRound) {
            if (assistantsCards.getValue() > a.getValue())
                position++;
            else if (assistantsCards.getValue() == a.getValue()) {
                for (Wizard w : game.getWizards()) {
                    if (w.getRoundAssistantsCard().getValue() == assistantsCards.getValue()) {
                        int i = actionPhaseOrderOfPlayers.indexOf(getPlayerFromWizard(w));
                        position = i + 1;
                    }

                }
            }
        }
        actionPhaseOrderOfPlayers.add(position, player);
        cardsPlayedInRound.add(assistantsCards);
    }

    /**
     * This method checks if the professor of the color passed need to be moved
     *
     * @param c is the color
     */
    public void lookUpProfessor(Color c){
        try {
            game.placeProfessor(c);
        }catch (ExceptionGame e){
            e.printStackTrace();
        }
    }

    /**
     * This method moves the player's student passed as parameter to the board of the player's wizard
     *
     * @param player  is the player which moves the student
     * @param student is the student
     * @throws ExceptionGame is thrown if there is an illegal situation
     */
    public void moveStudentOnBoard(Player player, Student student) throws ExceptionGame {
        game.placeStudentOnTable(player, student);
        lookUpProfessor(student.getColor());
        notifyObserver(new CurrentGameMessage(game));
    }

    /**
     * This method moves the player's student passed as parameter to the archipelago passed as parameter
     * @param player      is the player which moves the student
     * @param student     is the student
     * @param archipelago is the archipelago
     * @throws ExceptionGame is thrown if there is an illegal situation
     */
    public void moveStudentOnArchipelago(Player player, Student student, Archipelago archipelago) throws ExceptionGame {
        game.placeStudentOnArchipelago(player, student, archipelago);
        notifyObserver(new CurrentGameMessage(game));
    }

    /**
     * This method places Mother Nature, if possible, in the archipelago passed as parameter, followed by the update of the state of the archipelagos of the Game.
     * The method checks if there is a situation which indicate the end of the match
     *
     * @param player      is the player that move Mother Nature
     * @param archipelago is the archipelago where the player wants to place Mother Nature
     * @throws ExceptionGame is thrown if Mother Nature can't be placed in the archipelago passed
     */
    public void moveMotherNature(Player player, Archipelago archipelago) throws ExceptionGame {
        game.placeMotherNature(player, archipelago);
        try {
            this.buildTower(player, archipelago);
            lookUpArchipelago(archipelago);
        } catch (ExceptionGame e) {
            e.printStackTrace();
        } finally {
            checkVictory(player);

        }
        notifyObserver(new CurrentGameMessage(game));
    }

    /**
     * This method resets the list of assistant's cards played in the round
     */
    public void resetRound(){    //Tested in local
        actionPhaseOrderOfPlayers.clear();
        game.getAssistantsCardsPlayedInRound().clear();
        try {
            game.setRandomStudentsOnCloud();
        } catch (ExceptionGame e) {
            e.printStackTrace();
        }
    }

    /**
     * This method builds the tower, if the condition permitted, of the player on the archipelago, both passed as parameter.
     *
     * @param player      is the player
     * @param archipelago is the archipelago
     * @throws ExceptionGame is thrown if the tower can't be built
     */
    public void buildTower(Player player, Archipelago archipelago) throws ExceptionGame {
        boolean isMostInfluence = true;
        for(Player p : getRivals(player)){
            if(getWizardInfluenceInArchipelago(p, archipelago) >= getWizardInfluenceInArchipelago(player, archipelago))
                isMostInfluence = false;
        }
        if(isMostInfluence) {
            game.buildTower(getGame().getWizardFromPlayer(player), archipelago);
        }

    }

    /**
     * This method, following the placement of Mother Nature on the archipelago passed as parameter, updates the state of the archipelagos of the game
     *
     * @param archipelago is the archipelago where Mother Nature was placed
     */
    public void lookUpArchipelago(Archipelago archipelago) {
        game.takeCareOfTheMerge(archipelago);
    }

    /**
     * This method move the student from the cloud passed as parameter to the entrance of the board of the wizard corresponding the player passed as parameter
     *
     * @param player is the player
     * @param cloud  is the cloud
     * @throws ExceptionGame is thrown if the move is not allowed
     */
    public void chooseCloud(Player player, Cloud cloud) throws ExceptionGame {
        if(game.getStudentBag().getStudentsInBag().isEmpty())
            throw new ExceptionGame("The studentBag is empty, it is not possible to pick a cloud");
        game.moveStudentFromCloudToBoard(player, cloud);
        notifyObserver(new CurrentGameMessage(game));
        System.out.println("in chooseCloud BAsicMAtch " + actionPhaseOrderOfPlayers);
        if (player.equals(actionPhaseOrderOfPlayers.get(actionPhaseOrderOfPlayers.size() - 1))) {
            resetRound();
        }
    }

    /**
     * This method checks if an end-of-game condition occurs and the resulting winner
     */
    public void checkVictory(Player player) throws ExceptionGame{
        boolean endOfTheMatch = false;
        List<Wizard> w = getGame().getWizardsWithLeastTowers();
        List<Wizard>  winner = new ArrayList<>();
        Player lastPlayer = actionPhaseOrderOfPlayers.get(actionPhaseOrderOfPlayers.size() - 1);

        if (w.size()==1 && w.get(0).getBoard().getTowersInBoard().isEmpty()) {
            endOfTheMatch = true;
            winner.add(w.get(0));
        }else if(w.size() == 1 && (
                ((getGame().getStudentBag().getStudentsInBag().size() == 0 || getGame().getWizardFromPlayer(player).getAssistantsDeck().getPlayableAssistants().isEmpty()) && player.equals(lastPlayer))
                || (getGame().getArchipelagos().size() <= 3))){
            endOfTheMatch = true;
            winner.add(w.get(0));
        }else if (w.size() >1 && (
                ((getGame().getStudentBag().getNumberOfStudents() == 0 || getGame().getWizardFromPlayer(player).getAssistantsDeck().getPlayableAssistants().isEmpty()) && player.equals(lastPlayer))
                || (getGame().getArchipelagos().size() <= 3))) {
            endOfTheMatch = true;
            w.sort((w1, w2) -> w2.getBoard().getProfessorInTable().size() - w1.getBoard().getProfessorInTable().size());
            if(w.get(0).getBoard().getProfessorInTable().size() > w.get(1).getBoard().getProfessorInTable().size())
                winner.add(w.get(0));
            else{
                int numProf = w.get(0).getBoard().getProfessorInTable().size();
                for(Wizard wizard : w){
                    if(wizard.getBoard().getProfessorInTable().size() == numProf)
                        winner.add(wizard);
                }
            }

        }
        if (endOfTheMatch) {
            List<Player> winnerPlayers = winner.stream().map(wiz -> {
                try {
                    return getPlayerFromWizard(wiz);
                } catch (ExceptionGame e) {
                    e.printStackTrace();
                }
                return null;
            }).toList();
            notifyObserver(new EndMatchMessage(winnerPlayers));
        }

    }

    /**
     * This method returns the number of players in the game
     *
     * @return the number of players in the game
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * This method returns the game
     *
     * @return the game
     */
    public Game getGame() {
        return game;
    }

    /**
     * This method returns the list of players
     *
     * @return the list of players
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * This method returns the list of players in order of game round
     *
     * @return the list of players in order of game round
     */
    public List<Player> getActionPhaseOrderOfPlayers() {
        return actionPhaseOrderOfPlayers;
    }

    /**
     * This method returns the number of movable students from the entrance of the board of the wizards
     *
     * @return the number of movable students from the entrance of the board of the wizards
     */
    public int getNumberOfMovableStudents() {
        return numberOfMovableStudents;
    }

    /**
     * This method returns the number of clouds in the game
     *
     * @return the number of clouds in the game
     */
    public int getNumberOfClouds() {
        return numberOfClouds;
    }

    /**
     * This method returns the number of students allowed on the clouds
     *
     * @return the number of students allowed on the clouds
     */
    public int getNumberOfStudentsOnCLoud() {
        return numberOfStudentsOnCLoud;
    }

    /**
     * This method returns the number of students allowed in the entrance of the board of the wizards
     *
     * @return the number of students allowed in the entrance of the board of the wizards
     */
    public int getNumberOfStudentInEntrance() {
        return numberOfStudentInEntrance;
    }

    /**
     * This method returns the number of towers of the game
     *
     * @return the number of tower
     */
    public int getNumberOfTowers() {
        return numberOfTowers;
    }

    /**
     * This method returns the position of Mother Nature
     *
     * @return the position of Mother Nature
     */
    public int getPositionOfMotherNature() {
        return game.getMotherNature().getPosition();
    }

    /**
     * Method used to set teams
     * @param players a list of players
     * @throws ExceptionGame If the match does not have teams
     */
    public void setTeams(List<Player> players) throws ExceptionGame{
        throw new ExceptionGame("This match does not have teams");
    }

    /**
     * Get method for teams
     * @return
     * @throws ExceptionGame if the match does not have teams
     */
    public List<List<Player>> getTeams() throws ExceptionGame{
        throw new ExceptionGame("This match does not have teams");
    }

    /**
     * Method used to set number of players
     * @param numberOfPlayers number of players playing
     */
    //protected setter
    protected void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     * Method used to set the game
     * @param game current game
     */
    protected void setActualGame(Game game) {
        this.game = game;
    }

    /**
     * Set method for players
     * @param players a list of players playing
     */
    protected void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     *
     * @param actionPhaseOrderOfPlayers
     */
    protected void setActionPhaseOrderOfPlayers(List<Player> actionPhaseOrderOfPlayers) {
        this.actionPhaseOrderOfPlayers = actionPhaseOrderOfPlayers;
    }

    /**
     * This method sets the number of movable students
     * @param numberOfMovableStudents int representing the number of movable students
     */
    protected void setNumberOfMovableStudents(int numberOfMovableStudents) {
        this.numberOfMovableStudents = numberOfMovableStudents;
    }

    /**
     * Method that sets the number of clouds
     * @param numberOfClouds number of clouds
     */
    protected void setNumberOfClouds(int numberOfClouds) {
        this.numberOfClouds = numberOfClouds;
    }

    /**
     * Method that sets the number of students on clouds
     * @param numberOfStudentsOnCLoud number of students on clouds
     */
    protected void setNumberOfStudentsOnCLoud(int numberOfStudentsOnCLoud) {
        this.numberOfStudentsOnCLoud = numberOfStudentsOnCLoud;
    }

    /**
     * Method that sets the number of students in entrance
     * @param numberOfStudentInEntrance number of students in entrance
     */
    protected void setNumberOfStudentInEntrance(int numberOfStudentInEntrance) {
        this.numberOfStudentInEntrance = numberOfStudentInEntrance;
    }

    /**
     * Method that sets the number of towers
     * @param numberOfTowers number of towers
     */
    protected void setNumberOfTowers(int numberOfTowers) {
        this.numberOfTowers = numberOfTowers;
    }

    /**
     * Method that returns the influence of the given wizard in the archipelago
     * @param p player used for influence calculation
     * @param archipelago target archipelago
     * @return an int representing the influence
     * @throws ExceptionGame is thrown where there is no association between the player and the wizards in the game
     */
    public int getWizardInfluenceInArchipelago(Player p, Archipelago archipelago) throws ExceptionGame{
        Wizard w = game.getWizardFromPlayer(p);
        return game.getWizardInfluenceInArchipelago(w, archipelago);
    }

    /**
     * Returns the rival team
     * @param player player you want to know the rival of
     * @return a list of players in the opposing team
     * @throws ExceptionGame
     */
    public List<Player> getRivals(Player player) throws ExceptionGame{
        List<Player> rivals = new ArrayList<>();
        for (Player p : getPlayers()){
            if (!player.equals(p))
                rivals.add(p);
        }
        return rivals;
    }

    /**
     * Returns true if a given player controls the professor of the given color
     * @param player player
     * @param color color of the professor
     * @return a boolean
     * @throws ExceptionGame is thrown where there is no association between the player and the wizards in the game
     */
    public boolean playerControlProfessor(Player player, Color color) throws ExceptionGame{
        Wizard wizard = getGame().getWizardFromPlayer(player);
        return wizard.getBoard().isProfessorPresent(color);
    }

    /**
     * This method returns the captain of the team
     * @param player player you want to know the captain of
     * @return a player that is captain
     * @throws ExceptionGame if a method call throws an exception
     */
    public Player getCaptainTeamOfPlayer(Player player) throws ExceptionGame{
        System.out.println("This match does not have teams");
        return player;
    }

    /**
     * toString() method for this class
     * @return
     */
    @Override
    public String toString() {
        return "BasicMatch{" +
                "numberOfPlayers=" + numberOfPlayers +
                ", game=" + game +
                ", players=" + players +
                ", actionPhaseOrderOfPlayers=" + actionPhaseOrderOfPlayers +
                ", numberOfMovableStudents=" + numberOfMovableStudents +
                ", numberOfClouds=" + numberOfClouds +
                ", numberOfStudentsOnCLoud=" + numberOfStudentsOnCLoud +
                ", numberOfStudentInEntrance=" + numberOfStudentInEntrance +
                ", numberOfTowers=" + numberOfTowers +
                '}';
    }
}




