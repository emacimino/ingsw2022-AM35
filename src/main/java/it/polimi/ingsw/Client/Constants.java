package it.polimi.ingsw.Client;

import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;

import javax.print.attribute.HashPrintJobAttributeSet;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final String ASSISTANT_TEN = "CARD TEN";
    public static final String ASSISTANT_NINE = "CARD NINE";
    public static final String ASSISTANT_EIGHT = "CARD EIGHT";
    public static final String ASSISTANT_SEVEN = "CARD SEVEN";
    public static final String ASSISTANT_SIX = "CARD SIX";
    public static final String ASSISTANT_FIVE = "CARD FIVE";
    public static final String ASSISTANT_FOUR = "CARD FOUR";
    public static final String ASSISTANT_THREE = "CARD THREE";
    public static final String ASSISTANT_TWO = "CARD TWO";
    public static final String ASSISTANT_ONE = "CARD ONE";


    public static final String HERBALIST = "HERBALIST";
    public static final String JESTER = "JESTER";
    public static final String MAGICIAN = "MAGICIAN";
    public static final String KNIGHT = "KNIGHT";
    public static final String CHEF = "CHEF";
    public static final String BAKER = "BAKER";
    public static final String BANKER = "BANKER";
    public static final String ARCHER = "ARCHER";
    public static final String PRINCESS = "PRINCESS";
    public static final String MESSENGER = "MESSENGER";
    public static final String FRIAR = "FRIAR";
    public static final String MINSTREL = "MINSTREL";

    public static final String WHITE_TOWER = "WHITE TOWER";
    public static final String BLACK_TOWER = "BLACK TOWER";
    public static final String GRAY_TOWER = "GRAY TOWER";

    public static final String ANSI_UNDERLINE = "\033[4m";
    public static final String ANSI_RESET = "\033[0m";
    public static final String ANSI_RED = "\033[31m";
    public static final String ANSI_GREEN = "\033[32m";
    public static final String ANSI_YELLOW = "\033[93m";
    public static final String ANSI_BLUE = "\033[34m";
    public static final String ANSI_PINK = "\033[95m";
    public static final String ANSI_BRIGHTBLACK = "\u001b[30;1m";
    public static final String ANSI_BRIGHTWHITE = "\u001b[37;1m";
    public static final String ANSI_ORANGE = "\u001b[38;5;208m";

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



}
