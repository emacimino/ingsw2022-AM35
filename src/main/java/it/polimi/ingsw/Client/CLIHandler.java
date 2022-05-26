package it.polimi.ingsw.Client;

import it.polimi.ingsw.Controller.TurnPhase;
import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Cloud;
import it.polimi.ingsw.Model.SchoolsLands.Island;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Professor;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.Model.Wizard.Board;
import it.polimi.ingsw.Model.Wizard.Tower;
import it.polimi.ingsw.Model.Wizard.Wizard;
import it.polimi.ingsw.NetworkUtilities.Message.*;

import java.util.*;

import it.polimi.ingsw.Model.FactoryMatch.Game;

/**
 * Main CLI class
 */
public class CLIHandler {
    private CLI cli;

    /**
     * Constructor of the class
     * @param cli CLI type object
     */
    public CLIHandler(CLI cli) {
        this.cli = cli;
    }

    /**
     * This method converts user inputs into a messsage
     * @param inputString User input string
     * @param turnPhase Ongoing turn phase
     * @return the message ready to be sent
     */
    public Message convertInputToMessage(String inputString, TurnPhase turnPhase){
        Message message;
        if(inputString.equals("CharacterCard")){
                //characterCardHandler();
        }
        switch (turnPhase) {
            case LOGIN -> message = createLoginMessage(inputString);
            case PLAY_ASSISTANT -> message = createAssistantCardMessage(inputString);
            case MOVE_STUDENTS -> message = createMoveStudentMessage(inputString);
            case MOVE_MOTHERNATURE -> message = createMoveMotherNatureMessage(inputString);
            case CHOOSE_CLOUD -> message = createChooseCloudMessage(inputString);
            default -> {
                System.out.println("It is not your turn, please be patient...");
                message = null;}
        }
        return message;
    }

    /**
     * This method calls all the sub-methods that print a message
     * @param message message received
     * @throws ExceptionGame if showBoard throws an exception
     */
    public void showMessage(Message message) throws ExceptionGame {
        switch (message.getType()) {
            case REQUEST_LOGIN -> requestLogin();
            case ASK_ASSISTANT_CARD -> showAssistantsCardOption(message);
            case STUDENTS_ON_ENTRANCE -> showStudentsOnEntranceOption(message);
            case ASK_MOVE_MOTHER_NATURE -> showMotherNatureOption(message);
            case BOARD -> showBoard(message);
            case ARCHIPELAGOS_IN_GAME -> showArchipelagos(message);
            case CLOUD_IN_GAME -> showClouds(message);
            case CHARACTER_CARD_IN_GAME -> showCharacterCardsInGame(message);
            case END_OF_TURN -> showEndOfTurnMessage(message);
            case YOUR_TURN -> showYourTurnMessage(message);
            case GENERIC_MESSAGE -> showGenericMessage(message);
            case GAME_INFO -> showCurrentGame(message);
            case ERROR -> showErrorMessage(message);
            case CLIENT_UNREACHABLE -> showEndOfGameMessage(message);
            default -> System.out.println(message);

        }
    }

    /**
     * This method that prints the Login info
     */
    private void requestLogin(){
        System.out.println("Please, procede with the login: ");
        System.out.println("Insert username, number of players you want in the match (from 2 to 4) and if you want to play as an expert: " +
                "\n" + "(for example: camilla,2,yes  )");
    }

    /**
     * This method that prints an error message
     * @param message message that generated error
     */
    private void showErrorMessage(Message message) {
        ErrorMessage errorMessage = (ErrorMessage) message;
        System.out.println(Constants.ANSI_RED + errorMessage.getError() + Constants.ANSI_RESET);
    }

    /**
     * This method that prints a generic message(if it doesn't generate error)
     * @param message message printed
     */
    private void showGenericMessage(Message message){
        GenericMessage genericMessage = (GenericMessage) message;
        System.out.println(genericMessage.getContent());
    }

    /**
     * This method is called every time the player's turn ends
     * @param message message printed
     */
    private void showEndOfTurnMessage(Message message){
        EndTurnMessage endOfTurnMessage = (EndTurnMessage) message;
        System.out.println(endOfTurnMessage.getContent());
    }

    /**
     * This method is called every time the player's turn begins
     * @param message message printed
     */
    private void showYourTurnMessage(Message message){
        YourTurnMessage yourTurnMessage = (YourTurnMessage) message;
        System.out.println("\n"+yourTurnMessage.getContent());
    }

    private void showEndOfGameMessage(Message message){
        EndOfGameMessage endOfGameMessage = (EndOfGameMessage) message;
            System.out.println(endOfGameMessage.content);
    }

    /**
     * This method is called during the player's turn to print an archipelago
     * @param message message printed
     */
    private void showArchipelagos(Message message){
        ArchipelagoInGameMessage archipelagoListMessage = (ArchipelagoInGameMessage) message;
        cli.getRemoteModel().setArchipelagosMap(archipelagoListMessage.getArchipelago());
        for(int i : cli.getRemoteModel().getArchipelagosMap().keySet()) {
            System.out.print("\n"+i  + ")  In this archipelago we have:\n");
            getInfoArchipelago(cli.getRemoteModel().getArchipelagosMap().get(i));
        }
    }

    /**
     * This method is called to print clouds
     * @param message message printed
     */
    private void showClouds(Message message){
        CloudInGame cloudInGame = (CloudInGame) message;
        cli.getRemoteModel().setCloudsMap(cloudInGame.getCloudMap());
        for(int i : cli.getRemoteModel().getCloudsMap().keySet()) {
            System.out.print("\n"+i  + ")  In this cloud we have:\n");
            printCloud(cli.getRemoteModel().getCloudsMap().get(i));
        }
    }

    /**
     * This method is called to print the player's board
     * @param message message printed
     */
    private void showBoard(Message message) throws ExceptionGame {
        BoardMessage boardMessage = (BoardMessage) message;
        getInfoBoard(boardMessage.getBoard());
    }

    /**
     * This method is called to print character cards
     * @param message message printed
     */
    private void showCharacterCardsInGame(Message message){
        CharacterCardInGameMessage characterCardInGameMessage  = (CharacterCardInGameMessage) message;
        cli.getRemoteModel().setCharacterCardMap(characterCardInGameMessage.getCharacterCard());
        displayCharacterCard();
    }

    /**
     * This method is called to print the current state of the match
     * @param message message printed
     */
    private void showCurrentGame(Message message) throws ExceptionGame {
        System.out.println("State of current match is :\n");
        Game game = ((CurrentGameMessage) message).getGame();
        displayCurrentGameInfoCli(game);
    }

    /**
     * This method calls 2 other method to print a board and the archipelagos
     * @param game current game
     * @throws ExceptionGame if the board has fewer tables than it should have
     */
    private void displayCurrentGameInfoCli(Game game) throws ExceptionGame {
        currentBoardInfo(game);
        currentLandsInfo(game);
    }

    /**
     * This method is usd to print the archipelagos
     * @param game current game
     */
    private void currentLandsInfo(Game game) {
        System.out.print("\n\n  ARCHIPELAGOS:  \n");
        for (Archipelago archipelago : game.getArchipelagos()) {
            System.out.print("Archipelago " + game.getArchipelagos().indexOf(archipelago));
            getInfoArchipelago(archipelago);
        }
    }

    /**
     * This method is used to print the board belonging to each wizard
     * @param game current game
     * @throws ExceptionGame if the board has fewer tables than it should have
     */
    private void currentBoardInfo(Game game) throws ExceptionGame {
        for (Wizard wizard : game.getWizards()) {
            getInfoBoard(wizard.getBoard());
        }
    }

    /**
     * This method prints a single Archipelago
     * @param archipelago
     */
    private void getInfoArchipelago(Archipelago archipelago) {
        Printable.printArchipelago(archipelago);
    }

    /**
     * This method is used to print a single board belonging to a wizard
     * @param board board printed
     * @throws ExceptionGame  if the board has fewer tables than it should have
     */
    private void getInfoBoard(Board board) throws ExceptionGame {
        List<Student> students = new ArrayList<Student>();
        System.out.println("\n\nTO THIS WIZARD BELONGS:  \n");
        Printable.printBoardTowers(board.getTowersInBoard().size(), board.getTowersInBoard().iterator().next().getTowerColors().toString());
        for (Color c:
             Color.values()) {
            students.addAll(board.getStudentsFromTable(c));
        }
        Printable.printBoardProfessorAndTables(board.getProfessorInTable(), students);
        Printable.printEntrance(board.getStudentsInEntrance().stream().toList());
        System.out.print("\n");
    }

    private void printStudentInTables(Board board) {
        for (Color color : Color.values()) {
            System.out.print("\nOn the table " +color + " -> ");
            try {
                for (Student student : board.getStudentsFromTable(color)) {
                    printStudent(student);
                }
            } catch (ExceptionGame e) {
                e.printStackTrace();
            }
        }
    }
    private void printStudents(Collection<Student> students) {
        for (Student student : students) {
            printStudent(student);
        }
        System.out.println();
    }

    /**
     * This method is used to print students
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
    private void printProfessors(Collection<Professor> professors) {
        for (Professor p : professors) {
            printProfessor(p);
        }
    }
    /**
     * This method is used to print professors
     * @param professor professor printed
     */
    private void printProfessor(Professor professor) {
        switch (professor.getColor()) {
            case GREEN -> System.out.println(Printable.PROF_GREEN + "\n");
            case BLUE -> System.out.println(Printable.PROF_BLUE + "\n");
            case PINK -> System.out.println(Printable.PROF_PINK + "\n");
            case RED -> System.out.println(Printable.PROF_RED + "\n");
            case YELLOW -> System.out.println(Printable.PROF_YELLOW + "\n");
        }
    }

    /**
     * This method is used to print clouds
     * @param cloud cloud printed
     */
    private void printCloud(Cloud cloud){
        for(Student s : cloud.getStudentOnCloud())
            printStudent(s);
        System.out.println();
    }
    private void printTowers(Collection<Tower> towers){
        System.out.println("There are " + towers.size() + " towers\n");
    }

    /**
     * This method prints the options available for assistant cards
     * @param message message containing assistant card information
     */
    private void showAssistantsCardOption(Message message) {
        System.out.println("Please select an Assistant Card from the option below: ");
        List<AssistantsCards> assistantsCardsInTurn = ((AskAssistantCardMessage)message).getAssistantsCards();
        for(AssistantsCards a : assistantsCardsInTurn){
            cli.getRemoteModel().getAssistantsCardsMap().put(Constants.getAssistantCardCLI(a), a);
            System.out.println(Printable.getAssistantCardCLI(a));
        }
    }

    /**
     * This method is used to choose students from entrance
     * @param message message containing students in entrance
     */
    private void showStudentsOnEntranceOption(Message message){
        System.out.println("\nPlease select an Student from the option below: ");
        System.out.println("Indicate the student you cho ose with its number than after the comma choose the Archipelago you want to move the student" +
                "\n Write just the number if you want move the student on your board ");
        System.out.println("example: 1,2");
        StudentsOnEntranceMessage studentsCollectionMessage = (StudentsOnEntranceMessage) message;
        cli.getRemoteModel().setStudentOnEntranceMap(studentsCollectionMessage.getStudents());
        for(int s : cli.getRemoteModel().getStudentsOnEntranceMap().keySet()) {
            System.out.print(s + "->" + Printable.getStudentsCLI(cli.getRemoteModel().getStudentsOnEntranceMap().get(s)) + "  ");
        }

    }

    /**
     * This method prints the options for Mother Nature movements
     * @param message mother nature position
     */
    private void showMotherNatureOption(Message message) {
        AskToMoveMotherNatureMessage askToMoveMotherNatureMessage = (AskToMoveMotherNatureMessage) message;
        System.out.println(askToMoveMotherNatureMessage.getMessage());
        System.out.println("Insert the index of the Archipelago you want to move Mother Nature");
    }

    /**
     * This method creates a message containing an assistant card
     * @param assistant the chosen assistant
     * @return a message containing an assistant
     */
    private Message createAssistantCardMessage(String assistant){
        assistant = assistant.toUpperCase();
        if(cli.getRemoteModel().getAssistantsCardsMap().containsKey(assistant)) {
            return new AssistantCardMessage(cli.getRemoteModel().getAssistantsCardsMap().get(assistant));
        }else {
            System.out.println("Please write a valid Assistant Card");
            return null;
        }
    }

    /**
     * This method crates a Login message
     * @param login login information
     * @return a Login Response message
     */
    private Message createLoginMessage(String login){
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
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Please re-insert the information as the example");
            return null;
        }
    }

    /**
     * This method is used to create student's movement messages
     * @param student student to move
     * @return a message containing the new position for the student
     */
    private Message createMoveStudentMessage(String student){ //NON VA BENE DEVO RIVEDERE COME SEGNARE GLI ARCHIPELAGI
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
        }catch (NumberFormatException n){
            System.out.println("Please write a valid number");
        }
        return message;
    }

    /**
     * This method is used to create Mother Nature's movement messages
     * @param archipelago archipelago from which you want to move mother nature
     * @return a message containing the new mother nature's position
     */
    private Message createMoveMotherNatureMessage(String archipelago){
        Message message= null;
        try {
            Integer indexArch = Integer.parseInt(archipelago);
            if (cli.getRemoteModel().getArchipelagosMap().containsKey(indexArch)) {
                message = new MoveMotherNatureMessage(indexArch);
                System.out.println(message);
            }
            else
                System.out.println("Please write a valid index of Archipelago");
        }catch (NumberFormatException n){
            System.out.println("Please write a valid number");
        }
        return message;
    }

    /**
     * This method creates messages for the choice of clouds
     * @param cloud Chosen cloud
     * @return a message containing chosen cloud
     */
    private Message createChooseCloudMessage(String cloud){
        Message message= null;
        try {
            int indexCloud = Integer.parseInt(cloud);
            if (cli.getRemoteModel().getCloudsMap().containsKey(indexCloud)) {
                message = new CloudMessage(indexCloud);
            } else
                System.out.println("Please write a valid index of Archipelago");
        }catch (NumberFormatException n){
            System.out.println("Please write a valid number");
        }

        return message;
    }

    /**
     * This method prints the character cards
     */
    private void displayCharacterCard(){
        System.out.println("Character Card available: \n");
        int i = 1;
        for(String s : cli.getRemoteModel().getCharacterCardMap().keySet()) {
            System.out.print("\n"+i  + ")" +  cli.getRemoteModel().getCharacterCardMap().get(s));
        }
    }

    /**
     * This method helps the print of character cards
     */
    private void characterCardHandler(){
        //PRIMA DI ARRIVARE A QUESTO PUNTO VOGLIO CHE LE MAPPE SIANO STATE SETTATE/AGGIORNATE
        Scanner scanner = cli.scanner;
        System.out.println("Select the character card you want to play: ");
        displayCharacterCard();
        String nameCharacter = scanner.nextLine(); //expected to have the Name of the character selected
        //MOSTRI GLI ARCHIPELAGHI DELLA MAPPA CON GLI INDICI

        //MOSTRERAI GLI STUDENTI SULL CARTA
        //MOSTRERAI STUDENTI SULL ENTRATA

        //STUDENTI IN SALA/BOARD
        //CHIEDERE IL COLORE




    }


}
