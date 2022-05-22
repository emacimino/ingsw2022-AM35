package it.polimi.ingsw.Client;

import it.polimi.ingsw.Controller.TurnPhase;
import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Island;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Professor;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.Model.Wizard.Board;
import it.polimi.ingsw.Model.Wizard.Wizard;
import it.polimi.ingsw.NetworkUtilities.Message.*;

import java.util.Collection;
import java.util.HashMap;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import java.util.List;
import java.util.Map;

public class CLIHandler {
    private List<AssistantsCards> assistantsCardsInTurn;
    private Map<String, AssistantsCards> assistantsCardsMap = new HashMap<>();
    private Map<Integer, Student> studentsMap = new HashMap<>();
    private Map<Integer, Archipelago> archipelagosMap = new HashMap<>();



    public Message convertInputToMessage(String inputString, TurnPhase turnPhase){
        Message message;
        switch (turnPhase) {
            case LOGIN -> message = createLoginMessage(inputString);
            case PLAY_ASSISTANT -> message = createAssistantCardMessage(inputString);
            case MOVE_STUDENTS -> message = createMoveStudentMessage(inputString);
            case MOVE_MOTHERNATURE -> message = createMoveMotherNatureMessage(inputString);
            default -> {
                System.out.println("MAI QUI IN teoria");
                message = null;}
        }
        System.out.println(message);
        return message;
    }

    public void showMessage(Message message) {
        String show;
        switch (message.getType()) {
            case REQUEST_LOGIN -> requestLogin();
            case ASK_ASSISTANT_CARD -> showAssistantsCardOption(message);
            case STUDENTS_ON_ENTRANCE -> showStudentsOption(message);
            case ASK_MOVE_MOTHER_NATURE -> showMotherNatureOption(message);
            case BOARD -> showBoard(message);
            case ARCHIPELAGOS_IN_GAME -> showArchipelagos(message);
            case CHOOSE_CLOUD -> show = "Please choose a cloud";
            case END_OF_TURN -> showEndOfTurnMessage(message);
            case YOUR_TURN -> showYourTurnMessage(message);
            case GENERIC_MESSAGE -> showGenericMessage(message);
            case GAME_INFO -> showCurrentGame(message);
            case ERROR -> showErrorMessage(message);
            default -> System.out.println(message);

        }

    }

    private void showErrorMessage(Message message) {
        ErrorMessage errorMessage = (ErrorMessage) message;
        System.out.println(Constants.ANSI_RED + errorMessage.getError() + Constants.ANSI_RESET);
    }

    private void showCurrentGame(Message message) {
        System.out.println("State of current match is :\n");
        Game game = ((CurrentGameMessage) message).getGame();
        displayCurrentGameInfoCli(game);

    }

    private void displayCurrentGameInfoCli(Game game) {
        currentBoardInfo(game);
        currentLandsInfo(game);
    }

    private void currentLandsInfo(Game game) {
        for (Archipelago archipelago : game.getArchipelagos()) {
            getInfoArchipelago(archipelago);
        }
    }

    private void getInfoArchipelago(Archipelago archipelago) {
        int numTowers = 0;
        for(Student student: archipelago.getStudentFromArchipelago()){
            System.out.print(Printable.getStudentsCLI(student) + "  ");
        }
        for(Island island: archipelago.getIsle()) {
            if (island.isThereTower())
                numTowers++;
        }
        if(numTowers>0) {
            try {
                System.out.println(numTowers + "Towers of " + archipelago.getIsle().get(0).getTower().getProperty()+ " present in this Archipelagos\n");
            } catch (ExceptionGame e) {
                e.printStackTrace();
            }
        }

        if(archipelago.isMotherNaturePresence()){
            System.out.println("In this archipelago we have motherNature " );
        }else
            System.out.println("NO MOTHER NATURE" );
        if(archipelago.isProhibition()){
            System.out.println("In this archipelago a prohibition is present " );
        }
    }
    private void getInfoBoard(Board board){
        System.out.println("\n\nTO THIS BELONGS:  ");
        System.out.println("STUDENT in entrance:  ");
        printStudents(board.getStudentsInEntrance());
        printStudentInTables(board);
        System.out.println("\nPROFESSOR in board: \n ");
        printProfessors(board.getProfessorInTable());
    }
    private void currentBoardInfo(Game game) {
        for (Wizard wizard : game.getWizards()) {
            getInfoBoard(wizard.getBoard());
        }
    }

    private void printStudentInArchipelagoForEveryWizard(Wizard wizard) {
        for (Archipelago archipelago : wizard.getArchipelagosOfWizard()) {
            for (Student student : archipelago.getStudentFromArchipelago())
                printStudent(student);
        }
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
    }
    public void printStudent(Student student) {
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
    public void printProfessor(Professor professor) {
        switch (professor.getColor()) {
            case GREEN -> System.out.println(Printable.PROF_GREEN + "\n");
            case BLUE -> System.out.println(Printable.PROF_BLUE + "\n");
            case PINK -> System.out.println(Printable.PROF_PINK + "\n");
            case RED -> System.out.println(Printable.PROF_RED + "\n");
            case YELLOW -> System.out.println(Printable.PROF_YELLOW + "\n");
        }
    }


    private void showAssistantsCardOption(Message message) {
        System.out.println("Please select an Assistant Card from the option below: ");
        List<AssistantsCards> assistantsCardsInTurn = ((AskAssistantCardMessage)message).getAssistantsCards();
        for(AssistantsCards a : assistantsCardsInTurn){
            assistantsCardsMap.put(Constants.getAssistantCardCLI(a), a);
            System.out.println(Printable.getAssistantCardCLI(a));
        }
    }
    private void showStudentsOption(Message message){
        System.out.println("\nPlease select an Student from the option below: ");
        System.out.println("Indicate the student you cho ose with its number than after the comma choose the Archipelago you want to move the student" +
                "\n Write just the number if you want move the student on your board ");
        System.out.println("example: 1,2");
        StudentsOnEntranceMessage studentsCollectionMessage = (StudentsOnEntranceMessage) message;
        setStudentMap(studentsCollectionMessage.getStudents());
        for(int s : studentsMap.keySet()) {
            System.out.print(s + "->" + Printable.getStudentsCLI(studentsMap.get(s)) + "  ");
        }

    }
    private void showMotherNatureOption(Message message) {
        AskToMoveMotherNatureMessage askToMoveMotherNatureMessage = (AskToMoveMotherNatureMessage) message;
        System.out.println(askToMoveMotherNatureMessage.getMessage());
        System.out.println("Insert the index of the Archipelago you want to move Mother Nature");
    }
    private void showArchipelagos(Message message){
        ArchipelagoInGameMessage archipelagoListMessage = (ArchipelagoInGameMessage) message;
        setArchipelagosMap(archipelagoListMessage.getArchipelago());
        for(int i : archipelagosMap.keySet()) {
            System.out.print("\n"+i  + ")  In this archipelago we have:\n");
            getInfoArchipelago(archipelagosMap.get(i));
        }
    }
    private void showBoard(Message message){
        BoardMessage boardMessage = (BoardMessage) message;
        getInfoBoard(boardMessage.getBoard());
    }
    private void showGenericMessage(Message message){
        GenericMessage genericMessage = (GenericMessage) message;
        System.out.println(genericMessage.getContent());
    }
    private void showEndOfTurnMessage(Message message){
        EndTurnMessage endOfTurnMessage = (EndTurnMessage) message;
        System.out.println(endOfTurnMessage.getContent());
    }
    private void showYourTurnMessage(Message message){
        YourTurnMessage yourTurnMessage = (YourTurnMessage) message;
        System.out.println("\n"+yourTurnMessage.getContent());
    }
    private void requestLogin(){
        System.out.println("Please, procede with the login: ");
        System.out.println("Insert username, number of players you want in the match (from 2 to 4) and if you want to play as an expert: " +
                "\n" + "(for example: camilla,2,yes  )");
    }


    private Message createAssistantCardMessage(String assistant){
        assistant = assistant.toUpperCase();
        if(assistantsCardsMap.containsKey(assistant)) {
            return new AssistantCardMessage(assistantsCardsMap.get(assistant));
        }else {
            System.out.println("Please write a valid Assistant Card");
            return null;
        }
    }
    private Message createLoginMessage(String login){
        LoginResponse message;
        String info[] = login.split(",");
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
    private Message createMoveStudentMessage(String student){ //NON VA BENE DEVO RIVEDERE COME SEGNARE GLI ARCHIPELAGI
        Message message = null;
        if(!student.contains(",")){
            Integer indexStud = Integer.parseInt(student);
            if (studentsMap.containsKey(indexStud))
                message = new MoveStudentMessage(indexStud, null);
            else
                System.out.println("Please write a valid Student");

        }else {
            String info[] = student.split(",");
            String stud = info[0];
            String arch = info[1];
            Integer indexStud = Integer.parseInt(stud);
            Integer indexArch = Integer.parseInt(arch);
            if (studentsMap.containsKey(indexStud) && archipelagosMap.containsKey(indexArch)) {
                return new MoveStudentMessage(indexStud, indexArch);
            } else {
                System.out.println("Please write a valid Student");
            }
        }
        studentsMap.clear();
        archipelagosMap.clear();
        return message;
    }
    private Message createMoveMotherNatureMessage(String archipelago){
        Message message= null;
        Integer indexArch = Integer.parseInt(archipelago);
        System.out.println(indexArch);
        if (archipelagosMap.containsKey(indexArch)) {
            message = new MoveMotherNatureMessage(indexArch);
            System.out.println(message);
        }
        else
            System.out.println("Please write a valid index of Archipelago");

        return message;
    }

    private void displayCharacterCard(Message message){
        System.out.println("Character Card available: \n");
        List<CharacterCard> characterCards = (((CharacterChardDisplayMessage) message).getCharacterCards());
        Printable.printCharacterCards(characterCards);
    }
    private void setStudentMap(Map<Integer, Student> map){
        studentsMap.clear();
        studentsMap.putAll(map);
    }
    private void setArchipelagosMap(Map<Integer, Archipelago> map){
        archipelagosMap.clear();
        archipelagosMap.putAll(map);
    }


}
