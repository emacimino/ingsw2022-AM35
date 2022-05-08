package it.polimi.ingsw.Observer;

import it.polimi.ingsw.Model.ExpertMatch.CharacterCard;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;

import java.util.List;

public interface ViewObserver {

    void createAConnection(/*to add*/);

    void chooseAUsername(String username);

    void updateNumberOfPlayers(int numOfPlayers);

    void typeOfMatch(String typeOfMatch);

    void assistantCardChosen(AssistantsCards cards);

    void characterCardChosen(CharacterCard card);

    void studentToAddOnBoard(List<Student> students);

    void studentToAddOnArchipelago(List<Student> students, Archipelago archipelago);

    void moveMotherNature(int steps);

}