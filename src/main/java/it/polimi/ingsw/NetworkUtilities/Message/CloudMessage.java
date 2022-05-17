package it.polimi.ingsw.NetworkUtilities.Message;

import it.polimi.ingsw.Model.SchoolsLands.Cloud;

public class CloudMessage extends Message{
    private static final long serialVersionUID = 4508068615547056508L;
    private final Cloud cloud;

    public CloudMessage(Cloud cloud) {
        this.cloud = cloud;
    }

    public Cloud getCloud() {
        return cloud;
    }
}
