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


public class AssistantSceneController extends GenericSceneController {
    private final Stage stage;
    private Map<AssistantsCards, String> mapImageId ;
    private List<AssistantsCards> assistantsCardsOptions = new ArrayList<>();
    private AssistantsCards assistantsCardSelected;

    @FXML
    private Label assLbl;
    @FXML
    private Button confirmBtn;
    @FXML
    private ImageView ASSISTANT_ONE, ASSISTANT_TWO, ASSISTANT_THREE, ASSISTANT_FOUR, ASSISTANT_FIVE,
            ASSISTANT_SIX, ASSISTANT_SEVEN, ASSISTANT_EIGHT, ASSISTANT_NINE, ASSISTANT_TEN;
    @FXML
    private HBox oneToFive;
    @FXML
    private HBox sixToTen;


    public void setAssistants(List<AssistantsCards> assistantsCards) {
        mapImageId = createMapButton();
        assistantsCardsOptions.addAll(assistantsCards);
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

    public AssistantSceneController() {
        stage = new Stage();
        stage.setAlwaysOnTop(false);
    }

    public void setScene(Scene scene) {
        stage.setScene(scene);
    }

    public void displayOptions() {
        stage.showAndWait();
    }

    public void selectAssistantCard(AssistantsCards assistantsCards) {
        assistantsCardSelected = assistantsCards;
        assLbl.setText("YOU HAVE SELECTED: " + assistantsCardSelected);
    }

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


    public void sendAssistant(ActionEvent event) {
        if(assistantsCardSelected != null) {
            notifyObserver(new AssistantCardMessage(assistantsCardSelected));
            stage.close();
        }
    }
}
