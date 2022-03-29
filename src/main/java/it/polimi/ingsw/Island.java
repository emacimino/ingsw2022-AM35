package it.polimi.ingsw;

import java.util.Collection;
import java.util.HashSet;

public class Island {
    private Collection<Student> studentInIsland= new HashSet<>();
    private Tower tower;
    private boolean interdictionCard;


    public Collection getStudentInIsland() {
        return studentInIsland;
    }

    public Collection getStudentFilteredByColor(Color c){
        Collection<Student> filteredStudents= new HashSet<>();
        for (Student s:studentInIsland) {
            if(s.getColor()==c)filteredStudents.add(s);
        }
        return filteredStudents;
    }

    public Tower getTower() throws ExceptionTowerNotThere{
        if(tower==null){
            throw new ExceptionTowerNotThere("Tower is not present on the island");
        }
        return tower;
    }

    public void setTower(Tower tower) {
        this.tower = tower;
    }

    public boolean isInterdictionCard() {
        return interdictionCard;
    }
}
