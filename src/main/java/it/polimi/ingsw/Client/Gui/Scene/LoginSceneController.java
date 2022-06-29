package it.polimi.ingsw.Client.Gui.Scene;

import it.polimi.ingsw.NetworkUtilities.LoginResponse;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginSceneController extends GenericSceneController implements Initializable {
//questa classe dovrà fare il cambio di scena solo se effettivamente il match viene creato: e ricevo di conseguenza un

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


    @FXML
    public void onIsExpert(){
        isExpert = isExpertCheck.isSelected();
    }

    @FXML
    private void onBackToMenuBtnClick(Event event) {
        SceneController.changeRootPane(getObservers(), event, "serverInfo.fxml");
    }

    @FXML
    public void loginBtnClick(){
        String username = textFieldUsername.getText();
        notifyObserver(new LoginResponse(username, numOfPlayers, isExpert));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textFieldUsername.setText("Cami");
        choiceBoxNumber.getItems().addAll(numberOfPlayers);
        choiceBoxNumber.setOnAction(this::getNumberOFPlayers);
    }

    @FXML
    public void getNumberOFPlayers(ActionEvent event){
        numOfPlayers = choiceBoxNumber.getValue();
    }
}
