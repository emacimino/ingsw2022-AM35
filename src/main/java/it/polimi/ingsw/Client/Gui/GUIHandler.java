package it.polimi.ingsw.Client.Gui;

import it.polimi.ingsw.Client.Gui.Scene.SceneController;
import it.polimi.ingsw.NetworkUtilities.Message.GenericMessage;
import it.polimi.ingsw.NetworkUtilities.Message.Message;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;
import javafx.application.Platform;

public class GUIHandler  extends Observable implements Observer {


    public void showMessage(Message message){
        switch(message.getType()){
            case REQUEST_LOGIN -> loginHandler(message);
        }
       // GenericMessage gMessage = (GenericMessage)message;
        String genericMessage = message.toString();
        Platform.runLater(() -> SceneController.showAlert("Info GenericMessage", genericMessage));

    }

    private void loginHandler(Message message) {
        String genericMessage = message.toString();
        Platform.runLater(() -> SceneController.showAlert("Login Message", genericMessage));
    }

    @Override
    public void update(Message message) {
        notifyObserver(message);
    }
}
