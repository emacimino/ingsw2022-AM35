package it.polimi.ingsw.Client.Cli;

import it.polimi.ingsw.Model.Wizard.AssistantsCards;

/**
 * Useful constants for printable methods
 */
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
    public static final String ANSI_RESET = "\033[0m";
    public static final String ANSI_RED = "\033[31m";
    public static final String ANSI_GREEN = "\033[32m";
    public static final String ANSI_YELLOW = "\033[93m";
    public static final String ANSI_BLUE = "\033[34m";
    public static final String ANSI_PINK = "\033[95m";
    public static final String ANSI_BRIGHT_BLACK = "\u001b[30;1m";
    public static final String ANSI_BRIGHT_WHITE = "\u001b[37;1m";
    public static final String ANSI_ORANGE = "\u001b[38;5;208m";
    public static final String ANSI_DEEP_BLUE = "\u001b[34;1m";

    /**
     * Pick the correct assistant card from it-s value
     * @param assistantsCards to be picked
     * @return string to identify the assistant card picked
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



}
