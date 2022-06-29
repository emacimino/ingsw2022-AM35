package it.polimi.ingsw.Model.SchoolsMembers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Bag that contains all students, and they are casually drawn from it
 */
public class StudentBag {

    private final List<Student> studentsInBag;

    /**
     * StudentBag is the constructor of the Class StudentBag which set 26 Student
     * for each value present in the Class Color
     */

    public StudentBag(){
        studentsInBag = new ArrayList<>();
        for (Color c: Color.values()) {
            for(int i = 0; i<26; i++) {
                Student student = new Student(c);
                studentsInBag.add(student);
            }
        }
    }

    /**
     * Getter for number of students in the bag
     * @return the size of the attribute studentInBag
     */
    public int getNumberOfStudents(){
        return studentsInBag.size();
    }

    /**
     * draw a student from the bag
     * @return the Student randomly removed in the method drawStudent()
     * @throws IllegalArgumentException Exception is thrown when there are no more student to remove from StudentBag
     */
    public Student drawStudent() throws IllegalArgumentException {
        Random random = new Random();
        int toRemove = random.nextInt(studentsInBag.size());
            return studentsInBag.remove(toRemove);

    }

    /**
     * Getter for the students in bag
     * @return reference of the attribute studentsInBag
     */
    public List<Student> getStudentsInBag() {
        return studentsInBag;
    }

    /**
     * Getter for number of student in bag
     * @param c is the Color of interest
     * @return the number of Student whose attribute color is c
     */
    public int getNumberOfStudentsInBag(Color c) {
        int count = 0;
        for (Student student : studentsInBag) {
            if (student.getColor() == c) {
                count++;
            }
        }
        return count;
    }

    /**
     * Pick a student by his color
     * @param color color to pick
     * @return student of color color
     */
    public Student pickAStudentOfColor(Color color){
        for(Student s : studentsInBag){
            if (s.getColor().equals(color))
                return s;
        }
        return null;
    }

    /**
     * Override of toString
     * @return the actual situation in the bag
     */
    @Override
    public String toString() {
        return "StudentBag:" + "\n" +"Blue Students = " + getNumberOfStudentsInBag(Color.BLUE)+
                "\n" + "Green Students = " + getNumberOfStudentsInBag(Color.GREEN) +
                "\n" + "Pink Students = " + getNumberOfStudentsInBag(Color.PINK) +
                "\n" + "Red Students = " + getNumberOfStudentsInBag(Color.RED) +
                "\n" + "Yellow Students = " + getNumberOfStudentsInBag(Color.YELLOW) +
                "\n" + "total Students = " + studentsInBag.size() +
                "\n";
    }
}
