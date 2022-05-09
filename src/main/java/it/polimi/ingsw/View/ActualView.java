package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.NetworkUtilities.Client.ClientHandler;

import java.util.List;

public class ActualView implements ViewInterface {
    private final ClientHandler clientHandler;

    public ActualView(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    @Override
    public void loginRequest(){
        this.clientHandler
    }

    @Override
    public void setUsername() {

    }

    @Override
    public void setNumOfPlayers(int numOfPlayers) {

    }

    @Override
    public void participateToAnExistingMatch(boolean participate) {

    }

    @Override
    public void setTypeOfMatch(String typeOfMatch) {

    }

    @Override
    public void playAssistantCard(AssistantsCards assistantsCards) {

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
