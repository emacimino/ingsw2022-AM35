package it.polimi.ingsw.Client.Gui.Scene;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;


public class MenuSceneController extends GenericSceneController {

    @FXML //this annotation porterà tutti i valori del file fxml che ha menuSceneController
    private Button playBtn;
    @FXML
    private Button quitBtn;

    @FXML
    public void initialize() {
        playBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::switchToLoginScene);
        quitBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> System.exit(0));
    }

    public void switchToLoginScene(Event event){
        SceneController.changeRootPane(getObservers(), event, "login.fxml");
    }
}
