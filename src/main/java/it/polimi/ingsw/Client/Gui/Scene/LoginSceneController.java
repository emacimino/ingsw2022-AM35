package it.polimi.ingsw.Client.Gui.Scene;

import it.polimi.ingsw.NetworkUtilities.Message.LoginResponse;
import it.polimi.ingsw.NetworkUtilities.Message.ServerInfoMessage;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginSceneController extends GenericSceneController implements Initializable {
//questa classe dovrà fare il cambio di scena solo se effettivamente il match viene creato: e ricevo di conseguenza un
    //input infoGameMessage
    @FXML
    private Button backBtn;
    @FXML
    private Button loginBtn;
    @FXML
    private Label userLabel;
    @FXML
    private ChoiceBox<Integer> choiceBoxNumber;
    @FXML
    private TextField textFieldUsername;
    @FXML
    private CheckBox isExpertCheck;

    private Integer[] numberOfPlayers = {2, 3, 4};
    private String username;
    private int numOfPlayers;
    private boolean isExpert = false;

    @FXML
    public void usernameInput(Event event){
        username = textFieldUsername.getText();
        System.out.println(username);
    }

    @FXML
    public void onIsExpert(ActionEvent event){
        isExpert = isExpertCheck.isSelected();
    }

    @FXML
    private void onBackToMenuBtnClick(Event event) {
        SceneController.changeRootPane(getObservers(), event, "serverInfo.fxml");
    }

    @FXML
    public void loginBtnClick(Event event){
        notifyObserver(new LoginResponse(username, numOfPlayers, isExpert));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBoxNumber.getItems().addAll(numberOfPlayers);
        choiceBoxNumber.setOnAction(this::getNumberOFPlayers);
    }

    @FXML
    public void getNumberOFPlayers(ActionEvent event){
        numOfPlayers = choiceBoxNumber.getValue();
    }
}
