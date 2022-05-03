package it.polimi.ingsw.Model.ExpertMatch.CharacterCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.FactoryMatch.BasicMatch;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Island;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.Wizard;

import java.util.ArrayList;
import java.util.List;

public abstract class CharacterCard {
    protected int cost;
    private String name;
    private Game game;
    private Wizard activeWizard;
    private Wizard passiveWizard;
    private Color colorEffected;
    private Island islandEffected;
    private Archipelago archipelagoEffected;
    private List<Student> activeStudents;
    private List<Student> passiveStudents;
    private final List<Student> studentsOnCard = new ArrayList<>();

    public CharacterCard(Game game, String name) {
        this.name = name;
        this.game = game;
        passiveWizard =null;
        activeWizard = null;
        colorEffected = null;
        islandEffected = null;
        archipelagoEffected = null;
        activeStudents = null;
        passiveStudents = null;
    }


    public int getCost() {
        return cost;
    }

    protected void setCost(int cost){this.cost = cost;}


    public void setActiveWizard(Wizard activeWizard) {
        this.activeWizard = activeWizard;
    }

    public void setPassiveWizard(Wizard passiveWizard) {
        this.passiveWizard = passiveWizard;
    }

    public void setColorEffected(Color colorEffected) {
        this.colorEffected = colorEffected;
    }

    public void setIslandEffected(Island islandEffected) {
        this.islandEffected = islandEffected;
    }

    public void setArchipelagoEffected(Archipelago archipelagoEffected) {
        this.archipelagoEffected = archipelagoEffected;
    }

    public void setActiveStudents(List<Student> activeStudents) {
        this.activeStudents = activeStudents;
    }

    public void setPassiveStudents(List<Student> passiveStudents) {
        this.passiveStudents = passiveStudents;
    }

    public void useCard(ExpertMatch match) throws ExceptionGame{
        if(activeWizard == null){
            throw new ExceptionGame("activeWizard hasn't been set");
        }
        activeWizard.reduceCoins(getCost());
        this.cost++;
    }

    public Wizard getActiveWizard() {
        return activeWizard;
    }

    public Wizard getPassiveWizard() {
        return passiveWizard;
    }

    public Color getColorEffected() {
        return colorEffected;
    }

    public Island getIslandEffected() {
        return islandEffected;
    }

    public Archipelago getArchipelagoEffected() {
        return archipelagoEffected;
    }

    public List<Student> getActiveStudents() {
        return activeStudents;
    }

    public List<Student> getPassiveStudents() {
        return passiveStudents;
    }

    protected void resetCard(){
        passiveWizard =null;
        activeWizard = null;
        colorEffected = null;
        islandEffected = null;
        archipelagoEffected = null;
        activeStudents = null;
        passiveStudents = null;
    }

    public List<Student> getStudentsOnCard() {
        return studentsOnCard;
    }

    public String getName() {
        return name;
    }

    public Game getGame() {
        return game;
    }

    @Override
    public String toString() {
        return "CharacterCard{" +
                "cost=" + cost +
                ", name='" + name + '\'' +
                ", game=" + game +
                ", activeWizard=" + activeWizard +
                ", passiveWizard=" + passiveWizard +
                ", colorEffected=" + colorEffected +
                ", islandEffected=" + islandEffected +
                ", archipelagoEffected=" + archipelagoEffected +
                ", activeStudents=" + activeStudents +
                ", passiveStudents=" + passiveStudents +
                ", studentsOnCard=" + studentsOnCard +
                '}';
    }
}
