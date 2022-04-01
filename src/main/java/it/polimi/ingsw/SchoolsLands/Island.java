package it.polimi.ingsw.SchoolsLands;

import it.polimi.ingsw.Charachter.Color;
import it.polimi.ingsw.Exception.ExceptionGame;
import it.polimi.ingsw.Charachter.Student;
import it.polimi.ingsw.Wizard.Tower;
import it.polimi.ingsw.Wizard.Wizard;

import java.util.Collection;
import java.util.HashSet;

/**
 * This class represents the island that is instanced in the archipelago
 */

public class Island {
    private final Collection<Student> studentInIsland= new HashSet<>();
    private Tower tower;
    private boolean interdictionCard;

    /**
     * @return an HashSet of students present on the island
     */
    public Collection<Student> getStudentInIsland() {
        return studentInIsland;
    }

    /**
     * This method returns the student filtered by color
     * @param c color of the students you are looking for
     * @return an HashSet of student which color is c
     */
    public Collection<Student> getStudentFilteredByColor(Color c){
        Collection<Student> filteredStudents= new HashSet<>();
        for (Student s:studentInIsland) {
            if(s.getColor()==c)
                filteredStudents.add(s);
        }
        return filteredStudents;
    }

    /**
     * This method returns a tower object
     * @return a tower
     * @throws ExceptionGame that signals that there is no tower on the island
     */
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
