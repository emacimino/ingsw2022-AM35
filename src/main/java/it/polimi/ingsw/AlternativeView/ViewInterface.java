package it.polimi.ingsw.AlternativeView;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.NetworkUtilities.Message.Message;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;

import java.util.List;

public abstract class ViewInterface extends Observable implements Observer{

    public abstract void askAssistantCard(List<AssistantsCards> assistantsCards);

    public abstract void playCharacterCard(CharacterCard card);

    public abstract void placeStudentOnBoard(Student studentsToBoard);

    public abstract void askToMoveStudentFromEntrance(List<Student> studentsToBoard);

    public abstract void placeStudentOnArchipelago(Student studentsToArchipelago);

    public abstract void moveMotherNature(Archipelago archipelago);


    @Override
    public void update(Object message) {

    }

    public abstract void sendMessage(Message message);
    public abstract void showGenericMessage(Message message);
}