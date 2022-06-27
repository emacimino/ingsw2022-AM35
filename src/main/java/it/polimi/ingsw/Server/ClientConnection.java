package it.polimi.ingsw.Server;


import it.polimi.ingsw.NetworkUtilities.Message;

/**
 * This interface shows the method that handle one client connection
 */
public interface ClientConnection {
    /**
     * Handle connection closing
     */
    void closeConnection();

    /**
     * Handle the communication of a message after an event
     * @param message message that contains an event
     */
    void asyncSendMessage(Message message);

    /**
     * Getter of numOfMatch
     * @return numOfMatch
     */
    Integer getNumOfMatch();
}
