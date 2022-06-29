package it.polimi.ingsw.Client.Gui.Scene;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class SetupMatchSceneController extends GenericSceneController{
    @FXML
    public Button exitBtn;


    @FXML
    public void initialize() {
        // joinBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onJoinBtnClick);
        exitBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> System.exit(0));   }

    private void onBackToLoginBtnClick(Event event) {
        SceneController.changeRootPane(getObservers(), event, "login.fxml");
    }
}
