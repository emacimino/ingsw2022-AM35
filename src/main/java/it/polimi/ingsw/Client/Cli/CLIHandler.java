package it.polimi.ingsw.Client.Cli;

import it.polimi.ingsw.Controller.TurnPhase;
import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Cloud;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.Model.Wizard.Board;
import it.polimi.ingsw.Model.Wizard.Wizard;
import it.polimi.ingsw.NetworkUtilities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Main CLI class
 */
public class CLIHandler {
    private final CLI cli;

    /**
     * Constructor of the class
     *
     * @param cli CLI type object
     */
    public CLIHandler(CLI cli) {
        this.cli = cli;
    }

    /**
     * This method converts user inputs into a message
     *
     * @param inputString User input string
     * @param turnPhase   Ongoing turn phase
     * @return the message ready to be sent
     */
    public Message convertInputToMessage(String inputString, TurnPhase turnPhase) {
        Message message;
        if (inputString.equals("CharacterCard") && !cli.getRemoteModel().getCharacterCardMap().isEmpty()) {
            return askCharacterCardInfoMessage();
        }


        switch (turnPhase) {
            case LOGIN -> message = createLoginMessage(inputString);
            case PLAY_ASSISTANT -> message = createAssistantCardMessage(inputString);
            case MOVE_STUDENTS -> message = createMoveStudentMessage(inputString);
            case MOVE_MOTHER_NATURE -> message = createMoveMotherNatureMessage(inputString);
            case CHOOSE_CLOUD -> message = createChooseCloudMessage(inputString);
            case PLAY_CHARACTER_CARD -> message = createCharacterMessage(inputString);
            default -> {
                System.out.println("It is not your turn, please be patient...");
                message = null;
            }
        }
        return message;
    }

    private Message askCharacterCardInfoMessage() {
        String nameCharacter;
        displayCharacterCardInGame();
        System.out.println();
        do {
            System.out.println("Select the character card you want to play, or input 'quit' to return to the game: ");
            nameCharacter = cli.scanner.nextLine(); //expected to have the Name of the character selected
            if (nameCharacter.equals("quit")) {
                return null;
            }
            if (cli.getRemoteModel().getCurrentBoard().getCoins() < cli.getRemoteModel().getCharacterCardMap().get(nameCharacter).getCost()){
                System.out.println("Could not play the card for lack of coins");
                return null;
            }
        } while (!cli.getRemoteModel().getCharacterCardMap().containsKey(nameCharacter));

        return new AskCharacterCardMessage(nameCharacter);
    }

    /**
     * This method calls all the sub-methods that print a message
     *
     * @param message message received
     */
/*    public void showMessage(Message message) {
        switch (message.getType()) {
            case REQUEST_LOGIN -> requestLogin();
            case ASK_ASSISTANT_CARD -> showAssistantsCardOption(((AskAssistantCardMessage) message).getAssistantsCards());
            case ASK_TO_MOVE_STUDENT -> showStudentsOnEntranceOption(((StudentsOnEntranceMessage) message).getStudents());
            case ASK_MOVE_MOTHER_NATURE -> askToMotherNature(((AskToMoveMotherNatureMessage) message).getMessage());
            case BOARD -> showBoard(((BoardMessage) message).getBoard());
            case ARCHIPELAGOS_IN_GAME -> showArchipelagos(message);
            case CLOUD_IN_GAME -> showClouds(message);
            case CHARACTER_CARD_IN_GAME -> showCharacterCardsInGame(message);
            case END_OF_TURN -> showEndOfTurnMessage(message);
            case YOUR_TURN -> showYourTurnMessage(message);
            case GENERIC_MESSAGE -> showGenericMessage((String) ((GenericMessage) message).getContent());
            case GAME_INFO -> showCurrentGame(message);
            case ERROR -> showErrorMessage(((ErrorMessage) message).getError());
            case CLIENT_UNREACHABLE -> showEndOfGameMessage(message);
            case SHOW_CHARACTER_CARD_INFO -> showInfoChosenCharacterCard();
            default -> System.out.println(message.getMessage());

        }
    }
*/

    /**
     * This method that prints the Login info
     */
    public void requestLogin() {
        System.out.println(Printable.bigTitle);
        System.out.println("Please, proceed with the login: ");
        System.out.println("Insert username, number of players you want in the match (from 2 to 4) and if you want to play as an expert: " +
                "\n" + "(for example: camilla,2,yes  )");
    }

    /**
     * This method that prints an error message
     *
     * @param error message that generated error
     */
    public void showErrorMessage(String error) {
        System.out.println(Constants.ANSI_RED + error + Constants.ANSI_RESET);
    }


    /**
     * This method that prints a generic message(if it doesn't generate error)
     *
     * @param message message printed
     */
    public void showGenericMessage(String message) {
        System.out.println(message);
    }

    /**
     * This method is called every time the player's turn ends
     *
     * @param message message printed
     */
    private void showEndOfTurnMessage(Message message) {
        EndTurnMessage endOfTurnMessage = (EndTurnMessage) message;
        System.out.println(endOfTurnMessage.getContent());
    }

    /**
     * This method is called every time the player's turn begins
     *
     * @param message message printed
     */
    private void showYourTurnMessage(Message message) {
        YourTurnMessage yourTurnMessage = (YourTurnMessage) message;
        System.out.println("\n" + yourTurnMessage.getContent());
    }

    public void showEndOfGameMessage(Message message) {
        EndOfGameMessage endOfGameMessage = (EndOfGameMessage) message;
        System.out.println(endOfGameMessage.content);
    }

    /**
     * This method is called during the player's turn to print an archipelago
     *
     * @param message message printed
     */
    private void showArchipelagos(Message message){
        ArchipelagoInGameMessage archipelagoListMessage = (ArchipelagoInGameMessage) message;
        cli.getRemoteModel().setArchipelagosMap(archipelagoListMessage.getArchipelago());
        for (int i : cli.getRemoteModel().getArchipelagosMap().keySet()) {
            System.out.print("\n" + i + ")  In this archipelago we have:\n");
            getInfoArchipelago(cli.getRemoteModel().getArchipelagosMap().get(i));
        }
    }

    private void displayArchipelagos() {
        for (int i : cli.getRemoteModel().getArchipelagosMap().keySet()) {
            System.out.print("\n" + i + ")  In this archipelago we have:\n");
            getInfoArchipelago(cli.getRemoteModel().getArchipelagosMap().get(i));
        }
    }

    /**
     * This method is called to print clouds
     *
     * @param message message printed
     */
    public void showClouds(Message message) {
        CloudInGame cloudInGame = (CloudInGame) message;
        cli.getRemoteModel().setCloudsMap(cloudInGame.getCloudMap());
        for (int i : cli.getRemoteModel().getCloudsMap().keySet()) {
            System.out.print("\n" + i + ")  In this cloud we have:\n");
            printCloud(cli.getRemoteModel().getCloudsMap().get(i));
        }
    }

    /**
     * This method is called to print the player's board
     *
     * @param boardMessage message printed
     */
    public void showBoard(Board boardMessage) {
        try {
            getInfoBoard(boardMessage);
        } catch (ExceptionGame e) {
            e.printStackTrace();
        }
        cli.getRemoteModel().setCurrentBoard(boardMessage);
    }

    /**
     * This method is called to print character cards
     *
     * @param message message printed
     */
    public void showCharacterCardsInGame(Message message) {
        CharacterCardInGameMessage characterCardInGameMessage = (CharacterCardInGameMessage) message;
        cli.getRemoteModel().setCharacterCardMap(characterCardInGameMessage.getCharacterCard());
        displayCharacterCardInGame();
    }

    /**
     * This method is called to print the current state of the match
     *
     * @param message message printed
     */
    public void showCurrentGame(Message message) {
        System.out.println("State of current match is :\n");
        Game game = ((CurrentGameMessage) message).getGame();
        try {
            displayCurrentGameInfoCli(game);
        } catch (ExceptionGame e) {
            e.printStackTrace();
        }

    }

    /**
     * This method calls 2 other method to print a board and the archipelagos
     *
     * @param game current game
     * @throws ExceptionGame if the board has fewer tables than it should have
     */
    private void displayCurrentGameInfoCli(Game game) throws ExceptionGame {
        currentBoardInfo(game);
        currentLandsInfo(game);
    }

    /**
     * This method is usd to print the archipelagos
     *
     * @param game current game
     */
    private void currentLandsInfo(Game game) {
        System.out.print("\n\n  ARCHIPELAGOS:  \n");
        for (Archipelago archipelago : game.getArchipelagos()) {
            getInfoArchipelago(archipelago);
        }
    }


    /**
     * This method is used to print the board belonging to each wizard
     *
     * @param game current game
     */
    private void currentBoardInfo(Game game) {
        for (Wizard wizard : game.getWizards()) {
            try {
                getInfoBoard(wizard.getBoard());
            } catch (ExceptionGame e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method prints a single Archipelago
     *
     * @param archipelago is the printed archipelago
     */
    private void getInfoArchipelago(Archipelago archipelago){
        Printable.printArchipelago(archipelago);
    }

    /**
     * This method is used to print a single board belonging to a wizard
     *
     * @param board board printed
     * @throws ExceptionGame if the board has fewer tables than it should have
     */
    private void getInfoBoard(Board board) throws ExceptionGame {
        List<Student> students = new ArrayList<>();
        System.out.println("\n\nTO THIS WIZARD BELONGS:  \n");
        if(!board.getTowersInBoard().isEmpty())
             Printable.printBoardTowers(board.getTowersInBoard().size(), board.getTowersInBoard().iterator().next().getTowerColors().toString());
        for (Color c : Color.values()) {
            students.addAll(board.getStudentsFromTable(c));
        }
        Printable.printBoardProfessorAndTables(board.getProfessorInTable(), students);
        Printable.printEntranceAndCoins(board.getStudentsInEntrance().stream().toList(), board.getCoins());
        System.out.print("\n");
    }


    /**
     * This method is used to print students
     *
     * @param student student printed
     */
    private void printStudent(Student student) {
        switch (student.getColor()) {
            case GREEN -> System.out.print(Printable.STUDENT_GREEN + "  ");
            case BLUE -> System.out.print(Printable.STUDENT_BLUE + "  ");
            case PINK -> System.out.print(Printable.STUDENT_PINK + "  ");
            case RED -> System.out.print(Printable.STUDENT_RED + "  ");
            case YELLOW -> System.out.print(Printable.STUDENT_YELLOW + "  ");
        }
    }


    /**
     * This method is used to print clouds
     *
     * @param cloud cloud printed
     */
    private void printCloud(Cloud cloud) {
        for (Student s : cloud.getStudentOnCloud())
            printStudent(s);
        System.out.println();
    }


    /**
     * This method prints the options available for assistant cards
     *
     * @param assistantsCardsInTurn containing assistant card information
     */
    public void showAssistantsCardOption(List<AssistantsCards> assistantsCardsInTurn) {
        System.out.println("Please select an Assistant Card from the option below: ");
        cli.getRemoteModel().getAssistantsCardsMap().clear();
        cli.getRemoteModel().setAssistantsCardsMap(assistantsCardsInTurn);
        for(AssistantsCards a : assistantsCardsInTurn)
             System.out.println(Printable.getAssistantCardCLI(a));
    }

    /**
     * This method is used to choose students from entrance
     *
     * @param studentMap containing map of students in entrance
     */
    public void showStudentsOnEntranceOption(Map<Integer, Student> studentMap) {
        showBoard(cli.getRemoteModel().getCurrentBoard());
        System.out.println("\nPlease select an Student from the option below: ");
        System.out.println("Indicate the student you cho ose with its number than after the comma choose the Archipelago you want to move the student" +
                "\n Write just the number if you want move the student on your board ");
        System.out.println("example: 1,2");
        cli.getRemoteModel().setStudentOnEntranceMap(studentMap);
        for (int s : cli.getRemoteModel().getStudentsOnEntranceMap().keySet()) {
            System.out.print(s + "->" + Printable.getStudentsCLI(cli.getRemoteModel().getStudentsOnEntranceMap().get(s)) + "  ");
        }

    }

    /**
     * This method prints the options for Mother Nature movements
     */
    public void askToMotherNature(String string) {
        displayArchipelagos();
        System.out.println(string);
        System.out.println("Insert the index of the Archipelago you want to move Mother Nature");
    }

    /**
     * This method creates a message containing an assistant card
     *
     * @param assistant the chosen assistant
     * @return a message containing an assistant
     */
    private Message createAssistantCardMessage(String assistant) {
        System.out.println("sono qui in create assistant card");
        assistant = assistant.toUpperCase();
        if (cli.getRemoteModel().getAssistantsCardsMap().containsKey(assistant)) {
            return new AssistantCardMessage(cli.getRemoteModel().getAssistantsCardsMap().get(assistant));
        } else {
            System.out.println("Please write a valid Assistant Card");
            return null;
        }
    }

    /**
     * This method creates a message containing login parameters
     * @param login is the input login
     * @return a correct login message
     */
    private Message createLoginMessage(String login) {
        LoginResponse message;
        String[] info = login.split(",");
        try {
            String username = info[0];
            int numberOfPlayers = Integer.parseInt(info[1]);
            if (numberOfPlayers < 2 || numberOfPlayers > 4) {
                System.out.println("Error in the input, please re-insert information regarding the number of players re-correctly");
                return null;
            }
            boolean isExpert;
            if (info[2].equals("yes"))
                isExpert = true;
            else if (info[2].equals("no"))
                isExpert = false;
            else {
                System.out.println("Error in the input, please re-insert information regarding the expert field re-correctly");
                return null;
            }
            message = new LoginResponse(username, numberOfPlayers, isExpert);
            return message;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Please re-insert the information as the example");
            return null;
        }
    }

    /**
     * This method creates a message containing a student
     * @param student is the index of the student to be moved
     * @return moveStudentMessage
     */
    private Message createMoveStudentMessage(String student) {
        Message message = null;
        try {
            if (!student.contains(",")) {
                Integer indexStud = Integer.parseInt(student);
                if (cli.getRemoteModel().getStudentsOnEntranceMap().containsKey(indexStud))
                    message = new MoveStudentMessage(indexStud, null);
                else {
                    System.out.println("Please write a valid Student");
                    return null;
                }
            } else {
                String[] info = student.split(",");
                String stud = info[0];
                String arch = info[1];
                Integer indexStud = Integer.parseInt(stud);
                Integer indexArch = Integer.parseInt(arch);
                if (cli.getRemoteModel().getStudentsOnEntranceMap().containsKey(indexStud) && cli.getRemoteModel().getArchipelagosMap().containsKey(indexArch)) {
                    return new MoveStudentMessage(indexStud, indexArch);
                } else {
                    System.out.println("Please write a valid Student");
                    return null;
                }
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException n) {
            System.out.println("Please write a valid number");
        }
        return message;
    }

    /**
     * This method is used to create Mother Nature's movement messages
     *
     * @param archipelago archipelago from which you want to move mother nature
     * @return a message containing the new mother nature's position
     */
    private Message createMoveMotherNatureMessage(String archipelago) {
        System.out.println("IN CREATE MOVE MOTHER NATURE IN CLIHANDLER");
        Message message = null;
        try {
            Integer indexArch = Integer.parseInt(archipelago);
            if (cli.getRemoteModel().getArchipelagosMap().containsKey(indexArch)) {
                message = new MoveMotherNatureMessage(indexArch);
                System.out.println(message);
            } else
                System.out.println("Please write a valid index of Archipelago");
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException n) {
            System.out.println("Please write a valid number");
        }
        return message;
    }

    /**
     * This method creates a message containing the chosen cloud
      * @param cloud is the index of the chosen cloud
     * @return cloudMessage
     */
    private Message createChooseCloudMessage(String cloud) {
        System.out.println("IN CREATE CHOOSE CLOUD  IN CLIHANDLER");
        Message message = null;
        try {
            int indexCloud = Integer.parseInt(cloud);
            if (cli.getRemoteModel().getCloudsMap().containsKey(indexCloud)) {
                message = new CloudMessage(indexCloud);
            } else
                System.out.println("Please write a valid index of Archipelago");
        } catch (NumberFormatException n) {
            System.out.println("Please write a valid number");
        }

        return message;
    }

    /**
     * This method prints the character cards chosen for this game
     */
    private void displayCharacterCardInGame() {
        System.out.println("Character Card available: \n");
        int i = 1;
        for (String s : cli.getRemoteModel().getCharacterCardMap().keySet()) {
            System.out.print("\n" + i + ")" + cli.getRemoteModel().getCharacterCardMap().get(s));
            i++;
        }
    }

    /**
     * This method prints the info for all the character cards chosen for this game
     *
     */
    public void showInfoChosenCharacterCard() {
        System.out.println("Archipelagos: ");
        for (Integer integer : cli.getRemoteModel().getArchipelagosMap().keySet()) {
            System.out.println(integer + ") ");
            Printable.printArchipelago(cli.getRemoteModel().getArchipelagosMap().get(integer));
        }
        System.out.println("Students in Entrance: ");
        for (Integer integer : cli.getRemoteModel().getStudentsOnEntranceMap().keySet()) {
            System.out.println(integer + ") " + cli.getRemoteModel().getStudentsOnEntranceMap().get(integer));
        }
        System.out.println("Students on the Card: ");
        for (Integer integer : cli.getRemoteModel().getStudentsOnCardMap().keySet()) {
            System.out.println(integer + ") " + cli.getRemoteModel().getStudentsOnCardMap().get(integer));
        }
        askToChoose();
    }

    /**
     * This method prints the info requested for the character cards chosen for this turn
     */
    private void askToChoose() {
        String nameCharacter = cli.getRemoteModel().getActiveCharacterCard();
        switch (nameCharacter) {
            case "Princess" -> System.out.println("Please write a valid Student by his index: ");

            case "Jester" -> {
                System.out.println("You can choose FIRST from those student on Jester card by their index: write them separated by ','");
                System.out.println("Write a -");
                System.out.println("You can choose THEN three students from entrance by their index: write them separated by ','");
            }
            case "Friar" -> {
                System.out.println("Choose FIRST an archipelago by his index");
                System.out.println("Write a -");
                System.out.println("Choose THEN one student from Friar card by his index");
            }
            case "Minstrel" -> {
                System.out.println("You can choose FIRST two student on your Tables by their color: write them separated by ','");
                System.out.println("Write a -");
                System.out.println("You can choose THEN two students from entrance by their index: write them separated by ','");

            }
            case "Messenger", "Herbalist" -> System.out.println("Choose an Archipelago where use the card effect");
            case "Chef", "Banker" ->System.out.println("Choose a Color to use the card effect");
            default -> System.out.println("For this card you don't need nothing! press ENTER to continue");
        }

    }

    /**
     * This method create the info to use the character card chosen for this turn
      * @param input is the userInput with all the information needed
     * @return characterCardMessage with all the information needed
     */
    private Message createCharacterMessage(String input) {
        String nameCharacter = cli.getRemoteModel().getActiveCharacterCard();
        int notValidArchipelago = 13;
        boolean inputInvalid = true;
        while (inputInvalid) {
            try {
                switch (nameCharacter) {
                    case "Chef", "Banker" -> {
                        List<Color> color = new ArrayList<>();
                        color.add(getColor(input));
                        return new PlayCharacterMessage(nameCharacter, notValidArchipelago, null, null,  color);
                    }
                    case "Messenger", "Herbalist" -> {
                        int indexOfArchipelago = Integer.parseInt(input);
                        return new PlayCharacterMessage(nameCharacter, indexOfArchipelago, null, null, null);
                    }
                    case "Baker", "Magician", "Knight", "Archer" -> {
                        return new PlayCharacterMessage(nameCharacter, notValidArchipelago, null, null, null);
                    }
                    case "Princess", "Friar" -> {
                        List<Integer> toTradeFromCard = new ArrayList<>();
                        int indexOfArchipelago = notValidArchipelago;
                        int indexStud;
                        if(input.contains("-")) {
                            String[] info = input.split("-");
                            indexOfArchipelago = Integer.parseInt(info[0]);
                            indexStud = Integer.parseInt(info[1]);
                        }else{
                            indexStud = Integer.parseInt(input);
                        }
                        toTradeFromCard.add(indexStud);
                        return new PlayCharacterMessage(nameCharacter, indexOfArchipelago, null, toTradeFromCard, null);
                    }
                    case "Jester" -> {
                        List<Integer> toTradeFromEntrance = new ArrayList<>();
                        List<Integer> toTradeFromCard = new ArrayList<>();

                        String[] tradeFromEntrance;
                        String[] tradeFromCard;
                        String[] info = input.split("-");

                        if(info[0].contains(",")) {
                            tradeFromEntrance = info[0].split(",");
                        }else
                            tradeFromEntrance = new String[]{info[0]};

                        if(info[0].contains(",")) {
                            tradeFromCard = info[1].split(",");
                        }else
                            tradeFromCard = new String[]{info[1]};

                        for (String s : tradeFromEntrance) {
                            int stud = Integer.parseInt(s);
                            toTradeFromEntrance.add(stud);
                        }

                        for (String s : tradeFromCard) {
                            int stud1 = Integer.parseInt(s);
                            toTradeFromCard.add(stud1);
                        }
                        return new PlayCharacterMessage(nameCharacter, notValidArchipelago, toTradeFromCard, toTradeFromEntrance, null);
                    }
                    case "Minstrel" -> {
                        Color tmpColor;
                        List<Color> colorsOfTable = new ArrayList<>();
                        List<Integer> toTradeFromEntrance = new ArrayList<>();
                        String[] info = input.split("-");

                        String[] tradeFromTables;
                        if(info[0].contains(",")) {
                            tradeFromTables = info[0].split(",");
                        }else
                            tradeFromTables = new String[]{info[0]};

                        String[] tradeFromEntrance;
                        if(info[0].contains(",")) {
                             tradeFromEntrance = info[1].split(",");
                        }else
                            tradeFromEntrance = new String[]{info[1]};

                        for (String s : tradeFromTables) {
                            tmpColor = getColor(s);
                            colorsOfTable.add(tmpColor);
                        }
                        for (String s : tradeFromEntrance) {
                            int stud1 = Integer.parseInt(s);
                            toTradeFromEntrance.add(stud1);
                        }
                        return new PlayCharacterMessage(nameCharacter, notValidArchipelago, toTradeFromEntrance, null,  colorsOfTable);
                    }

                }
                inputInvalid = false;
            } catch (Exception e) {
                e.printStackTrace();
                input = cli.scanner.nextLine();
            }
        }

        return null;
    }

    /**
     * This method choose the right color from the user input
     * @param color is the color inserted by the user
     * @return the color chosen in Color type
     * @throws Exception if this color does not exist in the Game
     */
    private Color getColor(String color) throws Exception {
        Color tmpColor;
        switch (color) {
            case "blue" -> tmpColor = Color.BLUE;
            case "green" -> tmpColor = Color.GREEN;
            case "pink" -> tmpColor = Color.PINK;
            case "yellow" -> tmpColor = Color.YELLOW;
            case "red" -> tmpColor = Color.RED;
            default -> throw new Exception();
        }
        return tmpColor;
    }


}
