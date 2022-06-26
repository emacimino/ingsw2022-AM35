package it.polimi.ingsw.Model.FactoryMatch;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.Wizard.Wizard;
import it.polimi.ingsw.NetworkUtilities.CurrentGameMessage;
import it.polimi.ingsw.NetworkUtilities.EndMatchMessage;
import it.polimi.ingsw.NetworkUtilities.GenericMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BasicMatchFourPlayers extends BasicMatch{

    private  Player captainTeamOne;
    private  Player captainTeamTwo;
    private final List<Player> teamOne = new ArrayList<>(); //added final after a warning
    private final List<Player> teamTwo = new ArrayList<>(); //added final after a warning
    private final List<Player> captains = new ArrayList<>(); //added final after a warning

    public BasicMatchFourPlayers(){
        super.setNumberOfPlayers(4);
        super.setNumberOfMovableStudents(3);
        super.setNumberOfClouds(4);
        super.setNumberOfTowers(8);
        super.setNumberOfStudentInEntrance(7);
        super.setNumberOfStudentsOnCLoud(3);
    }

    @Override
    public void setGame(List<Player> players) throws ExceptionGame {
        Game game = new Game(getNumberOfStudentInEntrance(), getNumberOfMovableStudents());
        super.setActualGame(game);
        if(captains.size() != 2)
            throw new ExceptionGame("Please, declare the TWO teams");
        if(players.size() >4)
            throw new ExceptionGame("There are more players than allowed in this match");
        super.setPlayers(players);
        super.getGame().setWizards(captains);
        super.getGame().setTowers(getNumberOfTowers());
        super.getGame().getWizards().add(new Wizard(teamOne.get(1).getUsername(), getNumberOfStudentInEntrance(), getNumberOfMovableStudents()));
        super.getGame().getWizards().add(new Wizard(teamTwo.get(1).getUsername(), getNumberOfStudentInEntrance(), getNumberOfMovableStudents()));
        super.getGame().setRandomlyStudentsInEntrance();
        super.getGame().setArchipelagos();
        super.getGame().setProfessors();
        super.getGame().setClouds(getNumberOfClouds(), getNumberOfStudentsOnCLoud());
        super.getGame().setRandomlyFirstPlayer();
        notifyObserver(new GenericMessage("Order of the players: " + players));
        notifyObserver(new CurrentGameMessage(super.getGame()));
    }

    public void setTeams(List<Player> players) throws ExceptionGame {
        if(teamOne.isEmpty() && teamTwo.isEmpty()) {
            setTeamsOne(players.get(0), players.get(1));
            setTeamsTwo(players.get(2), players.get(3));
            notifyObserver(new GenericMessage("The teams are: \nteam ONE -> " + getTeams().get(0) + "\nteam TWO -> " + getTeams().get(1)));
        }else throw new ExceptionGame("Team One is already defined");
    }
    private void setTeamsOne(Player player1, Player player2) {
        captainTeamOne = player1;
        captains.add(captainTeamOne);
        teamOne.add(player1);
        teamOne.add(player2);
    }
    private void setTeamsTwo(Player player1, Player player2){
        captainTeamTwo = player1;
        captains.add(captainTeamTwo);
        teamTwo.add(player1);
        teamTwo.add(player2);
    }

    @Override
    public List<List<Player>> getTeams() throws ExceptionGame{
        List<List<Player>> teamsList = new ArrayList<>();
        teamsList.add(teamOne);
        teamsList.add(teamTwo);
        return teamsList;
    }
    private Player getCaptainOfTheTeam(Collection<Player> team) throws ExceptionGame{
        if(team.equals(teamOne))
            return captainTeamOne;
        if (team.equals(teamTwo))
            return captainTeamTwo;
        throw new ExceptionGame("This team is not in this match");
    }
    private List<Player> getTeamOfPlayer(Player player) throws ExceptionGame{
        if(teamOne.contains(player))
            return teamOne;
        if( teamTwo.contains(player))
            return  teamTwo;
        throw new ExceptionGame("Player has no teams");
    }

    @Override
    public Player getCaptainTeamOfPlayer(Player player) throws ExceptionGame{
        return getCaptainOfTheTeam(getTeamOfPlayer(player));
    }

    private List<Player> getCaptains(){
        return captains;
    }

    @Override
    public void moveMotherNature(Player player, Archipelago archipelago) throws ExceptionGame{
        super.getGame().placeMotherNature(player, archipelago);
        try {
            buildTower(getCaptainTeamOfPlayer(player), archipelago);
            lookUpArchipelago(archipelago);
        }catch (ExceptionGame e){
            e.printStackTrace();
        }finally {
            checkVictory(player);
        }
        //notifyObserver(new CurrentGameMessage(super.getGame()));
    }

    @Override
    public void buildTower(Player captain, Archipelago archipelago) throws ExceptionGame {
        boolean isMostInfluence = true;
        Wizard wizard = getGame().getWizardFromPlayer(captain);
        Player rivalCaptain = getRivals(captain).get(0);
        if (getWizardInfluenceInArchipelago(captain, archipelago) <= getWizardInfluenceInArchipelago(rivalCaptain, archipelago)) {
            isMostInfluence = false;
        }
        if(isMostInfluence) {
            getGame().buildTower(wizard, archipelago);
        }

    }

    @Override
    public void checkVictory(Player player) throws ExceptionGame{
        boolean endOfTheMatch = false;
        List<Wizard> w = getCaptainsWithLeastTowers();
        List<Wizard> winner = new ArrayList<>();
        Player lastPlayer = getActionPhaseOrderOfPlayers().get(getActionPhaseOrderOfPlayers().size() - 1);

        if (w.size()==1 && w.get(0).getBoard().getTowersInBoard().isEmpty()) {
            endOfTheMatch = true;
            winner.add(w.get(0));
        }else if(w.size() == 1 && (
                ((getGame().getStudentBag().getStudentsInBag().size() == 0 || getGame().getWizardFromPlayer(player).getAssistantsDeck().getPlayableAssistants().isEmpty()) && player.equals(getActionPhaseOrderOfPlayers().get(getActionPhaseOrderOfPlayers().size() - 1)))
                || (getGame().getArchipelagos().size() <= 3))){
            endOfTheMatch = true;
            winner.add(w.get(0));
        }
        else if (w.size()>1 && (
                ((getGame().getStudentBag().getStudentsInBag().size() == 0 || getGame().getWizardFromPlayer(player).getAssistantsDeck().getPlayableAssistants().isEmpty()) &&  player.equals(getActionPhaseOrderOfPlayers().get(getActionPhaseOrderOfPlayers().size() - 1)))
                || (super.getGame().getArchipelagos().size() <= 3))) {
            endOfTheMatch = true;
            List<Player> teamA = getTeamOfPlayer(getPlayerFromWizard(w.get(0)));
            List<Player> teamB = getTeamOfPlayer(getPlayerFromWizard(w.get(1)));
            int numProfTeamA = getGame().getWizardFromPlayer(teamA.get(0)).getBoard().getProfessorInTable().size() +
                    getGame().getWizardFromPlayer(teamA.get(1)).getBoard().getProfessorInTable().size();
            int numProfTeamB = getGame().getWizardFromPlayer(teamB.get(0)).getBoard().getProfessorInTable().size() +
                    getGame().getWizardFromPlayer(teamB.get(1)).getBoard().getProfessorInTable().size();
            if (numProfTeamA > numProfTeamB) {
                winner.add(getGame().getWizardFromPlayer(teamA.get(0)));
                winner.add(getGame().getWizardFromPlayer(teamA.get(1)));
            } else if (numProfTeamA < numProfTeamB) {
                winner.add(getGame().getWizardFromPlayer(teamB.get(0)));
                winner.add(getGame().getWizardFromPlayer(teamB.get(1)));
            } else
                winner.addAll(w);
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

    private List<Wizard> getCaptainsWithLeastTowers() throws ExceptionGame{
        Wizard wizardCaptainOne = super.getGame().getWizardFromPlayer(captainTeamOne);
        Wizard wizardCaptainTwo = super.getGame().getWizardFromPlayer(captainTeamTwo);
        List<Wizard> wizardsWithLeastTowers = new ArrayList<>();
        int towersCaptainOne = wizardCaptainOne.getBoard().getTowersInBoard().size();
        int towersCaptainTwo = wizardCaptainTwo.getBoard().getTowersInBoard().size();
        if(towersCaptainOne < towersCaptainTwo){
            wizardsWithLeastTowers.add(wizardCaptainOne);
        }else if(towersCaptainOne > towersCaptainTwo){
            wizardsWithLeastTowers.add(wizardCaptainTwo);
        }else{
            wizardsWithLeastTowers.add(wizardCaptainOne);
            wizardsWithLeastTowers.add(wizardCaptainTwo);
        }
        return wizardsWithLeastTowers;

    }

    @Override
    public int getWizardInfluenceInArchipelago(Player p, Archipelago archipelago) throws ExceptionGame{
        Wizard wizard = getGame().getWizardFromPlayer(p);
        int index = getTeamOfPlayer(p).indexOf(p);
        Wizard companion = getGame().getWizardFromPlayer(getTeamOfPlayer(p).get(1 - index));
        int influenceWizard = -1;
        int influenceCompanion = -1;
        if(companion!=null) {
            influenceWizard = getGame().getWizardInfluenceInArchipelago(wizard, archipelago);
            influenceCompanion = getGame().getWizardInfluenceInArchipelago(companion, archipelago);
        }
        if(influenceCompanion <0 || influenceWizard <0)
            throw new ExceptionGame("Error: The companion is null");
        return (influenceWizard + influenceCompanion);
    }

    @Override
    public List<Player> getRivals(Player captain) throws ExceptionGame{
        List<Player> rivals = new ArrayList<>();
        int index = getTeamOfPlayer(captain).indexOf(captain);
        Player companion = getTeamOfPlayer(captain).get(1 - index);
        for (Player p : getPlayers()){
            if (!captain.equals(p) && !companion.equals(p))
                rivals.add(p);
        }
        List<Player> rivalCaptain = new ArrayList<>();
        rivalCaptain.add(getCaptainOfTheTeam(rivals));
        return rivalCaptain;
    }

    @Override
    public boolean playerControlProfessor(Player player, Color color) throws ExceptionGame{
        Wizard captain = getGame().getWizardFromPlayer(getCaptainTeamOfPlayer(player));
        Wizard companion = getGame().getWizardFromPlayer(getTeamOfPlayer(player).get(1));
        return (companion.getBoard().isProfessorPresent(color) || captain.getBoard().isProfessorPresent(color));
    }


}
