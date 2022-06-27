package it.polimi.ingsw.Client.Gui.Scene;

import it.polimi.ingsw.Client.Gui.GUI;
import it.polimi.ingsw.Client.RemoteModel;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;

import java.util.List;

/**
 * Abstract class
 */
public abstract class GenericSceneController extends Observable {
    protected RemoteModel remoteModel;

    /**
     *
     *update the remote model
     *@param remoteModel remote model updated
     *
     * @param remoteModel
     */
    public void setRemoteModel(RemoteModel remoteModel){
        this.remoteModel = remoteModel;
    }

}
