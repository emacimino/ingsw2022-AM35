package it.polimi.ingsw;

import java.util.Collection;
import java.util.HashSet;

public class Island {
    private final Collection<Student> studentInIsland= new HashSet<>();
    private Tower tower;
    private boolean interdictionCard;


    public Collection<Student> getStudentInIsland() {
        return studentInIsland;
    }

    public Collection<Student> getStudentFilteredByColor(Color c){
        Collection<Student> filteredStudents= new HashSet<>();
        for (Student s:studentInIsland) {
            if(s.getColor()==c)
                filteredStudents.add(s);
        }
        return filteredStudents;
    }

    public Tower getTower() throws ExceptionGame{
        if(tower==null){
            throw new ExceptionGame("Tower is not present on the island");
        }
        return tower;
    }

    public void setTower(Tower tower) {
        try {
            Wizard wizard = this.tower.getProperty();
            wizard.getBoard().getTowersInBoard().add(this.tower);
            this.tower = tower;
        }catch(NullPointerException e){
            this.tower = tower;
        }

    }

    public boolean isInterdictionCard() {
        return interdictionCard;
    }
}
