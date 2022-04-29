package it.polimi.ingsw.Model.ExpertMatch;


import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.FactoryMatch.Match;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Cloud;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.Model.Wizard.Wizard;

import java.util.ArrayList;
import java.util.List;

/**
 * this class extends the MatchDecorator to implements the ExpertMode
 */
public class ExpertMatch extends MatchDecorator {
    private DeckCharacterCard deckCharacterCard;
    ArrayList<CharacterCard> charactersForThisGame = new ArrayList<>();
    private final Match match;

    /**
     * Constructor of ExpertMatch
     *
     * @param match is the match that has to be modified to play in ExpertMode
     */
    public ExpertMatch(Match match) {
        this.match=match;
        deckCharacterCard = new DeckCharacterCard(match);
    }

    public void setGame(List<Player> players) throws ExceptionGame {
        match.setGame(players);
        this.setFirstCoin();
        charactersForThisGame = deckCharacterCard.drawCharacterCard();
    }

    @Override
    public Player getPlayerFromWizard(Wizard wizard) throws ExceptionGame {
        return match.getPlayerFromWizard(wizard);
    }

    @Override
    public void playAssistantsCard(Player player, AssistantsCards assistantsCards) throws ExceptionGame {
      match.playAssistantsCard(player, assistantsCards);
    }

    /**
     * The override add a check to the number of student that are placed on the board when a student is moved
     *
     * @param player  is the player which moves the student
     * @param student is the student
     */
    @Override
    public void moveStudentOnBoard(Player player, Student student) throws ExceptionGame {
        match.moveStudentOnBoard(player, student);
        if (match.getGame().getWizardFromPlayer(player).getBoard().getStudentsFromTable(student.getColor()).size() == 3 ||
                match.getGame().getWizardFromPlayer(player).getBoard().getStudentsFromTable(student.getColor()).size() == 6 ||
                match.getGame().getWizardFromPlayer(player).getBoard().getStudentsFromTable(student.getColor()).size() == 9) {
            match.getGame().getWizardFromPlayer(player).addACoin();
        }
    }

    @Override
    public void moveStudentOnArchipelago(Player player, Student student, Archipelago archipelago) throws ExceptionGame {
        match.moveStudentOnArchipelago(player,student,archipelago);
    }

    @Override
    public void moveMotherNature(Player player, Archipelago archipelago) throws ExceptionGame {
        match.moveMotherNature(player,archipelago);
    }

    @Override
    public void chooseCloud(Player player, Cloud cloud) throws ExceptionGame {
        match.chooseCloud(player,cloud);
    }

    @Override
    public int getNumberOfPlayers() {
        return match.getNumberOfPlayers();
    }

    @Override
    public Game getGame() {
        return getGame();
    }

    @Override
    public List<Player> getPlayers() {
        return match.getPlayers();
    }

    @Override
    public List<Player> getActionPhaseOrderOfPlayers() {
        return getActionPhaseOrderOfPlayers();
    }

    @Override
    public int getNumberOfMovableStudents() {
        return getNumberOfMovableStudents();
    }

    @Override
    public int getNumberOfClouds() {
        return getNumberOfClouds();
    }

    @Override
    public int getNumberOfStudentsOnCLoud() {
        return getNumberOfStudentsOnCLoud();
    }

    @Override
    public int getNumberOfStudentInEntrance() {
        return getNumberOfStudentInEntrance();
    }

    @Override
    public int getNumberOfTowers() {
        return getNumberOfTowers();
    }

    @Override
    public int getPositionOfMotherNature() {
        return getPositionOfMotherNature();
    }

    @Override
    public void setTeamsOne(Player player1, Player player2) throws ExceptionGame {
        match.setTeamsOne(player1,player2);
    }

    @Override
    public void setTeamsTwo(Player player1, Player player2) throws ExceptionGame {
        match.setTeamsTwo(player1,player2);
    }

    @Override
    public List<List<Player>> getTeams() throws ExceptionGame {
        return getTeams();
    }

    /**
     * Add a coin to every wizard when we set the Match to Expert Mode
     */
    public void setFirstCoin() {
        for (Wizard wizard : match.getGame().getWizards())
            wizard.addACoin();
    }




}


