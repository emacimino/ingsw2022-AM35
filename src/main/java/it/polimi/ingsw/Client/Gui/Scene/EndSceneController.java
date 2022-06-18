package it.polimi.ingsw.Client.Gui.Scene;

import it.polimi.ingsw.NetworkUtilities.Message.DisconnectMessage;
import it.polimi.ingsw.NetworkUtilities.Message.NewMatchMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.List;


public class EndSceneController extends GenericSceneController{
    @FXML
    Button quitBtn, restartBtn;
    @FXML
    Label messageLbl;

    public void quit(ActionEvent event){
        System.exit(0);
    }

    public void restartMatch(ActionEvent event){
        notifyObserver(new NewMatchMessage());
    }

    public void setWinMessage(List<String> winners){
        messageLbl.setText("Congratulations " + winners.stream().reduce((s,t) -> s + ", " + t) +" \n! You have won!");
    }

    public void setLoseMessage(List<String> winners){
        messageLbl.setText("You have lose to "+ winners.stream().reduce((s,t) -> s + ", " + t) +" ! \n Don't worry the important thing is to participate !");
    }
}
