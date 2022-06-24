package it.polimi.ingsw.Client.Cli;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Island;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Professor;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.Model.Wizard.TowerColors;

import java.util.HashMap;
import java.util.List;

import static it.polimi.ingsw.Client.Cli.Constants.ANSI_BRIGHTBLACK;
import static it.polimi.ingsw.Client.Cli.Constants.ANSI_BRIGHTWHITE;

public class Printable {

    public static final String BLUE = Constants.ANSI_BLUE;
    public static final String RED = Constants.ANSI_RED;
    public static final String YELLOW = Constants.ANSI_YELLOW;
    public static final String GREEN = Constants.ANSI_GREEN;
    public static final String PINK = Constants.ANSI_PINK;
    public static final String RESET = Constants.ANSI_RESET;
    public static final String DEEP_BLUE = Constants.ANSI_DEEPBLUE;


    public static final HashMap<Color, String> colorANSI = new HashMap<>(){{
        put(Color.GREEN, GREEN);
        put(Color.BLUE, BLUE);
        put(Color.PINK, PINK);
        put(Color.RED, RED);
        put(Color.YELLOW, YELLOW);
    }};


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

    public static final String MOTHER_NATURE = Constants.ANSI_ORANGE + "§§" + RESET;


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



    public static final String TOWER_TOP =
            " █████ ";
    public static final String TOWER_MIDDLE =
            "  ███  ";




    public static final String BOARD_TOP_MARGIN = "########";

    /**
     * Get the name for the cli for assistant card
     * @param assistantsCards list of playable assistant card
     * @return the assistant card selected
     */
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

    /**
     * print students in cli
     * @param student student to be printed
     * @return string of its color
     */
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


    /**
     * Print tower in board
     * @param numberOfTowers number of towers
     * @param towerColor color of towers
     */
    public static void printBoardTowers(int numberOfTowers, String towerColor) {
        final String color;
        switch (towerColor) {
            case "Black" -> color = ANSI_BRIGHTBLACK;
            case "White" -> color = RESET;
            case "Gray" -> color = ANSI_BRIGHTWHITE;
            default -> throw new IllegalStateException("Unexpected value: " + towerColor);
        }
        int top_margin_length = 13;
        int firstRowTowers=numberOfTowers - 4 ;
        for (int i = 0; i < top_margin_length; i++) {
            System.out.print(BOARD_TOP_MARGIN);
        }
        System.out.print("\n");
        if(firstRowTowers > 0){
            printTowersRow(firstRowTowers, color);
            numberOfTowers = numberOfTowers - firstRowTowers;
        }
        System.out.print("\n");
        if(numberOfTowers > 0){
            printTowersRow(numberOfTowers, color);
            System.out.print("\n");
        }
        for (int i = 0; i < top_margin_length; i++) {
            System.out.print(BOARD_TOP_MARGIN);
        }
        System.out.print("\n");
    }

    /**
     * Print tower
     * @param firstRowTowers first row Tower
     * @param color color of the tower
     */
    public static void printTowersRow(int firstRowTowers, String color){
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
    }

    /**
     * print tower
     * @param towerColors the color of the tower to be printed
     */
    public static void printTowersIsland(TowerColors towerColors){
        final String color;
        switch (towerColors.name()) {
            case "Black" -> color = ANSI_BRIGHTBLACK;
            case "White" -> color = RESET;
            case "Gray" -> color = ANSI_BRIGHTWHITE;
            default -> throw new IllegalStateException("Unexpected value: " + towerColors);
        }
        System.out.print( color + TOWER_TOP + RESET);
        System.out.print("       |" + "\n" + "|  ");
        System.out.print(color + TOWER_MIDDLE + RESET);
        System.out.print("       |" + "\n" + "|  ");
        System.out.print(color + TOWER_MIDDLE + RESET);
        System.out.print("       |" + "\n" + "|   ");
    }

    /**
     * Print students and professors that belongs to the board
     * @param students to be printed
     * @param professors to be printed
     */
    public static void printBoardProfessorAndTables(List<Professor> professors, List<Student> students) {
                for (Color color :
                        Color.values()) {
                    printProfessorSeat(professors, color);
                    System.out.print("  #  ");
                            int n = 0;
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

    /**
     * Print professors
     * @param professors list of professors
     * @param color chosen to print
     */
    public static void printProfessorSeat(List<Professor> professors, Color color){
        for (Professor professor: professors){
            if(professor.getColor() == color){
                System.out.print(colorANSI.get(color) + " #| ");
                System.out.print(PROF);
                System.out.print(" |# " + RESET);
                return;
            }
        }
        System.out.print(colorANSI.get(color) + " #|   ");
        System.out.print("  |# " + RESET);
    }
    /**
     * Print entrance and coins
     * @param students to be printed
     * @param coins to be printed
     */
    public static void printEntranceAndCoins(List<Student> students, int coins){
        for (int i = 0; i < 13; i++) {
            System.out.print(BOARD_TOP_MARGIN);
        }
        System.out.print("\n");
        for (Student s:
             students) {
            System.out.print(colorANSI.get(s.getColor()) + STUDENT + RESET + "   ");
        }
        System.out.print("\n" + "Coins: ");
        for (int i = 0; i < coins; i++) {
            System.out.print(GREEN + " $ ");
        }
    }

    /**
     * Print an archipelago
     * @param archipelago to be printed
     */
    public static void printArchipelago(Archipelago archipelago) {
        System.out.print(DEEP_BLUE + "\n ##################### \n\n");
        for (int i = 0; i < 16; i++) System.out.print(RESET + "—");
        System.out.print("\n" + "|  ");
        if(archipelago.isMotherNaturePresence()) {
            System.out.print(MOTHER_NATURE + "          |");
        }
        else{
            System.out.print("            |");
        }
        for (Island isle:
             archipelago.getIsle()) {
            System.out.print("\n" + "| ");
            if(isle.isThereTower())
                try {
                    printTowersIsland(isle.getTower().getTowerColors());
                }catch (ExceptionGame e){
                    System.out.println(e.getMessage());
                }
            int counter = 0;
            for (Student student:
                 isle.getStudentInIsland()) {
                counter++;
                if(counter%4 == 1 && counter != 1){
                    System.out.print(" |\n" + "|  ");
                }
                System.out.print(" " + colorANSI.get(student.getColor()) + STUDENT + RESET + " ");

                }
            if(counter%4!=0) {
                for (int i = 0; i < (4 - counter%4); i++) System.out.print("    ");
            }
            if(counter == 0)System.out.print("            ");
            System.out.print("|" + "\n" + "|   ");
        }
        System.out.print("           |\n");
        for (int i = 0; i < 16; i++) System.out.print(RESET + "—");
        System.out.print("\n");
    }

}
