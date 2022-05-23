package it.polimi.ingsw.Model.Wizard;

import java.io.Serializable;

/**
 *Tower Class implements the object tower of the game
 */
public class Tower implements Serializable {
    private static final long serialVersionUID = -6120634413404378219L;
    private final Wizard property;

    /**
     * Constructor of Tower, it sets the property of the tower
     * @param w is the wizard who owns the tower
     */
    public Tower(Wizard w){
        this.property = w;
    }

    /**
     * This method returns the property of the tower
     * @return the property of the tower
     */
    public Wizard getProperty() {
        return property;
    }

    @Override
    public String toString(){
        return "Tower belongs to: " + getProperty() ;
    }
}
