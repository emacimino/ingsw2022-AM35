package it.polimi.ingsw.AlternativeView;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;

public interface ViewInterface extends Observable implements Observer {

    void loginRequest(String name, String username);

    void setNumOfPlayers(int numOfPlayers);

    void participateToAnExistingMatch(boolean participate);

    void setTypeOfMatch(String typeOfMatch);

    void playAssistantCard(AssistantsCards assistantsCards);

    void playCharacterCard(CharacterCard card);

    void placeStudentOnBoard(Student studentsToBoard);

    void placeStudentOnArchipelago(Student studentsToArchipelago);

    void moveMotherNature(Archipelago archipelago);


    @Override
    default void update(Object message) throws ExceptionGame {

    }
}