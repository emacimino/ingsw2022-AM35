package it.polimi.ingsw.View;

import it.polimi.ingsw.Client.CLIENT2.UserView;
import it.polimi.ingsw.Controller.GameState;
import it.polimi.ingsw.Model.Wizard.Board;
import it.polimi.ingsw.NetworkUtilities.*;
import it.polimi.ingsw.Server.ClientConnection;
import it.polimi.ingsw.Server.SocketClientConnection;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;

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


    public void moveMotherNature(Integer archipelago) {
        clientConnection.sendMessage(new MoveMotherNatureMessage(archipelago));
    }


    @Override
    public void update(Message message){
        if(message instanceof EndMatchMessage)
            manageEndMatch(message);
        else
            sendMessage(message);
    }

    private void manageEndMatch(Message message) {
        sendMessage(message);
        if(clientConnection.getController().isMatchOnGoing()) {
            clientConnection.getController().setMatchOnGoing(false);
            clientConnection.getController().setGameState(GameState.GAME_ENDED);
        }

    }

    @Override
    public void sendMessage(Message message) {
        clientConnection.sendMessage(message);
    }


    @Override
    public void askLogin() {

    }

    @Override
    public void askToPlayAssistantCard(List<AssistantsCards> assistantsCards) {

    }


    @Override
    public void askMoveMotherNature(String message) {

    }

    @Override
    public void askChooseCloud(CloudInGame cloud) {

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
    public void loadBoard(Board board) {

    }



    @Override
    public void showWinMessage(EndMatchMessage message, Boolean isWinner) {

    }

    @Override
    public void showGameState(CurrentGameMessage currentGameMessage) {

    }

    @Override
    public void showCharactersCards(CharacterCardInGameMessage characterCardInGameMessage) {

    }


    @Override
    public void askToMoveStudent() {

    }

    @Override
    public void loadArchipelagosOption(Map<Integer, Archipelago> archipelago) {

    }

    @Override
    public void loadStudentOnEntrance(Map<Integer, Student> students) {

    }

}
