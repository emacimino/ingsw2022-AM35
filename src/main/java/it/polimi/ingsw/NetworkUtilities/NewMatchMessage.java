package it.polimi.ingsw.NetworkUtilities;

/**
 * Class used to communicate the creation of a new match
 */
public class NewMatchMessage extends Message{
    /**
     * Constructor of the class
     */
    public NewMatchMessage() {
        setType(TypeMessage.NEW_MATCH);
    }
}
