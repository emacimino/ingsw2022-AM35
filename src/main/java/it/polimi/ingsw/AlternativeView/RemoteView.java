package it.polimi.ingsw.AlternativeView;

import it.polimi.ingsw.AlternativeServer.ClientConnection;
import it.polimi.ingsw.AlternativeServer.SocketClientConnection;
import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.NetworkUtilities.Message.*;

import java.util.List;

public class RemoteView extends ViewInterface {

    //pay attention to the message parameters and adjust them
    private SocketClientConnection clientConnection;

    public RemoteView(SocketClientConnection clientConnection) {
        this.clientConnection = clientConnection;
    }

    public ClientConnection getClientHandler() {
        return clientConnection;
    }

    public void askAssistantCard(List<AssistantsCards> assistantsCards) {
        clientConnection.sendMessage(new AssistantCardListMessage(assistantsCards));
    }

    public void askToMoveStudentFromEntrance(List<Student> students){
        clientConnection.sendMessage(new StudentsListMessage(students));

    }

    @Override
    public void showGenericMessage(String genericMessage) {
        clientConnection.sendMessage(new GenericMessage(genericMessage));
    }

    public void playCharacterCard(CharacterCard card) {
        //    clientConnection.sendMessage(new CharacterCard("",card));
    }

    @Override
    public void placeStudentOnBoard(Student studentsToBoard) {
        notifyObserver(new MoveStudentMessage(studentsToBoard, null));

    }


    public void placeStudentOnArchipelago(Student studentsToArchipelago) {

    }

    public void moveMotherNature(Archipelago archipelago) {
        clientConnection.sendMessage(new MoveMotherNatureMessage(archipelago));
    }


    @Override
    public void update(Object message){
        clientConnection.sendMessage((Message) message);
    }

    public void sendMessage(Message message){
        clientConnection.sendMessage(message);
    }
}
