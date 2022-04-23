package it.polimi.ingsw.ModelTest.WizardTest;

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

    int[] ints = {7, 3};

    /**
     * This methodTest tests that the method playAssistantCard's exception are thrown correctly
     * @param cardPlayed is the assistant's card played
     */
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


    /**
     * This methodTest test the playableAssistantCard method
     * @param cardPlayed is the assistant's card played
     */
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

    /**
     * This methodTest tests checkIfAssistantCardAlreadyPlayed method
     * @param cardPlayed is the assistant's card played
     */
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


    /**
     * This methodTest tests checkIfThereIsAlternativeAssistantCard method
     * @param cardPlayed is the assistant's card played
     */
    @ParameterizedTest
    @EnumSource(AssistantsCards.class)
    void checkIfThereIsAlternativeAssistantCard_Test(AssistantsCards cardPlayed) {
        Collection<AssistantsCards> playedByOpponent = combinationOfThreeAssistantsCards();
        Wizard wizard = new Wizard("player_test", ints[0], ints[1]);
        boolean isThereAlternative = wizard.checkIfThereIsAlternativeAssistantsCard(playedByOpponent);
        Assertions.assertTrue(isThereAlternative);

        wizard.getAssistantsDeck().getPlayableAssistants().removeIf(a -> !a.equals(cardPlayed));

        if (playedByOpponent.contains(cardPlayed)) {
            boolean isThereAlternative_false = wizard.checkIfThereIsAlternativeAssistantsCard(playedByOpponent);
            Assertions.assertFalse(isThereAlternative_false);
        }


    }

    /**
     * This methodTest tests that the method placeStudentOnArchipelago's exception are thrown correctly
     * @param c is the color passed
     */
    @ParameterizedTest
    @EnumSource(Color.class)
    void placeStudentOnArchipelago_ExceptionTest(Color c) {
        Wizard wizard = new Wizard("player_test", ints[0], ints[1]);
        StudentBag studentBag = new StudentBag();
        fillBoardEntrance(studentBag, wizard.getBoard());
        List<Student> s = new ArrayList<>(wizard.getBoard().getStudentsInEntrance());
        Archipelago archipelago = new Archipelago();
        Student student_1 = new Student(c);
        Assertions.assertThrows(ExceptionGame.class, ()->wizard.placeStudentOnArchipelago(student_1,archipelago));
        for(int i= 0; i<ints[1]; i++) {
            Assertions.assertDoesNotThrow(()-> {
                Student student = s.remove(0);
                wizard.placeStudentOnArchipelago(student, archipelago);
                Assertions.assertTrue(archipelago.getStudentFromArchipelago().contains(student));
            });
        }
        Student student = s.remove(0);
        Assertions.assertThrows(ExceptionGame.class, ()-> wizard.placeStudentOnArchipelago(student, archipelago));


    }

    /**
     * This methodTest tests the placeStudentOnTable method
     */
    @Test
    void placeStudentOnTable_Test() {
        Wizard wizard = new Wizard("player_test", ints[0], ints[1]);
        StudentBag studentBag = new StudentBag();
        fillBoardEntrance(studentBag, wizard.getBoard());
        List<Student> s = new ArrayList<>(wizard.getBoard().getStudentsInEntrance());
        for(int i= 0; i<ints[1]; i++){
            Assertions.assertDoesNotThrow(()-> {
                Student student = s.remove(0);
                if(wizard.checkIfStudentIsMovable(student)) {
                    TableOfStudents table = wizard.getBoard().getTables().stream().filter(t -> t.getColor().equals(student.getColor())).findAny().get();
                    wizard.placeStudentOnTable(student);
                    Assertions.assertFalse(wizard.getBoard().getStudentsInEntrance().contains(student));
                    Assertions.assertTrue(table.getStudentsInTable().contains(student));
                }
            });
        }
        Student student = s.remove(0);
        Assertions.assertThrows(ExceptionGame.class, ()-> wizard.placeStudentOnTable(student));

    }


    /**
     * Thid methodTest tests the checkIfStudentIsMovable method
     */
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
            Assertions.assertDoesNotThrow(()->
                Assertions.assertTrue(wizard.checkIfStudentIsMovable(s))
            );
        }
    }

    /**
     * This method returns a combination of three assistant's cards
     * @return a combination of three assistant's cards
     */
    public Collection<AssistantsCards> combinationOfThreeAssistantsCards(){
        Collection<AssistantsCards> combination = new ArrayList<>();
        AssistantsDeck assistantsDeck = new AssistantsDeck();
        Random r = new Random();

        for(int i = 0; i<3; i++){
            AssistantsCards a = assistantsDeck.getPlayableAssistants().remove(r.nextInt(assistantsDeck.getPlayableAssistants().size()));
            combination.add(a);
        }
        return combination;
    }

    /**
     * This method fills the entrance of the board
     * @param studentBag is the studentBag from where the students are drawn
     * @param board is the board to fill
     */
    public void fillBoardEntrance(StudentBag studentBag, Board board){
        for(int i = 0; board.getStudentsInEntrance().size()<ints[0]; i++) {
            Student student = studentBag.drawStudent();
            board.getStudentsInEntrance().add(student);
        }
    }
}