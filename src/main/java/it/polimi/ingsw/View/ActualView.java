package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.NetworkUtilities.Message.*;
import it.polimi.ingsw.NetworkUtilities.Server.ClientHandler;


import java.util.List;

public class ActualView extends ViewInterface {
    private final ClientHandler clientHandler;

    public ActualView(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    @Override
    public void loginRequest(String username){
        this.clientHandler.sendMessage(new LoginRequest(username));
    }

    @Override
    public void setUsername() {

    }

    @Override
    public void setNumOfPlayers(int numOfPlayers) {
        this.clientHandler.sendMessage(new NumberOfPlayer("", numOfPlayers));
    }

    @Override
    public void participateToAnExistingMatch(boolean participate) {

    }

    @Override
    public void setTypeOfMatch(String typeOfMatch) {
        //this.clientHandler.sendMessage(new Message());
    }

    @Override
    public void playAssistantCard(AssistantsCards assistantsCards) {
        this.clientHandler.sendMessage(new AssistantCard(assistantsCards.toString(), GameStateMessage.ASSISTANTCARD));
    }

    @Override
    public void playCharacterCard(CharacterCard card) {

    }

    @Override
    public void placeStudentOnBoard(List<Student> studentsToBoard) {

    }

    @Override
    public void placeStudentOnArchipelago(List<Student> studentsToArchipelago) {

    }

    @Override
    public void moveMotherNature(int steps) {

    }
}
