package it.polimi.ingsw.Client.Gui.Scene;

import it.polimi.ingsw.Client.CLIENT2.ClientController;
import it.polimi.ingsw.NetworkUtilities.Message.ServerInfoMessage;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class ServerInfoSceneController extends GenericSceneController {

    @FXML //this annotation porterà tutti i valori del file fxml che ha menuSceneController
    private Button playBtn;
    @FXML
    private Button quitBtn;
    @FXML
    private TextField ipAddressField;
    @FXML
    private TextField portField;
    @FXML
    public void initialize() {
        quitBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> System.exit(0));
    }

    public void switchToLoginScene(Event event){
        String ipAddress = ipAddressField.getText();
        String port = portField.getText();
        //devo settare il server
        boolean isValidIpAddress = ClientController.isValidIpAddress(ipAddress);
        boolean isValidPort = ClientController.isValidPort(port);

        if (isValidIpAddress && isValidPort) {
            notifyObserver(new ServerInfoMessage(ipAddress, port));
        }
       //  questa istruzione dovrebbe farla clientController in caso le info sono corrette
       // SceneController.changeRootPane(getObservers(), event, "login.fxml");
    }


}

