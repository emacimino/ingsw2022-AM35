package it.polimi.ingsw.Model.FactoryMatch;

import it.polimi.ingsw.Model.Exception.ExceptionEndGame;
import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.Wizard.Wizard;
import it.polimi.ingsw.NetworkUtilities.Message.GameStateMessage;
import it.polimi.ingsw.NetworkUtilities.Message.MoveMotherNature;
import it.polimi.ingsw.NetworkUtilities.Message.TeamMessage;
import it.polimi.ingsw.Observer.Observer;

import java.util.*;

public class BasicMatchFourPlayers extends BasicMatch{
    private  Player captainTeamOne;
    private  Player captainTeamTwo;
    private List<Player> teamOne = new ArrayList<>();
    private List<Player> teamTwo = new ArrayList<>();
    private List<Player> captains = new ArrayList<>();
   // private Team teamOne_;
   // private Team teamTwo_;

    public BasicMatchFourPlayers(){
        super.setNumberOfPlayers(4);
        super.setNumberOfMovableStudents(3);
        super.setNumberOfClouds(4);
        super.setNumberOfTowers(8);
        super.setNumberOfStudentInEntrance(7);
        super.setNumberOfStudentsOnCLoud(3);
        Game game = new Game(getNumberOfStudentInEntrance(), getNumberOfMovableStudents());
        super.setGame(game);
    }

    @Override
    public void setGame(List<Player> players) throws ExceptionGame {
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
    }

    @Override
    public void setTeamsOne(Player player1, Player player2) throws ExceptionGame{
        if(teamTwo.contains(player1) || teamTwo.contains(player2))
            throw new ExceptionGame("One player is already in Team Two");
        if(!teamOne.isEmpty())
            throw new ExceptionGame("Team One is already defined");
        captainTeamOne = player1;
        captains.add(captainTeamOne);
        teamOne.add(player1);
        teamOne.add(player2);
        notifyObserver(new TeamMessage(player1.getUsername(),player2.getUsername(), GameStateMessage.TEAM));
    }

    @Override
    public void setTeamsTwo(Player player1, Player player2)throws ExceptionGame{
        if(teamOne.contains(player1) || teamOne.contains(player2))
            throw new ExceptionGame("One player is already in Team One");
        if(!teamTwo.isEmpty())
            throw new ExceptionGame("Team Two is already defined");
        captainTeamTwo = player1;
        captains.add(captainTeamTwo);
        teamTwo.add(player1);
        teamTwo.add(player2);
        notifyObserver(new TeamMessage(player1.getUsername(),player2.getUsername(), GameStateMessage.TEAM));
    }

    @Override
    public List<List<Player>> getTeams() throws ExceptionGame{
        List<List<Player>> teamsList = new ArrayList<>();
        List<Player> copyOne = new ArrayList<>();
        List<Player> copyTwo = new ArrayList<>();
        Collections.copy(copyOne, teamOne);
        teamsList.add(copyOne);
        Collections.copy(copyTwo, teamTwo);
        teamsList.add(copyTwo);
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
            System.out.println(e);
        }finally {
            checkVictory();
        }
        if(player.equals(super.getActionPhaseOrderOfPlayers().get(super.getActionPhaseOrderOfPlayers().size()-1))){
            resetRound();
        }
        notifyObserver(new MoveMotherNature(archipelago));
    }

    @Override
    protected void buildTower(Player captain, Archipelago archipelago) throws ExceptionGame {
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
    public void checkVictory() throws ExceptionGame{
        boolean endOfTheMatch = false;
        List<Wizard> w = getCaptainsWithLeastTowers();
        List<Wizard> winner = new ArrayList<>();

        System.out.println("size of winners "+w.size());
        if (w.size()==1 && w.get(0).getBoard().getTowersInBoard().isEmpty()) {
            endOfTheMatch = true;
            winner.add(w.get(0));
        }else if(w.size() == 1 && ((getGame().getStudentBag().getStudentsInBag().size() == 0) || (getGame().getArchipelagos().size() <= 3))){
            endOfTheMatch = true;
            System.out.println("num towers "+ w.get(0) + " : " + w.get(0).getBoard().getTowersInBoard().size());
            winner.add(w.get(0));
            System.out.println("studentbag empty or assistant card playable empty, one winner");
        }
        else if (w.size()>1 && ((getGame().getStudentBag().getStudentsInBag().size() == 0) || (super.getGame().getArchipelagos().size() <= 3))) {
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
        System.out.println("number of students in bag "+ getGame().getStudentBag().getNumberOfStudents());
        System.out.println(endOfTheMatch);
        if (endOfTheMatch) {
            throw new ExceptionEndGame("Wizard: " + winner + " has won the match\n" +
                    "Please, create a new match if you want to replay");
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
        boolean teamControl = (companion.getBoard().isProfessorPresent(color) || captain.getBoard().isProfessorPresent(color));
        return teamControl;
    }

}
