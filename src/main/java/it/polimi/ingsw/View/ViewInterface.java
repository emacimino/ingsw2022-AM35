package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;

import java.util.List;

public interface ViewInterface {

    void loginRequest(String username);

    void setUsername();

    void setNumOfPlayers(int numOfPlayers);

    void participateToAnExistingMatch(boolean participate);

    void setTypeOfMatch(String typeOfMatch);

    void playAssistantCard(String username, AssistantsCards assistantsCards);

    void playCharacterCard(CharacterCard card);

    void placeStudentsOnBoard(List<Student> studentsToBoard);

    void placeStudentOnArchipelago(List<Student> studentsToArchipelago);

    void moveMotherNature(int steps);
}
