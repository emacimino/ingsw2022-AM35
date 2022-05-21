package it.polimi.ingsw.Client;

import it.polimi.ingsw.Controller.TurnPhase;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.ExpertMatch.ExpertMatch;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.NetworkUtilities.Message.*;

import java.util.List;

public class CLIHandler {
    Message message;
    TurnPhase turnPhase = TurnPhase.LOGIN;

    public Message convertInputToMessage(String inputString){
        Message message;
        switch (turnPhase){
            case LOGIN -> message = createLoginMessage(inputString);

            case PLAY_ASSISTANT -> message = createAssistantCardMessage(inputString);
          //  case MOVE_STUDENTS -> message = createMoveStudentMessage(inputString);
            default -> {
                throw new IllegalStateException();}
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
            case END_OF_TURN -> show = "Please wait for your turn";
            case GENERIC_MESSAGE -> showGenericMessage(message);
          //  case GAME_INFO -> showCurrentGame();
            default -> System.out.println(message);

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
}
