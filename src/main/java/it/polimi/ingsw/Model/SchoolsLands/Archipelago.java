package it.polimi.ingsw.Model.SchoolsLands;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.Tower;
import it.polimi.ingsw.Model.Wizard.Wizard;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * Archipelago is used to facilitate merge between islands and contains Mother Nature's position
 */
public class Archipelago implements Serializable {
    @Serial
    private static final long serialVersionUID = 8744156061031040215L;
    private final List<Island> isle= new ArrayList<>();
    private boolean motherNaturePresence = false;
    private boolean prohibition = false;

    /**
     * construct the class
     */
    public Archipelago() {
        Island island = new Island();
        isle.add(island);
    }

    /**
     *
     * @return the islands arraylist
     */
    public List<Island> getIsle() {
        return isle;
    }

    /**
     *
     * @return a boolean that indicates mother nature presence on the archipelago
     */
    public boolean isMotherNaturePresence() {
        return motherNaturePresence;
    }

    /**
     * @param motherNaturePresence is used to indicate the presence of mother nature
     */
    public void setMotherNaturePresence(boolean motherNaturePresence) {
        this.motherNaturePresence = motherNaturePresence;
    }

    /**
     * This method receives an archipelago as argument and strips it of its islands and adds them to the calling archipelago
     * @param archipelago is the archipelago that will be merged (hence stripped of its islands)
     */
    public void mergeArchipelago(Archipelago archipelago){
        this.isle.addAll(archipelago.getIsle());
        archipelago.getIsle().clear();
    }

    /**
     * This method is used to calculate influence (relative to the Wizard w) on the archipelago and takes into account towers and students
     * @param w is the wizard of which influence is going to be calculated
     * @return and int that is the influence itself
     */
    public int calculateInfluenceInArchipelago(Wizard w){
        int influence=0;
        for (Island island: isle) {
            try{
                if(island.getTower().getProperty().equals(w)){
                    influence++;
                }
            }
            catch(ExceptionGame ignored){} //on the island there is no tower
        }
        influence += calculateInfluenceStudents(w);
        return influence;
    }

    public int calculateInfluenceStudents(Wizard w){
        int influenceStudent = 0;
        for (Island island: isle) {
            if(!w.getBoard().getProfessorInTable().isEmpty()) {
                influenceStudent += w.getBoard().getProfessorInTable().stream().mapToInt(prof -> island.getStudentFilteredByColor(prof.getColor()).size()).sum();
            }
        }
        return influenceStudent;
    }

    /**
     * This method calculates the influence added by the towers
     * @param w wizard that has a tower on the island
     * @return the influence calculated
     */
    public int calculateInfluenceTowers(Wizard w){
        int towerInfluence = 0;
        for(Island island : isle){
            try{
                if(island.getTower() != null && island.getTower().getProperty().equals(w))
                    towerInfluence ++;
            }catch (ExceptionGame e){
                System.out.println("There is no tower in this island");
            }
        }
        return towerInfluence;
    }
    /**
     * This method receives a student and adds it to the archipelago
     * @param student is the student that's going to be placed on the archipelago
     */
    public void addStudentInArchipelago(Student student){
        isle.get(0).getStudentInIsland().add(student);
    }

    public Collection<Student> getStudentFromArchipelago(){
        Collection<Student> students = new ArrayList<>();
        for(Island isle : isle){
            students.addAll(isle.getStudentInIsland());
        }
        return students;
    }

    public void placeWizardsTower(Wizard wizard) throws ExceptionGame{
        for(Island island: isle){
            Optional<Tower> t = wizard.getBoard().getTowersInBoard().stream().reduce((first, second) -> first);
            Tower tower = t.orElseThrow(()-> new ExceptionGame("Wizard of player " + wizard.getUsername()+"has no towers in board"));
            wizard.getBoard().getTowersInBoard().remove(tower);
            island.setTower(tower);

        }

    }

    public boolean isProhibition() {
        return prohibition;
    }

    public void setProhibition(boolean prohibition) {
        this.prohibition = prohibition;
    }

    @Override
    public String toString() {
        return "Archipelago{" +
                "isle=" + isle +
                ", motherNaturePresence=" + motherNaturePresence +
                '}';
    }
}
