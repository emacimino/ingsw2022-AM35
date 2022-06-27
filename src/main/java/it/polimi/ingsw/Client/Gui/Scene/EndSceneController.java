package it.polimi.ingsw.Client.Gui.Scene;

import it.polimi.ingsw.Model.FactoryMatch.Player;
import it.polimi.ingsw.NetworkUtilities.NewMatchMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.List;
import java.util.Optional;


public class EndSceneController extends GenericSceneController{
    @FXML
    Button quitBtn, restartBtn;
    @FXML
    Label messageLbl;
    private List<String> winners;

    public void quit(ActionEvent event){
        System.exit(0);
    }

    public void restartMatch(ActionEvent event){
        if(!winners.equals(Optional.empty()))
            notifyObserver(new NewMatchMessage());
        else
            Platform.runLater(() ->SceneController.changeRootPane(getObservers(), event, "menuScene.fxml"));
    }

    public void setWinMessage(List<String> winners){
        this.winners = winners;
        messageLbl.setText("Congratulations " + winners +" \n! You have won!");
    }

    public void setLoseMessage(List<String> winners){
        this.winners = winners;
        messageLbl.setText("You have lose to "+ winners+" ! \n Don't worry the important thing is to participate !");
    }
}
