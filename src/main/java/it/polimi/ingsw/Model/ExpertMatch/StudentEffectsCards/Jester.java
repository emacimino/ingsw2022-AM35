package it.polimi.ingsw.Model.ExpertMatch.StudentEffectsCards;

import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.SchoolsLands.Island;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.SchoolsMembers.StudentBag;
import it.polimi.ingsw.Model.Wizard.Wizard;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Jester extends StudentEffect{
    private int cost = 1;
    ArrayList<Student> StudentsOnCard;

    public Jester(Game game) {
        super(game);
        this.setStudentsOnCard();
    }

    @Override
    public void drawStudent(int numberOfStudent, StudentBag studentBag) {
        super.drawStudent(numberOfStudent, studentBag);
    }

    public void setStudentsOnCard() {
        StudentsOnCard.add(game.getStudentBag().drawStudent());
        StudentsOnCard.add(game.getStudentBag().drawStudent());
        StudentsOnCard.add(game.getStudentBag().drawStudent());
        StudentsOnCard.add(game.getStudentBag().drawStudent());
        StudentsOnCard.add(game.getStudentBag().drawStudent());
        StudentsOnCard.add(game.getStudentBag().drawStudent());
    }

    public ArrayList<Student> getStudentsOnCard() {
        return StudentsOnCard;
    }

    public void useCharacterCard(Wizard wizard, List<Student> StudentOnEntranceToBeSwitched, List<Student> StudentOnCardToBeSwitched ){
        List<Student> tmp = wizard.getBoard().modifyEntranceByCharacterCard(StudentOnEntranceToBeSwitched, StudentOnCardToBeSwitched);
        for(Student student: tmp){
            this.StudentsOnCard.add(student);
        }
    }

    @Override
    public void usedJesterCard(Wizard wizard, List<Student> StudentOnEntranceToBeSwitched, List<Student> StudentOnCardToBeSwitched) {
        this.useCharacterCard(wizard, StudentOnEntranceToBeSwitched, StudentOnCardToBeSwitched);
        cost++;
    }
}

