package it.polimi.ingsw.View;

import it.polimi.ingsw.Controller.GameState;
import it.polimi.ingsw.NetworkUtilities.EndMatchMessage;
import it.polimi.ingsw.NetworkUtilities.Message;
import it.polimi.ingsw.Server.SocketClientConnection;

public class RemoteView extends ViewInterface   {

    private final SocketClientConnection clientConnection;
    /**
     * Constructor of the class
     * @param clientConnection is the client associated to the view
     */
    public RemoteView(SocketClientConnection clientConnection) {
        this.clientConnection = clientConnection;
    }

    /**
     * Used to update every view after an event
     * @param message a message created after a trigger event
     */
    @Override
    public void update(Message message){
        if(message instanceof EndMatchMessage)
            manageEndMatch(message);
        else
            sendMessage(message);
    }

    /**
     * Used to handle the end of the match event
     * @param message EndMatchMessage
     */
    private void manageEndMatch(Message message) {
        sendMessage(message);
        if(clientConnection.getController().isMatchOnGoing()) {
            clientConnection.getController().setMatchOnGoing(false);
            clientConnection.getController().setGameState(GameState.GAME_ENDED);
        }

    }

    /**
     * Used to send a message from the view
     * @param message message to be sent
     */
    @Override
    public void sendMessage(Message message) {
        clientConnection.sendMessage(message);
    }




}
