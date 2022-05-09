package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.NetworkUtilities.Message.Message;
import it.polimi.ingsw.Observer.Observer;

import java.util.List;

public class ActualView implements ViewInterface, Observer {

    public ActualView(String username) {
        initializeView(username);
    }

    private void initializeView(String username) {


    }

    @Override
    public void setUsername(String username) {

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

    @Override
    public void update(Message message) {

    }
}
