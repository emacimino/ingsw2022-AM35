package it.polimi.ingsw;

import java.util.*;

public class Island {
    private Collection <Student> StudentInIsland = new Collection <Student>();
    private Collection <Tower> TowerInIsland = new Collection <Tower>();

    public Island(Collection<Student> studentInIsland) {
        StudentInIsland = studentInIsland;
    }

    public Collection<Student> getStudentInIsland() {
        return StudentInIsland;
    }

    public Collection<Tower> getTowerInIsland() {
        return TowerInIsland;
    }

    public void setStudentInIsland(Collection<Student> studentInIsland) {
        StudentInIsland = studentInIsland;
    }

    public void setTowerInIsland(Collection<Tower> towerInIsland) {
        TowerInIsland = towerInIsland;
    }

}
