package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Game {
    private List<Wizard> wizards = new ArrayList<>();
    private Collection<AssistantsCards> assistantsCardsPlayedInRound = new ArrayList<>();
    //List<Archipelagos> archipelagos = new ArrayList<>();
    private StudentBag studentBag = new StudentBag();
    //Collection<Professor> professors = new ArrayList<>();
    private  Collection<Cloud> clouds = new ArrayList<>();
    private MotherNature motherNature = new MotherNature();
    private int limitOfStudentInEntrance=7;
    private int numOfStudentMovable=3;

    public void setWizards(List<Player> player){
        for(Player p: player){
            wizards.add(new Wizard(p.getUsername(), limitOfStudentInEntrance, numOfStudentMovable));
        }
    }

    public void setClouds(int numberOfClouds, int numberOfStudentsOnCloud){
        for( int i=0; i<numberOfClouds; i++){
            clouds.add(new Cloud(numberOfStudentsOnCloud));
        }
    }

    public Wizard getWizardFromPlayer(Player player) throws ExceptionGame{
        for (Wizard w : wizards){
            if(player.getUsername().equals(w.getUsername()))
                return w;
        }
        throw new ExceptionGame("Player is not in this Game");
    }

    public void setStudentOnCloud() throws ExceptionGame{
        for(Cloud c : clouds){
            c.setStudentOnCloud(studentBag);
        }
    }

    public void moveStudentFromCloudToBoard(Player player, Cloud cloud ) throws ExceptionGame{
        Wizard wizard = getWizardFromPlayer(player);
        Collection<Student> students = cloud.removeStudentFromCloud();
        placeStudentInEntrance(wizard, students);
    }

    public void placeStudentInEntrance(Wizard wizard, Collection<Student> students){

    }

}
