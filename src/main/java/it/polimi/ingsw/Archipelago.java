package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
        archipelago.getIsle().removeAll(isle);
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
            if(!w.board.getProfessorInTable().isEmpty()) {
                influence += w.board.professorInTable.stream().mapToInt(prof -> island.getStudentFilteredByColor(prof.getColor()).size()).sum();
            }
        }
        return influence;
    }

    public void addStudentInArchipelago(Student student){
        isle.get(0).getStudentInIsland().add(student);
    }
}
