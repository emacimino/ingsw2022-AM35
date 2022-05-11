package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;

import java.util.List;

//la view osserva il modello, se ci sono modifiche al modello il modello notificherà la view
public abstract class ViewInterface extends Observable implements Observer {

    abstract void loginRequest(String username);

   abstract void setUsername();

    abstract void setNumOfPlayers(int numOfPlayers);

    abstract void participateToAnExistingMatch(boolean participate);

    abstract void setTypeOfMatch(String typeOfMatch);

    abstract void playAssistantCard(AssistantsCards assistantsCards);

    abstract void playCharacterCard(CharacterCard card);

    abstract void placeStudentOnBoard(List<Student> studentsToBoard);

    abstract void placeStudentOnArchipelago(List<Student> studentsToArchipelago);

    abstract void moveMotherNature(int steps);
}
