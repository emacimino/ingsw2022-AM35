package it.polimi.ingsw.Client.Gui.Scene;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class MenuSceneController extends GenericSceneController{

    @FXML
    Button playBtn;
    @FXML
    Button exitBtn;

    @FXML
    public void initialize() {
        exitBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> System.exit(0));
    }

    @FXML
    public void goToServerInfo(ActionEvent event){
        SceneController.changeRootPane(getObservers(), event, "serverInfo.fxml");
    }
}
