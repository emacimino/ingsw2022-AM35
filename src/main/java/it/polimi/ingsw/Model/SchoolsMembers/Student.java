package it.polimi.ingsw.Model.SchoolsMembers;

import java.io.Serial;
import java.io.Serializable;

/**
 * Student class, are owned by wizards
 */
public class Student implements Serializable {
    @Serial
    private static final long serialVersionUID = -8625622548772539723L;
    private final Color color;

    /**
     * Constructor of the class
     * @param color is the attribute of Class COLOR of the object of Student
     */
    public Student(Color color){
        this.color = color;
    }

    /**Getter for the Color value of the object Student
     * @return the Color value of the object Student
     */
    public Color getColor() {
        return color;
    }

    /**
     * Override of toString
     * @return student and his color
     */
    @Override
    public String toString() {
        return "Student: " + "color = " + color ;
    }

}
