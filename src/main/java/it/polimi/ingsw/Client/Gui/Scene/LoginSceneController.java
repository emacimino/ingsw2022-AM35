package it.polimi.ingsw.Client.Gui.Scene;

import it.polimi.ingsw.NetworkUtilities.LoginResponse;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * A class used to display the login screen
 */
public class LoginSceneController extends GenericSceneController implements Initializable {


    @FXML
    public Button backBtn;
    @FXML
    public Button loginBtn;
    @FXML
    public Label userLabel;
    @FXML
    public ChoiceBox<Integer> choiceBoxNumber;
    @FXML
    public TextField textFieldUsername;
    @FXML
    public CheckBox isExpertCheck;

    private final Integer[] numberOfPlayers = {2, 3, 4};
    private int numOfPlayers;
    private boolean isExpert = false;



    /**
     * This method is used to check if the game is in expert mode
     * @param event
     */
    @FXML
    public void onIsExpert(ActionEvent event){
        isExpert = isExpertCheck.isSelected();
    }

    /**
     * This method is used to go back to the menu
     * @param event user input
     */
    @FXML
    private void onBackToMenuBtnClick(Event event) {
        SceneController.changeRootPane(getObservers(), event, "serverInfo.fxml");
    }

    /**
     * This method is used to send the login information on a button press
     */
    @FXML
    public void loginBtnClick(){
        String username = textFieldUsername.getText();
        notifyObserver(new LoginResponse(username, numOfPlayers, isExpert));
    }

    /**
     * This method is used to add the player to the match
     * @param url resources
     * @param resourceBundle resources
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textFieldUsername.setText("Cami");
        choiceBoxNumber.getItems().addAll(numberOfPlayers);
        choiceBoxNumber.setOnAction(this::getNumberOFPlayers);
    }

    /**
     * This method is used to get the number of players
     * @param event user input
     */
    @FXML
    public void getNumberOFPlayers(ActionEvent event){
        numOfPlayers = choiceBoxNumber.getValue();
    }
}
