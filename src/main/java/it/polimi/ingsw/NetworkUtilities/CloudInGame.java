package it.polimi.ingsw.NetworkUtilities;

import it.polimi.ingsw.Model.SchoolsLands.Cloud;

import java.io.Serial;
import java.util.Map;

/**
 * Class used to send clouds
 */
public class CloudInGame extends Message{
    @Serial
    private static final long serialVersionUID = -5826688117840815134L;
    private final Map<Integer, Cloud> cloudMap;

    /**
     * Constructor of the class
     * @param cloudMap a map of the cloud in the game
     */
    public CloudInGame(Map<Integer, Cloud> cloudMap) {
        this.cloudMap = cloudMap;
        setType(TypeMessage.CLOUD_IN_GAME);
    }

    /**
     * Method that returns the cloud contained in the message
     * @return a map containing the clouds
     */
    public Map<Integer, Cloud> getCloudMap() {
        return cloudMap;
    }
}
