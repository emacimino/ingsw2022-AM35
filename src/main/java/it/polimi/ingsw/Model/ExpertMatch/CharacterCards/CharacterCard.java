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

import java.util.List;

public abstract class CharacterCard {
    protected int cost;
    protected String name;
    protected Game game;
    protected Wizard activeWizard;
    protected Wizard passiveWizard;
    protected Color colorEffected;
    protected Island islandEffected;
    protected Archipelago archipelagoEffected;
    protected List<Student> activeStudents;
    protected List<Student> passiveStudents;
    protected List<Student> studentsOnCard;

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
        studentsOnCard = null;
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

    protected void resetCard(){
        passiveWizard =null;
        activeWizard = null;
        colorEffected = null;
        islandEffected = null;
        archipelagoEffected = null;
        activeStudents = null;
        passiveStudents = null;
        studentsOnCard = null;
    }

    public List<Student> getStudentsOnCard() {
        return studentsOnCard;
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
