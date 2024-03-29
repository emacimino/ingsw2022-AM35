package it.polimi.ingsw.Model.SchoolsMembers;

import it.polimi.ingsw.Model.Wizard.Wizard;

import java.io.Serial;
import java.io.Serializable;

/**
 * Professor, set who have the influence on one color of students
 */
public class Professor implements Serializable {
    @Serial
    private static final long serialVersionUID = 5617884145379497150L;
    private final Color color;
    private Wizard property;
/**
 * Constructor of the class
 * @param color is the professor's color and is generated by the Enum Color
 * */
    public Professor(Color color) {
        this.color = color;
    }
/**
 * Setter of property of the wizard
 * @param property is used to tell which Wizard owns what
*/
public void setProperty(Wizard property) {
        this.property = property;
    }

    /**
     * Getter for color of the professor
     * @return color of the professor
     */
    public Color getColor() {
        return color;
    }

    /**
     * Getter for the wizard who owns the professor
     * @return wizard who owns the professor
     */
    public Wizard getProperty() {
        return property;
    }

    /**
     * Override of toString
     * @return professor and his color
     */
    @Override
    public String toString() {
        return "Professor{" +
                "color=" + color +
                '}';
    }
}
