package it.polimi.ingsw.Client.Gui.Scene;

import it.polimi.ingsw.NetworkUtilities.Message.DisconnectMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class EndSceneController extends GenericSceneController{
    @FXML
    Button quitBtn, restartBtn;

    public void quit(ActionEvent event){
        notifyObserver(new DisconnectMessage());
    }

    public void restartMatch(ActionEvent event){
        SceneController.changeRootPane(getObservers(), event, "endScene.fxml");
    }
}
