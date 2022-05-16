package it.polimi.ingsw.AlternativeView;

import it.polimi.ingsw.AlternativeServer.ClientConnection;
import it.polimi.ingsw.AlternativeServer.SocketClientConnection;
import it.polimi.ingsw.Model.Exception.ExceptionGame;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.NetworkUtilities.Message.*;
import it.polimi.ingsw.Observer.Observer;

public class RemoteView extends ViewInterface {

    //pay attention to the message parameters and adjust them
    private SocketClientConnection clientConnection;

    public RemoteView(SocketClientConnection clientConnection) {
        this.clientConnection = clientConnection;
    }

    public ClientConnection getClientHandler() {
        return clientConnection;
    }

    public void playAssistantCard(AssistantsCards assistantsCards) {
        clientConnection.sendMessage(new AssistantCardMessage("",assistantsCards));
    }

    public void playCharacterCard(CharacterCard card) {
        //    clientConnection.sendMessage(new CharacterCard("",card));
    }

    public void placeStudentOnBoard(Student studentsToBoard) {
        clientConnection.sendMessage(new StudentOnBoard("",studentsToBoard,GameStateMessage.STUDENT_ON_BOARD));
    }

    public void placeStudentOnArchipelago(Student studentsToArchipelago) {
        clientConnection.sendMessage(new StudentInArchipelago("",studentsToArchipelago,GameStateMessage.STUDENT_ON_BOARD));
    }

    public void moveMotherNature(Archipelago archipelago) {
        clientConnection.sendMessage(new MoveMotherNature("",archipelago,GameStateMessage.MOVE_MOTHER_NATURE));
    }


    @Override
    public void update(Object message) throws ExceptionGame {
        clientConnection.sendMessage((Message) message);
    }
}
