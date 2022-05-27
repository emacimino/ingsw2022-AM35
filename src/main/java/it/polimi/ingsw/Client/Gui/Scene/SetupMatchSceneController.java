package it.polimi.ingsw.Client.Gui.Scene;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class SetupMatchSceneController extends GenericSceneController{
    @FXML
    private Button backBtn;

    @FXML
    private Label labelUsername;

    @FXML
    public void initialize() {
        // joinBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onJoinBtnClick);
        backBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBackToLoginBtnClick);
    }

    private void onBackToLoginBtnClick(Event event) {
        SceneController.changeRootPane(getObservers(), event, "login.fxml");
    }
}
