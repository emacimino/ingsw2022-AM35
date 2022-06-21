package it.polimi.ingsw.NetworkUtilities;

import java.io.Serial;

public class CloudMessage extends Message{
    @Serial
    private static final long serialVersionUID = 4508068615547056508L;
    private final int cloud;

    public CloudMessage(int cloud) {
        this.cloud = cloud;
        setType(TypeMessage.CLOUD_CHOICE);
    }

    public int getCloud() {
        return cloud;
    }
}
