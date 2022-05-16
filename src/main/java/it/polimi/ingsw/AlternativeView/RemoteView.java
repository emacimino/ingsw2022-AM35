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

public class RemoteView implements ViewInterface {

    //pay attention to the message parameters and adjust them
    private SocketClientConnection clientConnection;

    public RemoteView(SocketClientConnection clientConnection) {
        this.clientConnection = clientConnection;
    }

    public ClientConnection getClientHandler() {
        return clientConnection;
    }

    @Override
    public void loginRequest(String name, String username) {
        clientConnection.sendMessage(new LoginRequest(name,username));
    }

    @Override
    public void setNumOfPlayers(int numOfPlayers) {
        //clientConnection.sendMessage(new NumberOfPlayer(numOfPlayers));
    }

    @Override
    public void participateToAnExistingMatch(boolean participate) {
        clientConnection.sendMessage(new ParticipateToAMatch(participate));
    }

    @Override
    public void setTypeOfMatch(String typeOfMatch) {
        clientConnection.sendMessage(new TypeOfMatch(typeOfMatch));
    }

    @Override
    public void playAssistantCard(AssistantsCards assistantsCards) {
        clientConnection.sendMessage(new AssistantCardMessage("",assistantsCards));
    }

    @Override
    public void playCharacterCard(CharacterCard card) {
        //    clientConnection.sendMessage(new CharacterCard("",card));
    }

    @Override
    public void placeStudentOnBoard(Student studentsToBoard) {
        clientConnection.sendMessage(new StudentOnBoard("",studentsToBoard,GameStateMessage.STUDENT_ON_BOARD));
    }

    @Override
    public void placeStudentOnArchipelago(Student studentsToArchipelago) {
        clientConnection.sendMessage(new StudentInArchipelago("",studentsToArchipelago,GameStateMessage.STUDENT_ON_BOARD));
    }

    @Override
    public void moveMotherNature(Archipelago archipelago) {
        clientConnection.sendMessage(new MoveMotherNature("",archipelago,GameStateMessage.MOVE_MOTHER_NATURE));
    }


    @Override
    public void update(Object message) throws ExceptionGame {
        clientConnection.sendMessage((Message) message);
    }
}
