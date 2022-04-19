package it.polimi.ingsw.Model.ExpertMatch.StudentEffectsCards;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.SchoolsMembers.StudentBag;
import it.polimi.ingsw.Model.Wizard.Board;
import it.polimi.ingsw.Model.Wizard.Wizard;


import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 */
public class Jester extends StudentEffect{
    private int cost = 1;
    ArrayList<Student> StudentsOnCard = new ArrayList<>();
    ArrayList<Student> StudentsOnEntrance = new ArrayList<>();

    public Jester(ArrayList<Student> studentsOnCard, ArrayList<Student> studentsOnEntrance) {
        StudentsOnCard = studentsOnCard;
        StudentsOnEntrance = studentsOnEntrance;
    }

  /*  public void jester(StudentBag studentBag, Board board){
        StudentsOnCard.add(studentBag.drawStudent());
        StudentsOnCard.add(studentBag.drawStudent());
        StudentsOnCard.add(studentBag.drawStudent());
        StudentsOnCard.add(studentBag.drawStudent());
        StudentsOnCard.add(studentBag.drawStudent());
        StudentsOnCard.add(studentBag.drawStudent());
        StudentsOnEntrance = (ArrayList<Student>) board.getStudentsInEntrance();
    }
*/
    public void useCharacterCard(Jester jester, Wizard w,StudentBag studentBag) throws ExceptionGame{
        for (Student student : jester.StudentsOnCard = jester.drawStudent(6, studentBag)) {
            
        }

        //System.out.println(w.getBoard().getStudentsInEntrance().stream());
        //ArrayList<Student> StudentsFromEntranceToBeTrade = new ArrayList<>();
        //ArrayList<Student> StudentsFromCardToBeTrade = new ArrayList<>();
        String sStud;

        for (int i = 0; i < 3; i++) {
            System.out.println("Choose a Student to trade from the ones on the card by his color : ");
            Scanner scanIn = new Scanner(System.in);
            sStud = scanIn.nextLine();
            for (Student stud : StudentsOnCard) {
                if (stud.getColor().equals(sStud))
                    StudentsFromCardToBeTrade.add(stud);
                else
                    throw new ExceptionGame("Student not founded");
            }
            scanIn.close();
        }

        for (int i = 0; i < 3; i++) {
            System.out.println("Choose a Student to trade from the ones in entrance by his color : ");
            Scanner scanIn = new Scanner(System.in);
            sStud = scanIn.nextLine();
            for (Student stud : StudentsOnEntrance) {
                    if (stud.getColor().equals(sStud))
                        StudentsFromEntranceToBeTrade.add(stud);
                    else
                        throw new ExceptionGame("Student not founded");
                }
            scanIn.close();
            }

        for(Student stud : StudentsFromEntranceToBeTrade){
            for(Student student : StudentsOnEntrance)
                if(stud.getColor().equals(student.getColor())){
                    StudentsOnEntrance.remove(stud);
                    StudentsOnCard.add(stud);
                }
        }

        for(Student stud : StudentsFromCardToBeTrade){
            for(Student student : StudentsOnCard)
                if(stud.getColor().equals(student.getColor())){
                    StudentsOnCard.remove(stud);
                    StudentsOnEntrance.add(stud);
                }
        }

    }
}

