package it.polimi.ingsw.Client;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Professor;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.Model.Wizard.Wizard;

import javax.print.attribute.standard.PresentationDirection;
import java.util.HashMap;
import java.util.List;

import static it.polimi.ingsw.Client.Constants.ANSI_BRIGHTBLACK;
import static it.polimi.ingsw.Client.Constants.ANSI_BRIGHTWHITE;

public class Printable {

    public static final String BLUE = Constants.ANSI_BLUE;
    public static final String RED = Constants.ANSI_RED;
    public static final String YELLOW = Constants.ANSI_YELLOW;
    public static final String GREEN = Constants.ANSI_GREEN;
    public static final String PINK = Constants.ANSI_PINK;
    public static final String RESET = Constants.ANSI_RESET;

    public static final String PLUS = "+";

    public static final HashMap<Color, String> colorANSI = new HashMap<>(){{
        put(Color.GREEN, GREEN);
        put(Color.BLUE, BLUE);
        put(Color.PINK, PINK);
        put(Color.RED, RED);
        put(Color.YELLOW, YELLOW);
    }};

    public static final String LINE_BLOCK =
            "█████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████";

    public static final String bigTitle=
            """
                    ███████  ███████   ██      ██      ██     ██  ██████  ██    ██   █████
                    ██       ██    █   ██     █  █     ██ █   ██    ██      █  █    █    \s
                    ███████  ██████    ██    █ ██ █    ██  █  ██    ██       ██      ██  \s
                    ██       ██   █    ██   ██    ██   ██   █ ██    ██       ██        ██\s
                    ███████  ██    ██  ██  ██      ██  ██    ███    ██       ██    █████ \s
                    """
            ;

    public static final String STUDENT = "█";
    public static final String STUDENT_BLUE = BLUE +"█"+ RESET;
    public static final String STUDENT_RED = RED + "█"+ RESET;
    public static final String STUDENT_YELLOW = YELLOW + "█"+ RESET;
    public static final String STUDENT_GREEN = GREEN +"█"+ RESET;
    public static final String STUDENT_PINK = PINK + "█"+ RESET;

    public static final String PROF = "███";
    public static final String PROF_BLUE = BLUE +"███"+ RESET;
    public static final String PROF_RED = RED + "███"+ RESET;
    public static final String PROF_YELLOW = YELLOW + "███"+ RESET;
    public static final String PROF_GREEN = GREEN +"███"+ RESET;
    public static final String PROF_PINK = PINK + "███"+ RESET;

    public static final String TABLE_OF_STUDENTS_PINK = PINK + " | "+ RESET;
    public static final String TABLE_OF_STUDENTS_GREEN = GREEN + " | "+ RESET;
    public static final String TABLE_OF_STUDENTS_BLUE = BLUE + " | "+ RESET;
    public static final String TABLE_OF_STUDENTS_YELLOW = YELLOW + " | "+ RESET;
    public static final String TABLE_OF_STUDENTS_RED = RED + " | "+ RESET;

    public static final String ASSISTANT_ONE = Constants.ASSISTANT_ONE + ", Value = 1, Steps = 1";
    public static final String ASSISTANT_TWO = Constants.ASSISTANT_TWO + ", Value = 2, Steps = 1";
    public static final String ASSISTANT_THREE = Constants.ASSISTANT_THREE + ", Value = 3, Steps = 2";
    public static final String ASSISTANT_FOUR = Constants.ASSISTANT_FOUR + ", Value = 4, Steps = 2";
    public static final String ASSISTANT_FIVE = Constants.ASSISTANT_FIVE + ", Value = 5, Steps = 3";
    public static final String ASSISTANT_SIX = Constants.ASSISTANT_SIX + ", Value = 6, Steps = 3";
    public static final String ASSISTANT_SEVEN = Constants.ASSISTANT_SEVEN + ", Value = 7, Steps = 4";
    public static final String ASSISTANT_EIGHT = Constants.ASSISTANT_EIGHT + ", Value = 8, Steps = 4";
    public static final String ASSISTANT_NINE = Constants.ASSISTANT_NINE + ", Value = 9, Steps = 5";
    public static final String ASSISTANT_TEN = Constants.ASSISTANT_TEN + ", Value = 10, Steps = 5";


    public static final String TOWER_GREY = RESET +
            "█████\n" +
            " ███ \n" +
            " ███ " + RESET;
    public static final String TOWER_BLACK = ANSI_BRIGHTBLACK +
            "█████\n" +
            " ███ \n" +
            " ███ " + RESET;
    public static final String TOWER_WHITE = ANSI_BRIGHTWHITE +
            "█████\n" +
            " ███ \n" +
            " ███ " + RESET;
    public static final String TOWER_TOP =
            " █████ ";
    public static final String TOWER_MIDDLE =
            "  ███  ";



    public static final String BOARD_LEFT_MARGIN = "##   ";
    public static final String BOARD_RIGHT_MARGIN = "   ##";
    public static final String BOARD_TOP_MARGIN = "########";

    public static String getAssistantCardCLI(AssistantsCards assistantsCards){
        switch (assistantsCards.getValue()){
            case 1 -> {
                return ASSISTANT_ONE;
            }
            case 2 -> {
                return ASSISTANT_TWO;
            }
            case 3 -> {
                return ASSISTANT_THREE;
            }
            case 4 -> {
                return ASSISTANT_FOUR;
            }
            case 5 -> {
                return ASSISTANT_FIVE;
            }
            case 6 -> {
                return ASSISTANT_SIX;
            }
            case 7 -> {
                return ASSISTANT_SEVEN;
            }
            case 8 -> {
                return ASSISTANT_EIGHT;
            }
            case 9 -> {
                return ASSISTANT_NINE;
            }
            case 10 -> {
                return ASSISTANT_TEN;
            }
            default -> {
                return "ERROR IN SEND ASSISTANT'S CARDS";
            }
        }
    }
    public static String getStudentsCLI(Student student){
        String studentString;
        switch (student.getColor()) {
            case GREEN -> studentString = STUDENT_GREEN;
            case BLUE -> studentString = STUDENT_BLUE;
            case PINK -> studentString = STUDENT_PINK;
            case RED -> studentString = STUDENT_RED;
            case YELLOW -> studentString = STUDENT_YELLOW;
            default -> studentString = null;
        }
        return studentString;
    }

    public static String getProfessorsCLI(Professor professor){
        String professorString;
        switch (professor.getColor()) {
            case GREEN -> professorString = STUDENT_GREEN;
            case BLUE -> professorString = STUDENT_BLUE;
            case PINK -> professorString = STUDENT_PINK;
            case RED -> professorString = STUDENT_RED;
            case YELLOW -> professorString = STUDENT_YELLOW;
            default -> professorString = null;
        }
        return professorString;
    }

    public static void printGenericBoard(Wizard wizard){
        Printable.printBoardTowers(wizard.getBoard().getTowersInBoard().size(), wizard.getTowerColor());
        try {
            Printable.printBoardProfessorAndTables(wizard);
        } catch (ExceptionGame e) {
            e.printStackTrace();
        }
        Printable.printEntrance((List<Student>) wizard.getBoard().getStudentsInEntrance());
    }
    public static void printCharacterCards(List<CharacterCard> characterCards){
        for (CharacterCard c: characterCards) {
            System.out.println(c);
        }
    }


    public static void printBoardTowers(int numberOfTowers, String towerColor) {
        final String color;
        switch (towerColor) {
            case "Black" -> color = ANSI_BRIGHTBLACK;
            case "White" -> color = RESET;
            case "Gray" -> color = ANSI_BRIGHTWHITE;
            default -> throw new IllegalStateException("Unexpected value: " + towerColor);
        }
        int top_margin_lenght = 13;
        int firstRowTowers=numberOfTowers - 4 ;
        for (int i = 0; i < top_margin_lenght; i++) {
            System.out.print(BOARD_TOP_MARGIN);
        }
        System.out.print("\n");
        if(firstRowTowers > 0){
            for (int i = 0; i < firstRowTowers; i++) {
                System.out.print( color + TOWER_TOP + RESET);
            }
            System.out.print("\n");
            for (int i = 0; i < firstRowTowers; i++) {
                System.out.print(color + TOWER_MIDDLE + RESET);
            }
            System.out.print("\n");
            for (int i = 0; i < firstRowTowers; i++) {
                System.out.print(color + TOWER_MIDDLE + RESET);
            }
            numberOfTowers = numberOfTowers - firstRowTowers;
        }
        System.out.print("\n");
        if(numberOfTowers > 0){
            for (int i = 0; i < numberOfTowers; i++) {
                System.out.print( color + TOWER_TOP + RESET);
            }
            System.out.print("\n");
            for (int i = 0; i < numberOfTowers; i++) {
                System.out.print(color + TOWER_MIDDLE + RESET);
            }
            System.out.print("\n");
            for (int i = 0; i < numberOfTowers; i++) {
                System.out.print(color + TOWER_MIDDLE + RESET);
            }
            System.out.print("\n");
        }
        for (int i = 0; i < top_margin_lenght; i++) {
            System.out.print(BOARD_TOP_MARGIN);
        }
        System.out.print("\n");
    }

        public static void printBoardProfessorAndTables(Wizard wizard) throws ExceptionGame {
            List<Professor> professors = wizard.getBoard().getProfessorInTable();

        for (Color color : Color.values()) {
                    printProfessorSeat(professors, color);
                    System.out.print("  #  ");
                            int n = 0;
            List<Student> students = (List<Student>) wizard.getBoard().getStudentsFromTable(color);

            for (Student student : students) {
                        if (student.getColor() == color) n++;
                    }
                            for (int i = 0; i < (10 - n); i++) {
                                System.out.print(RESET + " | ");
                                System.out.print(" ");
                                System.out.print(" | " + RESET);
                        }
                            for(int i=0;i<n;i++){
                                System.out.print(colorANSI.get(color) + " | ");
                                System.out.print(STUDENT);
                                System.out.print(" | " + RESET);
                    }
                            System.out.print("\n");
                }
                System.out.print("\n");
    }


    public static void printProfessorSeat(List<Professor> p, Color c){
        for (Professor professor: p){
            if(professor.getColor() == c){
                System.out.print(colorANSI.get(c) + " #| ");
                System.out.print(PROF);
                System.out.print(" |# " + RESET);
                return;
            }
        }
        System.out.print(colorANSI.get(c) + " #|   ");
        System.out.print("  |# " + RESET);
    }

    public static void printEntrance(List<Student> students){
        for (int i = 0; i < 13; i++) {
            System.out.print(BOARD_TOP_MARGIN);
        }
        System.out.print("\n");
        for (Student s:
             students) {
            System.out.print(colorANSI.get(s.getColor()) + STUDENT + RESET + "   ");
        }

    }

}
