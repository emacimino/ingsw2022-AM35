package it.polimi.ingsw.View;

import it.polimi.ingsw.Client.CLIENT2.UserView;
import it.polimi.ingsw.Model.SchoolsLands.Cloud;
import it.polimi.ingsw.Model.Wizard.Board;
import it.polimi.ingsw.Server.ClientConnection;
import it.polimi.ingsw.Server.SocketClientConnection;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.NetworkUtilities.Message.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RemoteView extends ViewInterface implements UserView {

    //pay attention to the message parameters and adjust them
    private final SocketClientConnection clientConnection;

    public RemoteView(SocketClientConnection clientConnection) {
        this.clientConnection = clientConnection;
    }

    public ClientConnection getClientConnection() {
        return clientConnection;
    }

    @Override
    public void showGenericMessage(Message genericMessage) {
        clientConnection.sendMessage(genericMessage);
    }

    public void playCharacterCard(CharacterCard card) {
        //    clientConnection.sendMessage(new CharacterCard("",card));
    }



    public void placeStudentOnArchipelago(Student studentsToArchipelago) {

    }

    public void moveMotherNature(Integer archipelago) {
        clientConnection.sendMessage(new MoveMotherNatureMessage(archipelago));
    }


    @Override
    public void update(Message message){
        clientConnection.sendMessage(message);
    }

    public void sendMessage(Message message){
        clientConnection.sendMessage(message);
    }



    @Override
    public void askLogin() {

    }

    @Override
    public void askToPlayAssistantCard(List<AssistantsCards> assistantsCards) {

    }

    @Override
    public void askMoveStudent(List<Student> students) {

    }

    @Override
    public void askMoveMotherNature(List<Archipelago> archipelagos) {

    }

    @Override
    public void askChooseCloud(List<Cloud> cloud) {

    }

    @Override
    public void showLogin(boolean success) {

    }

    @Override
    public void showGenericMessage(String genericMessage) {

    }

    @Override
    public void showDisconnectionMessage(String usernameDisconnected, String text) {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showBoard(Board board) {

    }

    @Override
    public void showLobby(List<String> usernames) {

    }

    @Override
    public void showMatchInfo() {

    }

    @Override
    public void showWinMessage(String winner) {

    }

    @Override
    public void showGameState(CurrentGameMessage currentGameMessage) {

    }

    @Override
    public void showCharactersCards(CharacterCardInGameMessage characterCardInGameMessage) {

    }

}
