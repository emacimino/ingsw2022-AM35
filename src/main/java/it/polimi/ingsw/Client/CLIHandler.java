package it.polimi.ingsw.Client;

import it.polimi.ingsw.Controller.TurnPhase;
import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Cloud;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Professor;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.Model.Wizard.Board;
import it.polimi.ingsw.Model.Wizard.Tower;
import it.polimi.ingsw.Model.Wizard.Wizard;
import it.polimi.ingsw.NetworkUtilities.Message.*;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.*;

import java.util.*;

import it.polimi.ingsw.Model.FactoryMatch.Game;


public class CLIHandler {
    private final CLI cli;

    public CLIHandler(CLI cli) {
        this.cli = cli;
    }


    public Message convertInputToMessage(String inputString, TurnPhase turnPhase) {
        Message message;
        if (inputString.equals("CharacterCard")) {

            message = characterCardHandler();
            return message;
        }
        switch (turnPhase) {
            case LOGIN -> message = createLoginMessage(inputString);
            case PLAY_ASSISTANT -> message = createAssistantCardMessage(inputString);
            case MOVE_STUDENTS -> message = createMoveStudentMessage(inputString);
            case MOVE_MOTHERNATURE -> message = createMoveMotherNatureMessage(inputString);
            case CHOOSE_CLOUD -> message = createChooseCloudMessage(inputString);
            default -> {
                System.out.println("It is not your turn, please be patient...");
                message = null;
            }
        }
        return message;
    }


    private Message createPongMessage() {
        return new Pong();
    }

    public void showMessage(Message message) {
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
            default -> System.out.println(message);

        }
    }


    private void requestLogin() {
        System.out.println(Printable.bigTitle);
        System.out.println("Please, proceed with the login: ");
        System.out.println("Insert username, number of players you want in the match (from 2 to 4) and if you want to play as an expert: " +
                "\n" + "(for example: camilla,2,yes  )");
    }

    private void showErrorMessage(Message message) {
        ErrorMessage errorMessage = (ErrorMessage) message;
        System.out.println(Constants.ANSI_RED + errorMessage.getError() + Constants.ANSI_RESET);
    }

    private void showGenericMessage(Message message) {
        GenericMessage genericMessage = (GenericMessage) message;
        System.out.println(genericMessage.getContent());
    }

    private void showEndOfTurnMessage(Message message) {
        EndTurnMessage endOfTurnMessage = (EndTurnMessage) message;
        System.out.println(endOfTurnMessage.getContent());
    }

    private void showYourTurnMessage(Message message) {
        YourTurnMessage yourTurnMessage = (YourTurnMessage) message;
        System.out.println("\n" + yourTurnMessage.getContent());
    }

    private void showArchipelagos(Message message) {
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

    private void showClouds(Message message) {
        CloudInGame cloudInGame = (CloudInGame) message;
        cli.getRemoteModel().setCloudsMap(cloudInGame.getCloudMap());
        for (int i : cli.getRemoteModel().getCloudsMap().keySet()) {
            System.out.print("\n" + i + ")  In this cloud we have:\n");
            printCloud(cli.getRemoteModel().getCloudsMap().get(i));
        }
    }

    private void showBoard(Message message) {
        BoardMessage boardMessage = (BoardMessage) message;
        try {
            getInfoBoard(boardMessage.getBoard());
        } catch (ExceptionGame e) {
            e.printStackTrace();
        }
    }

    private void showCharacterCardsInGame(Message message) {
        CharacterCardInGameMessage characterCardInGameMessage = (CharacterCardInGameMessage) message;
        cli.getRemoteModel().setCharacterCardMap(characterCardInGameMessage.getCharacterCard());
        displayCharacterCardInGame();
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
        System.out.print("\n\n  ARCHIPELAGOS:  \n");
        for (Archipelago archipelago : game.getArchipelagos()) {
            getInfoArchipelago(archipelago);
        }
    }

    private void currentBoardInfo(Game game) {
        for (Wizard wizard : game.getWizards()) {
            try {
                getInfoBoard(wizard.getBoard());
            } catch (ExceptionGame e) {
                e.printStackTrace();
            }
        }
    }

    private void getInfoArchipelago(Archipelago archipelago) {
        Printable.printArchipelago(archipelago);
    }

    private void getInfoBoard(Board board) throws ExceptionGame {
        List<Student> students = new ArrayList<>();
        System.out.println("\n\nTO THIS WIZARD BELONGS:  \n");
        Printable.printBoardTowers(board.getTowersInBoard().size(), board.getTowersInBoard().iterator().next().getTowerColors().toString());
        for (Color c :
                Color.values()) {
            students.addAll(board.getStudentsFromTable(c));
        }
        Printable.printBoardProfessorAndTables(board.getProfessorInTable(), students);
        Printable.printEntrance(board.getStudentsInEntrance().stream().toList());
        System.out.print("\n");
    }


    private void printStudentInTables(Board board) {
        for (Color color : Color.values()) {
            System.out.print("\nOn the table " + color + " -> ");
            try {
                for (Student student : board.getStudentsFromTable(color)) {
                    printStudent(student);
                }
            } catch (ExceptionGame e) {
                e.printStackTrace();
            }
        }
    }

    /*private void printStudents(Collection<Student> students) {
        for (Student student : students) {
            printStudent(student);
        }
        System.out.println();
    }
    */
    private void printProfessors(Collection<Professor> professors) {
        for (Professor p : professors) {
            printProfessor(p);
        }
    }

    private void printProfessor(Professor professor) {
        switch (professor.getColor()) {
            case GREEN -> System.out.println(Printable.PROF_GREEN + "\n");
            case BLUE -> System.out.println(Printable.PROF_BLUE + "\n");
            case PINK -> System.out.println(Printable.PROF_PINK + "\n");
            case RED -> System.out.println(Printable.PROF_RED + "\n");
            case YELLOW -> System.out.println(Printable.PROF_YELLOW + "\n");
        }
    }

    private void printCloud(Cloud cloud) {
        for (Student s : cloud.getStudentOnCloud())
            printStudent(s);
        System.out.println();
    }

    private void printTowers(Collection<Tower> towers) {
        System.out.println("There are " + towers.size() + " towers\n");
    }


    private void showAssistantsCardOption(Message message) {
        System.out.println("Please select an Assistant Card from the option below: ");
        List<AssistantsCards> assistantsCardsInTurn = ((AskAssistantCardMessage) message).getAssistantsCards();
        for (AssistantsCards a : assistantsCardsInTurn) {
            cli.getRemoteModel().getAssistantsCardsMap().put(Constants.getAssistantCardCLI(a), a);
            System.out.println(Printable.getAssistantCardCLI(a));
        }
    }

    private void showStudentsOnEntranceOption(Message message) {
        System.out.println("\nPlease select an Student from the option below: ");
        System.out.println("Indicate the student you cho ose with its number than after the comma choose the Archipelago you want to move the student" +
                "\n Write just the number if you want move the student on your board ");
        System.out.println("example: 1,2");
        StudentsOnEntranceMessage studentsCollectionMessage = (StudentsOnEntranceMessage) message;
        cli.getRemoteModel().setStudentOnEntranceMap(studentsCollectionMessage.getStudents());
        for (int s : cli.getRemoteModel().getStudentsOnEntranceMap().keySet()) {
            System.out.print(s + "->" + Printable.getStudentsCLI(cli.getRemoteModel().getStudentsOnEntranceMap().get(s)) + "  ");
        }

    }

    private void showMotherNatureOption(Message message) {
        AskToMoveMotherNatureMessage askToMoveMotherNatureMessage = (AskToMoveMotherNatureMessage) message;
        System.out.println(askToMoveMotherNatureMessage.getMessage());
        System.out.println("Insert the index of the Archipelago you want to move Mother Nature");
    }


    private Message createAssistantCardMessage(String assistant) {
        assistant = assistant.toUpperCase();
        if (cli.getRemoteModel().getAssistantsCardsMap().containsKey(assistant)) {
            return new AssistantCardMessage(cli.getRemoteModel().getAssistantsCardsMap().get(assistant));
        } else {
            System.out.println("Please write a valid Assistant Card");
            return null;
        }
    }

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

    private Message createMoveStudentMessage(String student) { //NON VA BENE DEVO RIVEDERE COME SEGNARE GLI ARCHIPELAGI
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
        } catch (NumberFormatException n) {
            System.out.println("Please write a valid number");
        }
        return message;
    }

    /*private Message createMoveStudentMessageFromCharacterCard(String characterCardName,String student){ //NON VA BENE DEVO RIVEDERE COME SEGNARE GLI ARCHIPELAGI
        Message message = null;
        //to be added -> getIndexOfStudentInCard to find The student in card
        try {
            if (!student.contains(",")) {
                Integer indexStud = Integer.parseInt(student);
                if (cli.getRemoteModel().getCharacterCardMap().get(characterCardName))
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

     */
    private Message createMoveMotherNatureMessage(String archipelago) {
        Message message = null;
        try {
            Integer indexArch = Integer.parseInt(archipelago);
            if (cli.getRemoteModel().getArchipelagosMap().containsKey(indexArch)) {
                message = new MoveMotherNatureMessage(indexArch);
                System.out.println(message);
            } else
                System.out.println("Please write a valid index of Archipelago");
        } catch (NumberFormatException n) {
            System.out.println("Please write a valid number");
        }
        return message;
    }

    private Message createChooseCloudMessage(String cloud) {
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

    private void displayCharacterCardInGame() {
        System.out.println("Character Card available: \n");
        int i = 1;
        for (String s : cli.getRemoteModel().getCharacterCardMap().keySet()) {
            System.out.print("\n" + i + ")" + cli.getRemoteModel().getCharacterCardMap().get(s));
        }
    }

    private Message characterCardHandler() {
        Message message;
        String nameCharacter;
        //Map already settled
        Scanner scanner = cli.scanner;
        displayCharacterCardInGame();
        do{
            System.out.println("Select the character card you want to play: ");
            nameCharacter = scanner.nextLine(); //expected to have the Name of the character selected
        } while (cli.getRemoteModel().getCharacterCardMap().containsKey(nameCharacter));

        message = createCharacterMessage(nameCharacter);

        //MOSTRI GLI ARCHIPELAGHI DELLA MAPPA CON GLI INDICI

        //MOSTRERAI GLI STUDENTI SULL CARTA
        //MOSTRERAI STUDENTI SULL ENTRATA

        //STUDENTI IN SALA/BOARD
        //CHIEDERE IL COLORE
        return message;
    }


        private Message createCharacterMessage(String nameCharacter) {
        int notValidArchipelago = 13;
        int notValidNumOfStep = -1;
            switch (nameCharacter) {
                case "Archer" -> {
                    Archer card = (Archer) cli.getRemoteModel().getCharacterCardMap().get(nameCharacter);
                    return new PlayCharacterMessage(card, notValidArchipelago, null, null, null,notValidNumOfStep);
                }
                    case "Chef" -> {
                    Chef card = (Chef) cli.getRemoteModel().getCharacterCardMap().get(nameCharacter);
                        return new PlayCharacterMessage(card,notValidArchipelago,null,null, null,  notValidNumOfStep);
                    }
                case "Knight" -> {
                    Knight card = (Knight) cli.getRemoteModel().getCharacterCardMap().get(nameCharacter);
                    return new PlayCharacterMessage(card,notValidArchipelago,null,null, null, notValidNumOfStep);
                }
                case "Messenger" -> {
                    Messenger card = (Messenger) cli.getRemoteModel().getCharacterCardMap().get(nameCharacter);
                    displayArchipelagos();
                    Scanner scanner = cli.scanner;
                    System.out.println("Choose an archipelago by his index");
                    int indexOfArchipelago = Integer.parseInt(scanner.nextLine());
                    return new PlayCharacterMessage(card,indexOfArchipelago,null,null, null,notValidNumOfStep);
                }
                case "Baker" -> {
                    Baker card = (Baker) cli.getRemoteModel().getCharacterCardMap().get(nameCharacter);
                    return new PlayCharacterMessage(card,notValidArchipelago,null,null,null, notValidNumOfStep);
                }
                case "Princess" -> {
                    Princess card = (Princess) cli.getRemoteModel().getCharacterCardMap().get(nameCharacter);
                    List<Integer> toTradeFromCard = new ArrayList<>();
                    int indexStud;

                    for (Student student : card.getStudentsOnCard()) {
                        printStudent(student);
                    }
                    do {
                        System.out.println("Please write a valid Student by his index: ");
                        Scanner scanner = cli.scanner;
                        indexStud = Integer.parseInt(scanner.nextLine());
                    }while (indexStud<0 || indexStud>card.getStudentsOnCard().size());
                    toTradeFromCard.add(indexStud);
                    return new PlayCharacterMessage(card,notValidArchipelago,null,toTradeFromCard,null,notValidNumOfStep);
                }
                case "Jester" -> {
                    int jesterTrade = 3;
                    List<Integer> toTradeFromEntrance = new ArrayList<>();
                    List<Integer> toTradeFromCard = new ArrayList<>();
                    Jester card = (Jester) cli.getRemoteModel().getCharacterCardMap().get(nameCharacter);

                    System.out.println("You can choose three students from entrance:");
                    for (int i = 0; i < jesterTrade; i++) {
                        for (Student student : cli.getRemoteModel().getStudentsOnEntranceMap().values()) {
                        printStudent(student);
                        }
                        Scanner scanner = cli.scanner;
                        Integer indexStud = Integer.parseInt(scanner.nextLine());
                        if (cli.getRemoteModel().getStudentsOnEntranceMap().containsKey(indexStud))
                            toTradeFromEntrance.add(indexStud);
                        else {
                            System.out.println("Please write a valid Student");
                            i--;//check if this does not create an infinite loop
                        }
                    }

                    System.out.println("You can choose from those student on Jester card by their index");
                    for (int i = 0; i < jesterTrade; i++) {
                        for (Student student : card.getStudentsOnCard()) {
                            printStudent(student);
                        }
                        Scanner scanner = cli.scanner;
                        int indexStud = Integer.parseInt(scanner.nextLine());
                        if (cli.getRemoteModel().getStudentsOnEntranceMap().containsKey(indexStud))
                            toTradeFromCard.add(indexStud);
                        else {
                            System.out.println("Please write a valid Student");
                            i--;//check if this does not create a infinite loop
                        }
                    }
                    return new PlayCharacterMessage(card,notValidArchipelago,toTradeFromCard,toTradeFromEntrance,null, notValidNumOfStep);
                }
                case "Friar" -> {
                    int indexStud;
                    List<Integer> toTradeFromCard = new ArrayList<>();
                    Friar card = (Friar) cli.getRemoteModel().getCharacterCardMap().get(nameCharacter);
                        //display and get one student and one archipelago and sent to the controller
                    displayArchipelagos();
                    Scanner scanner = cli.scanner;
                    System.out.println("Choose an archipelago by his index");
                    int indexOfArchipelago = Integer.parseInt(scanner.nextLine());
                    for (Student student : card.getStudentsOnCard()) {
                        printStudent(student);
                    }
                    do {
                        indexStud = Integer.parseInt(scanner.nextLine());
                        if(indexStud<0 || indexStud>card.getStudentsOnCard().size())
                            System.out.println("Please insert a valid number");
                    } while(indexStud<0 || indexStud>card.getStudentsOnCard().size());

                    if (cli.getRemoteModel().getStudentsOnEntranceMap().containsKey(indexStud))
                        toTradeFromCard.add(indexStud);
                    return new PlayCharacterMessage(card,indexOfArchipelago,null,toTradeFromCard,null,notValidNumOfStep);
                }
                case "Minstrel" -> {
                    Minstrel card = (Minstrel) cli.getRemoteModel().getCharacterCardMap().get(nameCharacter);
                    int indexStud;
                    List<Integer> toTradeFromTables = new ArrayList<>();
                    List<Integer> toTradeFromEntrance = new ArrayList<>();
                    for(Student student: cli.getRemoteModel().getStudentsOnBoardMap().values()) {
                        printStudent(student);
                    }
                    for (int i = 0; i < 2; i++) {
                        do {
                            indexStud = Integer.parseInt(cli.scanner.nextLine());
                            if(indexStud<0 || indexStud>cli.getRemoteModel().getStudentsOnBoardMap().size())
                                System.out.println("Please insert a valid number");
                        } while(indexStud<0 || indexStud>cli.getRemoteModel().getStudentsOnBoardMap().size());
                        toTradeFromTables.add(indexStud);
                    }

                    for (Student student : cli.getRemoteModel().getStudentsOnEntranceMap().values()) {
                        printStudent(student);
                    }
                    for (int i = 0; i < 2; i++) {
                        do {
                            indexStud = Integer.parseInt(cli.scanner.nextLine());
                            if (indexStud < 0 || indexStud > cli.getRemoteModel().getStudentsOnEntranceMap().size())
                                System.out.println("Please insert a valid number");
                        } while (indexStud < 0 || indexStud > cli.getRemoteModel().getStudentsOnEntranceMap().size());
                        toTradeFromEntrance.add(indexStud);
                    }

                    return new PlayCharacterMessage(card,notValidArchipelago,toTradeFromEntrance,null,toTradeFromTables,notValidNumOfStep);
                }
                case "Magician" -> {
                    Magician card = (Magician) cli.getRemoteModel().getCharacterCardMap().get(nameCharacter);
                    System.out.println("Please insert a number between 0 and 2");
                    Scanner scanner = cli.scanner;
                    int numOfStep = Integer.parseInt(scanner.nextLine());
                    while(numOfStep<0 || numOfStep>2){
                        System.out.println("Number not valid \nPlease insert a number between 0 and 2");
                        numOfStep = Integer.parseInt(scanner.nextLine());
                    }
                    return new PlayCharacterMessage(card,notValidArchipelago,null,null,null,numOfStep);
                }
                case "Banker" -> {
                    Banker card = (Banker) cli.getRemoteModel().getCharacterCardMap().get(nameCharacter);
                    int indexStud;
                    List<Integer> toTradeFromCard = new ArrayList<>();

                    for (Student student : card.getStudentsOnCard()) {
                        printStudent(student);
                    }

                    for (int i = 0; i < 3; i++) {
                        do {
                            indexStud = Integer.parseInt(cli.scanner.nextLine());
                            if(indexStud<0 || indexStud>cli.getRemoteModel().getStudentsOnBoardMap().size())
                                //not sure if we actually have a right index
                                System.out.println("Please insert a valid number");
                        } while(indexStud<0 || indexStud>cli.getRemoteModel().getStudentsOnBoardMap().size());

                        if (cli.getRemoteModel().getStudentsOnBoardMap().containsKey(indexStud))
                            toTradeFromCard.add(indexStud);
                    }
                    return new PlayCharacterMessage(card,notValidArchipelago,null,toTradeFromCard,null,notValidNumOfStep);
                }
                case "Herbalist" -> {
                    Herbalist card = (Herbalist) cli.getRemoteModel().getCharacterCardMap().get(nameCharacter);
                    displayArchipelagos();
                    Scanner scanner = cli.scanner;
                    System.out.println("Choose an archipelago by his index");
                    int indexOfArchipelago = Integer.parseInt(scanner.nextLine());
                    return new PlayCharacterMessage(card,indexOfArchipelago,null,null,null, notValidNumOfStep);
                }

                default -> throw new IllegalStateException("Unexpected value: " + nameCharacter);
            }
        }

        public void printStudent (Student student){
            switch (student.getColor()) {
                case GREEN -> System.out.print(Printable.STUDENT_GREEN + "  ");
                case BLUE -> System.out.print(Printable.STUDENT_BLUE + "  ");
                case PINK -> System.out.print(Printable.STUDENT_PINK + "  ");
                case RED -> System.out.print(Printable.STUDENT_RED + "  ");
                case YELLOW -> System.out.print(Printable.STUDENT_YELLOW + "  ");
            }
        }

    }
