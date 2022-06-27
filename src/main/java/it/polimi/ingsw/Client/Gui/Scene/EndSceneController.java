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

/**
 * A class that is used to end a match on the GUI
 */
public class EndSceneController extends GenericSceneController{
    @FXML
    Button quitBtn, restartBtn;
    @FXML
    Label messageLbl;
    private List<String> winners;

    /**
     * This method is used to leave a game and close the GUI
     * @param event a user input
     */
    public void quit(ActionEvent event){
        System.exit(0);
    }

    /**
     * This method is used to restart the match
     * @param event a user input
     */
    public void restartMatch(ActionEvent event){
        if(!winners.equals(Optional.empty()))
            notifyObserver(new NewMatchMessage());
        else
            Platform.runLater(() ->SceneController.changeRootPane(getObservers(), event, "menuScene.fxml"));
    }

    /**
     * This method is used to display a banner showing the winners of the match
     * @param winners a list containing all the winning players
     */
    public void setWinMessage(List<String> winners){
        this.winners = winners;
        messageLbl.setText("Congratulations " + winners.stream().reduce((s,t) -> s + ", " + t) +" \n! You have won!");
    }

    /**
     * This method is used to display a banner showing the losers of the match
     * @param winners a list containing all the losing players
     */
    public void setLoseMessage(List<String> winners){
        this.winners = winners;
        messageLbl.setText("You have lose to "+ winners.stream().reduce((s,t) -> s + ", " + t) +" ! \n Don't worry the important thing is to participate !");
    }
}
