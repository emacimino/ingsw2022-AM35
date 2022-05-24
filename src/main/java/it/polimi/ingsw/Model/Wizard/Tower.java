package it.polimi.ingsw.Model.Wizard;

import java.io.Serial;
import java.io.Serializable;

/**
 *Tower Class implements the object tower of the game
 */
public class Tower implements Serializable {
    @Serial
    private static final long serialVersionUID = -6120634413404378219L;
    private final Wizard property;
    private final TowerColors towerColors;


    /**
     * Constructor of Tower, it sets the property of the tower
     * @param w is the wizard who owns the tower
     * @param towerColors
     */
    public Tower(Wizard w, TowerColors towerColors){
        this.property = w;
        this.towerColors = towerColors;
    }

    /**
     * This method returns the property of the tower
     * @return the property of the tower
     */
    public Wizard getProperty() {
        return property;
    }

    public TowerColors getTowerColors() {
        return towerColors;
    }

    @Override
    public String toString(){
        return "Tower belongs to: " + getProperty() ;
    }
}
