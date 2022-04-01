package it.polimi.ingsw.ModelTest.SchoolsLandsTest.WizardTest;

import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.SchoolsMembers.StudentBag;
import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.Wizard.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;



@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WizardTest {

    int[] ints = {9, 4};
    @ParameterizedTest
    @EnumSource(AssistantsCards.class)
    @DisplayName("playAssistantCardThrowsException_Test (Method setRoundAssistantCard was tested in local since is a private method)")
    void playAssistantCardThrowsException_Test(AssistantsCards cardPlayed) {
        Collection<AssistantsCards> playedByOpponent = combinationOfThreeAssistantsCards();
        Wizard wizard = new Wizard("player_test", ints[0], ints[1]);
        if(!wizard.playableAssistantsCard(cardPlayed, playedByOpponent)){
            Assertions.assertThrows(ExceptionGame.class, ()-> wizard.playAssistantsCard(cardPlayed, playedByOpponent));
        }
    }


    @ParameterizedTest
    @EnumSource(AssistantsCards.class)
    void playableAssistantCard_Test(AssistantsCards cardPlayed) {
        Wizard wizard = new Wizard("player_test", ints[0], ints[1]);
        Collection<AssistantsCards> playedByOpponent = combinationOfThreeAssistantsCards();
        if(wizard.checkIfAssistantsCardAlreadyPlayed(cardPlayed, playedByOpponent) && wizard.checkIfThereIsAlternativeAssistantsCard(playedByOpponent))
            Assertions.assertFalse(wizard.playableAssistantsCard(cardPlayed, playedByOpponent));
        else
            Assertions.assertTrue(wizard.playableAssistantsCard(cardPlayed,playedByOpponent));

        wizard.getAssistantsDeck().getPlayableAssistants().removeIf(a -> !a.equals(cardPlayed));
        if(wizard.checkIfAssistantsCardAlreadyPlayed(cardPlayed,playedByOpponent) && !wizard.checkIfThereIsAlternativeAssistantsCard(playedByOpponent))
            Assertions.assertTrue(wizard.playableAssistantsCard(cardPlayed,playedByOpponent));
    }

    @ParameterizedTest
    @EnumSource(AssistantsCards.class)
    void checkIfAssistantCardAlreadyPlayed_Test(AssistantsCards cardPlayed) {
        Collection<AssistantsCards> playedByOpponent= combinationOfThreeAssistantsCards();
        Wizard wizard = new Wizard("player_test", ints[0], ints[1]);
        if(playedByOpponent.contains(cardPlayed)) {
            Assertions.assertTrue(wizard.checkIfAssistantsCardAlreadyPlayed(cardPlayed, playedByOpponent));
        }else
            Assertions.assertFalse(wizard.checkIfAssistantsCardAlreadyPlayed(cardPlayed,playedByOpponent));

        Collection<AssistantsCards> playedByOpponent2 = new ArrayList<>();
        Assertions.assertFalse(wizard.checkIfAssistantsCardAlreadyPlayed(cardPlayed, playedByOpponent2));

    }


    @ParameterizedTest
    @EnumSource(AssistantsCards.class)
    void checkIfThereIsAlternativeAssistantCard_Test(AssistantsCards cardPlayed) {
        Collection<AssistantsCards> playedByOpponent = combinationOfThreeAssistantsCards();
        Wizard wizard = new Wizard("player_test", ints[0], ints[1]); //It is sure that wizard.assistantsDeck has an alternative
        boolean isThereAlternative = wizard.checkIfThereIsAlternativeAssistantsCard(playedByOpponent);
        Assertions.assertTrue(isThereAlternative);   //Verified that there is an alternative

        wizard.getAssistantsDeck().getPlayableAssistants().removeIf(a -> !a.equals(cardPlayed));

        if (playedByOpponent.contains(cardPlayed)) {
            boolean isThereAlternative_false = wizard.checkIfThereIsAlternativeAssistantsCard(playedByOpponent);
            Assertions.assertFalse(isThereAlternative_false);
        }


    }

    @ParameterizedTest
    @EnumSource(Color.class)
    void placeStudentOnArchipelago_ExceptionTest(Color c) {
        Wizard wizard = new Wizard("player_test", ints[0], ints[1]);
        Student student_1 = new Student(c);
        Student student_2 = new Student(c);
        Collection<Student> s = new ArrayList<>();
        s.add(student_1);
        s.add(student_2);
        Archipelago archipelago = new Archipelago();
        Assertions.assertThrows(ExceptionGame.class, ()->wizard.placeStudentOnArchipelago(student_1,archipelago));

        try {
            wizard.placeStudentInEntrance(s);
            wizard.placeStudentOnArchipelago(student_1, archipelago);
            Assertions.assertTrue(archipelago.getStudentFromArchipelago().contains(student_1));
        }catch (ExceptionGame e){}

    }

    @Test
    void placeStudentOnTable_Test() {
        int movable = 3;
        Wizard wizard = new Wizard("player_test", ints[0], ints[1]);
        StudentBag studentBag = new StudentBag();
        fillBoardEntrance(studentBag, wizard.getBoard());
        List<Student> s = new ArrayList<>();
        s.addAll(wizard.getBoard().getStudentsInEntrance());
        for(int i= 0; i<ints[0]; i++){
            try {
                Student student = s.remove(0);
                if(wizard.checkIfStudentIsMovable(student)) {
                    TableOfStudents table = wizard.getBoard().getTables().stream().filter(t -> t.getColor().equals(student.getColor())).findAny().get();
                    wizard.placeStudentOnTable(student);
                    Assertions.assertFalse(wizard.getBoard().getStudentsInEntrance().contains(student));
                    Assertions.assertTrue(table.getStudentsInTable().contains(student));
                }
            } catch (ExceptionGame ignored) {
            }
        }

    }


    @Test
    void checkIfStudentIsMovable_Test() {
        Wizard wizard = new Wizard("player_test", ints[0], ints[1]);
        StudentBag studentBag = new StudentBag();

        for(int i = 0; i<ints[0]-ints[1]; i++){
            Student student = studentBag.drawStudent();
            wizard.getBoard().getStudentsInEntrance().add(student);
        }
        for (Student s : wizard.getBoard().getStudentsInEntrance()) {
            Assertions.assertThrows(ExceptionGame.class, ()-> wizard.checkIfStudentIsMovable(s));
        }
        fillBoardEntrance(studentBag, wizard.getBoard());
        for (Color c : Color.values()){
            Student student = new Student(c);
            Assertions.assertThrows(ExceptionGame.class, ()->wizard.checkIfStudentIsMovable(student));
        }
        for (Student s : wizard.getBoard().getStudentsInEntrance()) {
            try {
                Assertions.assertTrue(wizard.checkIfStudentIsMovable(s));
            } catch (ExceptionGame ignored) {
            }
        }
    }

    public Collection<AssistantsCards> combinationOfThreeAssistantsCards(){
        Collection<AssistantsCards> combination = new ArrayList<>();
        AssistantsDeck assistantsDeck = new AssistantsDeck();
        Random r = new Random();

        for(int i = 0; i<3; i++){
            AssistantsCards a = assistantsDeck.getPlayableAssistants().remove(r.nextInt(assistantsDeck.getPlayableAssistants().size()));
            combination.add(a);
            System.out.println("Assistant Card: value = " + a.getValue() + "; step = " + a.getStep() );
        }
        System.out.println();

        return combination;
    }

    public void fillBoardEntrance(StudentBag studentBag, Board board){
        for(int i = 0; board.getStudentsInEntrance().size()<ints[0]; i++) {
            Student student = studentBag.drawStudent();
            board.getStudentsInEntrance().add(student);
        }
    }
}