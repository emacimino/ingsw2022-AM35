package it.polimi.ingsw.Client.Gui;

import it.polimi.ingsw.Client.Client;

import java.io.ObjectInputStream;

public class GUI extends Client {
    private final JavaFxGui guiHandler = new JavaFxGui();

    public GUI(String ip, int port) {
        super(ip, port);
    }


    @Override
    public Thread asyncReadFromSocket(ObjectInputStream socketInput) {
        return guiHandler.asyncReadFromSocket(socketInput);
    }

    @Override
    public Thread asyncWriteToSocket(Object inputFromUSer) {
        return null;
    }

    @Override
    public void login() {
    }


}
