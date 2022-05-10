package it.polimi.ingsw.View;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Constant {
    public static final String HERBALIST = "HERBALIST";
    public static final String MESSENGER = "MESSENGER";
    public static final String MINISTREL = "MINISTREL";
    public static final String CHEF = "CHEF";
    public static final String BAKER = "BAKER";
    public static final String BANKER = "BANKER";
    public static final String PRINCESS = "PRINCESS";
    public static final String JESTER = "JESTER";
    public static final String FRIAR = "FRIAR";
    public static final String KNIGHT = "KNIGHT";
    public static final String MAGICIAN = "MAGICIAN";
    public static final String ARCHER = "ARCHER";

    private Constant(){}

    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 4;
    public static final String ANSI_UNDERLINE = "\033[4m";
    public static final String ANSI_RESET = "\033[0m";
    public static final String ANSI_RED = "\033[31m";
    public static final String ANSI_GREEN = "\033[32m";
    public static final String ANSI_YELLOW = "\033[33m";
    public static final String ANSI_BLUE = "\033[34m";
    public static final String ANSI_PURPLE = "\033[35m";
    public static final String ANSI_CYAN = "\033[36m";
    public static final String ANSI_WHITE = "\033[37m";
    public static final String ANSI_BACKGROUND_BLACK = "\033[40m";
    public static final String ANSI_BACKGROUND_PURPLE = "\033[45m";

    public static final String ERIANTYS =
                      "███████╗██████╗ ██╗ █████╗ ███╗   ██╗████████╗ ██╗    ██╗ ███████╗\n"
                    + "██╔════╝██╔══██╗██║██╔══██╗████╗  ██║╚══██╔══╝ ██║    ██║ ██╔════╝\n"
                    + "███████╗██████╔╝██║███████║██╔██╗ ██║   ██║      ██║ ██╔╝ ███████╗\n"
                    + "██║     ██╔══██╗██║██╔══██║██║╚██╗██║   ██║        ██╔═╝  ╚════██║\n"
                    + "███████║██║  ██║██║██║  ██║██║ ╚████║   ██║        ██║    ███████║\n"
                    + "╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═╝══╝  ╚═══╝   ╚═╝        ╚═╝    ╚══════╝";

    public static final String AUTHORS =
            "\nby "
                    + ANSI_RED
                    + "Camilla Andiloro"
                    + ANSI_RESET
                    + ", "
                    + ANSI_CYAN
                    + "Marco Crisafulli"
                    + ANSI_RESET
                    + ", "
                    + ANSI_GREEN
                    + "Emanuele Cimino"
                    + ANSI_RESET;
    public static final String RULES =
            "\nView full rules here: https://www.craniocreations.it/wp-content/uploads/2021/11/Eriantys_ITA_bassa.pdf";
    // server constants
    private static String address;
    private static int port;

    /**
     * Method getInfo returns the info of this Constants object.
     *
     * @return the info (type String) of this Constants object.
     */
    // datetime
    public static String getInfo() {
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " INFO: ");
    }
    /**
     * Method getErr returns the err of this Constants object.
     *
     * @return the err (type String) of this Constants object.
     */
    public static String getErr() {
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " ERR: ");
    }
    /**
     * Method setADDRESS sets the ADDRESS of this Constants object.
     *
     * @param address the ADDRESS of this Constants object.
     */
    public static void setAddress(String address) {
        Constant.address = address;
    }
    /**
     * Method setPORT sets the PORT of this Constants object.
     *
     * @param port the PORT of this Constants object.
     */
    public static void setPort(int port) {
        Constant.port = port;
    }

    /**
     * Method getADDRESS returns the ADDRESS of this Constants object.
     *
     * @return the ADDRESS (type String) of this Constants object.
     */
    public static String getAddress() {
        return address;
    }

    /**
     * Method getPORT returns the PORT of this Constants object.
     *
     * @return the PORT (type int) of this Constants object.
     */
    public static int getPort() {
        return port;
    }


}
