package it.polimi.ingsw.NetworkUtilities;

import it.polimi.ingsw.Model.SchoolsLands.Cloud;

import java.io.Serial;
import java.util.Map;

/**
 * Clouds ready to be picked message
 */
public class CloudInGame extends Message{
    @Serial
    private static final long serialVersionUID = -5826688117840815134L;
    private final Map<Integer, Cloud> cloudMap;

    /**
     * Constructor of the class
     * @param cloudMap cloud map
     */
    public CloudInGame(Map<Integer, Cloud> cloudMap) {
        this.cloudMap = cloudMap;
        setType(TypeMessage.CLOUD_IN_GAME);
    }

    /**
     * Getter for cloud map
     * @return cloud map
     */
    public Map<Integer, Cloud> getCloudMap() {
        return cloudMap;
    }
}
