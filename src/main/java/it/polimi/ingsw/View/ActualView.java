package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.NetworkUtilities.Message.*;
import it.polimi.ingsw.NetworkUtilities.Server.ClientHandler;


import java.util.ArrayList;
import java.util.List;

public class ActualView extends ViewInterface {
    private final ClientHandler clientHandler;
    private final String username;

    public ActualView(String username, ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        addObserver(this.clientHandler);
        this.username = username;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    @Override
    public void loginRequest(String name,String username){
        this.clientHandler.sendMessage(new LoginRequest(name,username));
    }


    @Override
    public void setNumOfPlayers(int numOfPlayers) {
        this.clientHandler.sendMessage(new NumberOfPlayer(numOfPlayers));
    }

    @Override
    public void participateToAnExistingMatch(boolean participate) {
        this.clientHandler.sendMessage(new ParticipateToAMatch(participate));
    }

    @Override
    public void setTypeOfMatch(String typeOfMatch) {
        this.clientHandler.sendMessage(new TypeOfMatch(typeOfMatch));
    }


    @Override
    public void playAssistantCard(AssistantsCards assistantsCards) {
        this.clientHandler.sendMessage(new AssistantCardMessage(username,assistantsCards));
    }

    @Override
    public void playCharacterCard(CharacterCard card) {
        this.clientHandler.sendMessage(new CharacterCardRequired(card));
    }

    @Override
    void placeStudentOnBoard(List<Student> studentsToBoard) {
        List<String> studentsToBoardString = new ArrayList<>();
        for(Student student: studentsToBoard){
            studentsToBoardString.add(student.colorToString());
        }
        this.clientHandler.sendMessage(new StudentOnBoard(studentsToBoardString));
    }

    @Override
    public void placeStudentsOnBoard(List<Student> studentsToBoard) {

    }

    @Override
    public void placeStudentOnArchipelago(List<Student> studentsToArchipelago) {

    }

    @Override
    public void moveMotherNature(int steps) {

    }

    @Override
    public void update(Message message) {
        clientHandler.sendMessage(message);
    }
}
