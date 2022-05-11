package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.NetworkUtilities.Client.Client;
import it.polimi.ingsw.NetworkUtilities.Message.Message;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.View.ActualView;
import it.polimi.ingsw.View.ViewInterface;

import java.util.List;

public class ClientController implements ViewInterface, Observer {

    private ViewInterface view;
    private Client client;

    public ClientController(ViewInterface view) {
        this.view = view;
    }

    @Override
    public void update(Message message) {

    }

    @Override
    public void loginRequest(String username) {

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
