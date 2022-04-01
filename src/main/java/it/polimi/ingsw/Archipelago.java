package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Archipelago is used to facilitate merge between islands and contains Mother Nature's position
 */
public class Archipelago {
    private List<Island> isle= new ArrayList<>();
    private boolean motherNaturePresence;

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
        for (Island island: archipelago.isle) {
            isle.add(island);
        }
        archipelago.getIsle().removeAll(isle);
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
                if(island.getTower().getProperty()==w){
                    influence++;
                }
            }
            catch(ExceptionTowerNotThere e){
                ;
            }
            if(!w.board.getProfessorInTable().isEmpty()) {
                influence += w.board.professorInTable.stream().mapToInt(prof -> island.getStudentFilteredByColor(prof.getColor()).size()).sum();
            }
        }
        return influence;
    }

    /**
     * This method receives a student and adds it to the archipelago
     * @param student is the student that's going to be placed on the archipelago
     */
    public void addStudentInArchipelago(Student student){
        isle.get(0).getStudentInIsland().add(student);
    }
}
