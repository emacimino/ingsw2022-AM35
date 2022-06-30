package it.polimi.ingsw.Client.Gui.Scene;

import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.NetworkUtilities.AssistantCardMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.util.*;

/**
 * Class used to control the assistant cards display
 */
public class AssistantSceneController extends GenericSceneController {
    private final Stage stage;
    private Map<AssistantsCards, String> mapImageId ;
    private AssistantsCards assistantsCardSelected;

    @FXML
    public Label assLbl;
    @FXML
    public HBox oneToFive;
    @FXML
    public HBox sixToTen;

    /**
     * Method used to set the assistant cards
     * @param assistantsCards a list of the assistant cards in game
     */
    public void setAssistants(List<AssistantsCards> assistantsCards) {
        mapImageId = createMapButton();
        List<Node> nodes;
        Node card;
        HBox hBox;
        for (AssistantsCards a : assistantsCards) {
            if (a.getValue() < 6) {
                hBox = oneToFive;
            } else
                hBox = sixToTen;
            nodes = hBox.getChildren();
            card = null;
            try {
                card = nodes.stream().filter(n -> n.getId().equals(mapImageId.get(a))).findFirst().get();
            }catch (NullPointerException | NoSuchElementException e){
                e.printStackTrace();
            }
            assert card != null;
            card.setVisible(true);
            card.setDisable(false);
            card.setOnMouseClicked(MouseEvent -> selectAssistantCard(a));
        }
    }

    /**
     * A method that sets a new stage
     */
    public AssistantSceneController() {
        stage = new Stage();
        stage.setAlwaysOnTop(false);
    }

    /**
     * A method used to set the scene
     * @param scene the new scene
     */
    public void setScene(Scene scene) {
        stage.setScene(scene);
    }

    /**
     * A method used to display the options
     */
    public void displayOptions() {
        stage.showAndWait();
    }

    /**
     * Method used to select the assistant card
     * @param assistantsCards assistant card selected
     */
    public void selectAssistantCard(AssistantsCards assistantsCards) {
        assistantsCardSelected = assistantsCards;
        assLbl.setText("YOU HAVE SELECTED: " + assistantsCardSelected);
    }

    /**
     * Method used to map cards and buttons
     * @return a map of cards and buttons associated to them
     */
    public Map<AssistantsCards, String> createMapButton() {
        Map<AssistantsCards, String> map = new HashMap<>();
        for (Node n : oneToFive.getChildren()) {
            switch (n.getId()) {
                case "ASSISTANT_ONE" -> map.put(AssistantsCards.CardOne, n.getId());
                case "ASSISTANT_TWO" -> map.put(AssistantsCards.CardTwo, n.getId());
                case "ASSISTANT_THREE" -> map.put(AssistantsCards.CardThree, n.getId());
                case "ASSISTANT_FOUR" -> map.put(AssistantsCards.CardFour, n.getId());
                case "ASSISTANT_FIVE" -> map.put(AssistantsCards.CardFive, n.getId());
            }
        }
        for (Node n : sixToTen.getChildren()) {
            switch (n.getId()) {
                case "ASSISTANT_SIX" -> map.put(AssistantsCards.CardSix, n.getId());
                case "ASSISTANT_SEVEN" -> map.put(AssistantsCards.CardSeven, n.getId());
                case "ASSISTANT_EIGHT" -> map.put(AssistantsCards.CardEight, n.getId());
                case "ASSISTANT_NINE" -> map.put(AssistantsCards.CardNine, n.getId());
                case "ASSISTANT_TEN" -> map.put(AssistantsCards.CardTen, n.getId());
            }
        }
        return map;
    }

    /**
     * Method used to send the assistant card
     * @param event An Event representing some type of action input by the player
     */
    public void sendAssistant(ActionEvent event) {
        if(assistantsCardSelected != null) {
            notifyObserver(new AssistantCardMessage(assistantsCardSelected));
            stage.close();
        }
    }
}
