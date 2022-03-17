package it.polimi.ingsw;
import java.util.*;


public class Archipelago {
    private Collection <Island> Isle= new ArrayList<Island>();

    public Archipelago(Collection<Island> isle) {
        Isle = isle;
    }

    public Collection<Island> getIsle() {
        return Isle;
    }

    public void setIsle(Collection<Island> isle) {
        Isle = isle;
    }

    public void mergeIsland(Island){};

    public String InfluenceCalc(){};

    public Boolean getMotherNature(){};
}
