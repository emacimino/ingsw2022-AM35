package it.polimi.ingsw.Client.Gui.Scene;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginSceneController extends GenericSceneController{
//questa classe dovrà fare il cambio di scena solo se effettivamente il match viene creato: e ricevo di conseguenza un
    //input infoGameMessage
    @FXML
    private Button backBtn;
    @FXML
    private Button loginBtn;
    @FXML
    private Label userLabel;
    @FXML
    private TextField textFieldUsername;

    public void switchToMenuScene(Event event) throws IOException {
        SceneController.changeRootPane(getObservers(), event, "menu.fxml");
    }
    @FXML
    public void initialize() {
       // joinBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onJoinBtnClick);
        backBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onBackToMenuBtnClick);
        loginBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::loginBtnClick);
    }

    public void usernameInput(ActionEvent event){
        String username = textFieldUsername.getText();

    }

    private void onBackToMenuBtnClick(Event event) {
      //  joinBtn.setDisable(true);
      //  backToMenuBtn.setDisable(true);

        SceneController.changeRootPane(getObservers(), event, "menu.fxml");
    }

    public void loginBtnClick(Event event){
        SceneController.changeRootPane(getObservers(), event, "setupMatch.fxml");
    }

}
