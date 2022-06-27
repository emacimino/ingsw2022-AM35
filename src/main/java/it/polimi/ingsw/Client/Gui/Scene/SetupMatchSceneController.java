package it.polimi.ingsw.Client.Gui.Scene;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * A class that handles a scene
 */
public class SetupMatchSceneController extends GenericSceneController{
    @FXML
    private Button exitBtn;

    /**
     * This method initializes the quit button
     */
    @FXML
    public void initialize() {
        // joinBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onJoinBtnClick);
        exitBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> System.exit(0));   }

    /**
     * This method opens up the login screen on button click
     * @param event user input
     */
    private void onBackToLoginBtnClick(Event event) {
        SceneController.changeRootPane(getObservers(), event, "login.fxml");
    }
}
