package it.polimi.ingsw.NetworkUtilities;

import java.io.Serial;

/**
 * Class used to send a single cloud
 */
public class CloudMessage extends Message{
    @Serial
    private static final long serialVersionUID = 4508068615547056508L;
    private final int cloud;

    /**
     * Constructor of the class
     * @param cloud a cloud in the game
     */
    public CloudMessage(int cloud) {
        this.cloud = cloud;
        setType(TypeMessage.CLOUD_CHOICE);
    }

    /**
     * Method that return the cloud contained in the message
     * @return a cloud
     */
    public int getCloud() {
        return cloud;
    }
}
