package it.polimi.ingsw.Client;

import it.polimi.ingsw.Controller.TurnPhase;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.Model.Wizard.Wizard;
import it.polimi.ingsw.NetworkUtilities.Message.*;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import java.util.List;

public class CLIHandler {
    Message message;
    TurnPhase turnPhase = TurnPhase.LOGIN;

    public Message convertInputToMessage(String inputString) {
        Message message;
        switch (turnPhase) {
            case LOGIN -> message = createLoginMessage(inputString);

            case PLAY_ASSISTANT -> message = createAssistantCardMessage(inputString);
            //  case MOVE_STUDENTS -> message = createMoveStudentMessage(inputString);
            default -> {
                throw new IllegalStateException();
            }
        }

        return message;
    }

    public void showMessage(Message message) {
        String show;
        switch (message.getType()) {
            case REQUEST_LOGIN -> requestLogin();
            case LIST_ASSISTANT_CARD -> showAssistantsCardOption(message);
            case MOVE_STUDENT -> show = "Please move your students from the entrance";
            case MOVE_MOTHER_NATURE -> show = "Please, move Mother Nature";
            case CHOOSE_CLOUD -> show = "Please choose a cloud";
            case END_OF_TURN -> show = "Please wait for your turn";
            case GENERIC_MESSAGE -> showGenericMessage(message);
            case GAME_INFO -> showCurrentGame(message);
            default -> System.out.println(message);

        }

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
            infoArchipelago(archipelago);

        }
    }

    private void infoArchipelago(Archipelago archipelago) {
        System.out.println("In this archipelago we have:\n" );
        for(Student student: archipelago.getStudentFromArchipelago()){
            printStudent(student);
        }
        if(archipelago.isMotherNaturePresence()){
            System.out.println("In this archipelago we have motherNature \n" );
        }
        if(archipelago.isProhibition()){
            System.out.println("In this archipelago a prohibition is present \n" );
        }
    }

    private void currentBoardInfo(Game game) {
        for (Wizard wizard : game.getWizards()) {
            System.out.println("To " + wizard.getUsername() + " belongs: \n ");
            printStudentInEntrance(wizard);
            printStudentInTables(wizard);
            printStudentInArchipelagoForEveryWizard(wizard);
        }
    }


    private void printStudentInArchipelagoForEveryWizard(Wizard wizard) {
        for (Archipelago archipelago : wizard.getArchipelagosOfWizard()) {
            for (Student student : archipelago.getStudentFromArchipelago())
                printStudent(student);
        }
    }

    private void printStudentInTables(Wizard wizard) {
        System.out.println("To " + wizard.getUsername() + " tables belongs: \n ");

        for (Color color : Color.values()) {
            System.out.println(color + "\n");
            try {
                for (Student student : wizard.getBoard().getStudentsFromTable(color)) {
                    printStudent(student);
                }
            } catch (ExceptionGame e) {
                e.printStackTrace();
            }
        }
    }

    private void printStudentInEntrance(Wizard wizard) {
        System.out.println("In entrance we have: \n ");
        for (Student student : wizard.getBoard().getStudentsInEntrance()) {
            printStudent(student);
        }
    }

    public void printStudent(Student student) {
        switch (student.getColor()) {
            case GREEN -> System.out.println(Printable.STUDENT_GREEN + "\n");
            case BLUE -> System.out.println(Printable.STUDENT_BLUE + "\n");
            case PINK -> System.out.println(Printable.STUDENT_PINK + "\n");
            case RED -> System.out.println(Printable.STUDENT_RED + "\n");
            case YELLOW -> System.out.println(Printable.STUDENT_YELLOW + "\n");
        }
    }



    private void showAssistantsCardOption(Message message) {
        System.out.println("Please select an Assistant Card from the option below: ");
        List<AssistantsCards> assistantsCards = ((AssistantCardListMessage)message).getAssistantsCards();
        for(AssistantsCards a : assistantsCards){
            System.out.println(Printable.getAssistantCardCLI(a));
        }
        setPhase(TurnPhase.PLAY_ASSISTANT);
    }
    private void showGenericMessage(Message message){
        GenericMessage genericMessage = (GenericMessage) message;
        System.out.println(genericMessage.getContent());
    }
    private void requestLogin(){
        System.out.println("Please, procede with the login: ");
        System.out.println("Insert username, number of players you want in the match (from 2 to 4) and if you want to play as an expert: " +
                "\n" + "(for example: camilla, 2, yes  )");
    }


    private Message createAssistantCardMessage(String assistant){
        return null;
    }
    private Message createLoginMessage(String login){
        LoginResponse message;
        String info[] = login.split(",");

        String username = info[0];
        int numberOfPlayers = Integer.parseInt(info[1]);
        if(numberOfPlayers< 2 || numberOfPlayers >4) {
            System.out.println("Error in the input, please re-insert information regarding the number of players re-correctly");
            return null;
        }
        boolean isExpert;
        if(info[2].equals("yes"))
            isExpert = true;
        else if(info[2].equals("no"))
            isExpert = false;
        else {
            System.out.println("Error in the input, please re-insert information regarding the expert field re-correctly");
            return null;
        }
        System.out.println(Printable.PROF_PINK);
        message = new LoginResponse(username, numberOfPlayers, isExpert);
        return message;
    }

    private void setPhase(TurnPhase turnPhase){
        this.turnPhase = turnPhase;
    }

    private void displayCharacterCard(Message message){
        System.out.println("Character Card available: \n");
        List<CharacterCard> characterCards = (((CharacterChardDisplayMessage) message).getCharacterCards());
        Printable.printCharacterCards(characterCards);
    }

    private void displayArchipelagos(Message message) throws ExceptionGame {
        System.out.println("Archipelago: \n");
        List<Archipelago> archipelagoList = (((ArchipelagoListMessage)message).getArchipelagoList());
        int i=1;
        for (Archipelago a:
             archipelagoList) {
            System.out.println("Archipelago " + i + ":");
            displayArchipelagosHelper(a);
            i++;
        }
    }

    private void displayArchipelagosHelper(Archipelago archipelago) throws ExceptionGame {
        List<Island> islands = archipelago.getIsle();
        if(archipelago.isMotherNaturePresence()) System.out.println("Mother nature present!");
        for (Island isle:
                islands) {
            System.out.println(isle.getTower().toString());
            System.out.println(isle.getStudentInIsland().toString());
            if(isle.isInterdictionCard()) System.out.println("Interdiction effect");
        }
    }
}
