package it.polimi.ingsw.Client.Gui.Scene;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jdk.jfr.Event;

/**
 * Class used to alert the scene controller
 */
public class AlertSceneController {
    private final Stage stage;

    @FXML
    public Label titleLbl;
    @FXML
    public Label messageLbl;
    @FXML
    public Button okBtn;

    /**
     * Constructor of the class
     */
    public AlertSceneController() {
        stage = new Stage();
        stage.initOwner(SceneController.getActiveScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        //stage.setAlwaysOnTop(true);
    }

    /**
     * Method used to set the alert title
     * @param str a string you want to print
     */
    public void setAlertTitle(String str) {
        titleLbl.setText(str);
    }

    /**
     * Method used to set the alert message
     * @param str string contained in the message
     */
    public void setAlertMessage(String str) {
        messageLbl.setText(str);
    }

    /**
     * Method used to display an alert
     */
    public void displayAlert() {
        stage.show();
    }

    /**
     * Method used to set the scene
     * @param scene another scene
     */
    public void setScene(Scene scene) {
        stage.setScene(scene);
    }

    /**
     * Method used to close a stage on a button click
     * @param event the click of a button
     */
    public void okBtnClick(ActionEvent event) {
        stage.close();
    }
}
