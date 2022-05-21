package it.polimi.ingsw.Client;

import it.polimi.ingsw.Controller.TurnPhase;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.NetworkUtilities.Message.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CLIHandler {
    List<AssistantsCards> assistantsCardsInTurn;
    Map<String, AssistantsCards> assistantsCardsMap = new HashMap<>();


    public Message convertInputToMessage(String inputString, TurnPhase turnPhase){
        Message message;
        switch (turnPhase){
            case LOGIN -> message = createLoginMessage(inputString);

            case PLAY_ASSISTANT -> message = createAssistantCardMessage(inputString);
          //  case MOVE_STUDENTS -> message = createMoveStudentMessage(inputString);
            default -> {message = null;}
        }

        return message;
    }

    public void showMessage(Message message){
        String show;
        switch (message.getType()){
            case REQUEST_LOGIN -> requestLogin();
            case LIST_ASSISTANT_CARD -> showAssistantsCardOption(message);
            case MOVE_STUDENT -> show = "Please move your students from the entrance";
            case MOVE_MOTHER_NATURE -> show = "Please, move Mother Nature";
            case CHOOSE_CLOUD -> show = "Please choose a cloud";
            case END_OF_TURN -> showEndOfTurnMessage(message);
            case YOUR_TURN -> showYourTurnMessage(message);
            case GENERIC_MESSAGE -> showGenericMessage(message);
          //  case GAME_INFO -> showCurrentGame();
            default -> System.out.println(message);

        }

    }

    private void showAssistantsCardOption(Message message) {
        System.out.println("Please select an Assistant Card from the option below: ");
        List<AssistantsCards> assistantsCardsInTurn = ((AssistantCardListMessage)message).getAssistantsCards();
        for(AssistantsCards a : assistantsCardsInTurn){
            assistantsCardsMap.put(Constants.getAssistantCardCLI(a), a);
            System.out.println(Printable.getAssistantCardCLI(a));
        }
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
        System.out.println(yourTurnMessage.getContent());
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

}
