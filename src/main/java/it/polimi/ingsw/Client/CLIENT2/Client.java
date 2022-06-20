package it.polimi.ingsw.Client.CLIENT2;

import it.polimi.ingsw.NetworkUtilities.Message.Message;
import it.polimi.ingsw.Observer.Observable;

import javax.swing.table.TableRowSorter;

public abstract class Client extends Observable {
    public abstract void sendMessage(Message message);

    public abstract void readMessage();

    public abstract void disconnect();

    public abstract void enablePingPong(boolean enabled);

}
