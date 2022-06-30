package it.polimi.ingsw.Client.Gui.Scene;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * A class used to show the main menu
 */
public class MenuSceneController extends GenericSceneController{

    @FXML
    public Button playBtn;
    @FXML
    public Button exitBtn;

    /**
     * This method initializes the menu
     */
    @FXML
    public void initialize() {
        exitBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> System.exit(0));
    }

    /**
     * This method is used to display server information
     * @param event user input
     */
    @FXML
    public void goToServerInfo(ActionEvent event){
        SceneController.changeRootPane(getObservers(), event, "serverInfo.fxml");
    }
}
