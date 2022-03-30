package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Archipelago {
    private List<Island> isle= new ArrayList<>();
    private boolean motherNaturePresence;

    public Archipelago() {
        Island island = new Island();
        isle.add(island);
    }

    public List getIsle() {
        return isle;
    }

    public void setIsle(List isle) {
        this.isle = isle;
    }

    public boolean isMotherNaturePresence() {
        return motherNaturePresence;
    }

    public void setMotherNaturePresence(boolean motherNaturePresence) {
        this.motherNaturePresence = motherNaturePresence;
    }

    public void mergeArchipelago(Archipelago archipelago){
        for (Island island: archipelago.isle) {
            isle.add(island);
        }
    }

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
            for (Professor p:w.board.professorInTable) {
                Color c=w.board.getProfessorInTable().getColor();
                influence+=island.getStudentFilteredByColor(c).size();
            }
        }
        return influence;
    }

    public void addStudentInArchipelago(Student student){
        isle.get(0).getStudentInIsland().add(student);
    }
}
