package it.polimi.ingsw.Client.Gui.Scene;

import it.polimi.ingsw.Client.ClientController;
import it.polimi.ingsw.NetworkUtilities.ServerInfoMessage;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;


public class ServerInfoSceneController extends GenericSceneController implements Initializable {

    @FXML //this annotation porterà tutti i valori del file fxml che ha menuSceneController
    public Button playBtn;
    @FXML
    public Button quitBtn;
    @FXML
    public TextField ipAddressField;
    @FXML
    public TextField portField;
    @FXML
    public void initialize() {
        quitBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> System.exit(0));
    }

    public void switchToLoginScene(Event event){
        String ipAddress = ipAddressField.getText();
        String port = portField.getText();
        boolean isValidIpAddress = ClientController.isValidIpAddress(ipAddress);
        boolean isValidPort = ClientController.isValidPort(port);

        if (isValidIpAddress && isValidPort) {
            notifyObserver(new ServerInfoMessage(ipAddress, port));
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ipAddressField.setText("127.0.0.1");
        portField.setText("1234");
    }
}

