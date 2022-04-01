package it.polimi.ingsw;

import java.util.*;

public class Archipelago {
    private List<Island> isle= new ArrayList<>();
    private boolean motherNaturePresence;

    public Archipelago() {
        Island island = new Island();
        isle.add(island);
    }

    public List<Island> getIsle() {
        return isle;
    }

    //serve?
    public void setIsle(List<Island> isle) {
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
                if(island.getTower().getProperty().equals(w)){
                    influence++;
                }
            }
            catch(ExceptionGame ignored){}

            if(!w.getBoard().getProfessorInTable().isEmpty()) {
                influence += w.getBoard().getProfessorInTable().stream().mapToInt(prof -> island.getStudentFilteredByColor(prof.getColor()).size()).sum();
            }
        }
        return influence;
    }

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
}
