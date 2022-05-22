package it.polimi.ingsw.View;

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

public class RemoteView extends ViewInterface {

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
    public void update(Object message){
        clientConnection.sendMessage((Message) message);
    }

    public void sendMessage(Message message){
        clientConnection.sendMessage(message);
    }

}
