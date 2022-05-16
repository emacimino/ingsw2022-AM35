package it.polimi.ingsw.AlternativeView;

import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;

public abstract class ViewInterface extends Observable implements Observer{

    abstract void playAssistantCard(AssistantsCards assistantsCards);

    abstract void playCharacterCard(CharacterCard card);

    abstract void placeStudentOnBoard(Student studentsToBoard);

    abstract void placeStudentOnArchipelago(Student studentsToArchipelago);

    abstract void moveMotherNature(Archipelago archipelago);


    @Override
    public void update(Object message) throws ExceptionGame {

    }
}