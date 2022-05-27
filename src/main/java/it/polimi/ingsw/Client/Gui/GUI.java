package it.polimi.ingsw.Client.Gui;

import it.polimi.ingsw.Client.Client;
import it.polimi.ingsw.NetworkUtilities.Message.Message;
import it.polimi.ingsw.Observer.Observer;

import java.io.ObjectInputStream;
import java.lang.runtime.ObjectMethods;

public class GUI extends Client implements Observer {

    public GUI(String ip, int port) {
        super(ip, port);
    }


    @Override
    public Thread asyncReadFromSocket(ObjectInputStream socketInput) {
        return null;
    }

    @Override
    public Thread asyncWriteToSocket() {
        return asyncWriteToSocket();
    }


    @Override
    public void update(Message message) {
        sendToServer(message);
    }
}
