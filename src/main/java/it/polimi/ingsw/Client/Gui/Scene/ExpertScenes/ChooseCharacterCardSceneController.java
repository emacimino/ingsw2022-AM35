package it.polimi.ingsw.Client.Gui.Scene.ExpertScenes;

import it.polimi.ingsw.Client.Gui.Scene.CardMap;
import it.polimi.ingsw.Client.Gui.Scene.GenericSceneController;
import it.polimi.ingsw.Client.Gui.Scene.SceneController;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.NetworkUtilities.AskCharacterCardMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class ChooseCharacterCardSceneController extends GenericSceneController {
    private final Stage stage;
    private String characterCardSelected;
    private Map<Node, String> mapImageId = new HashMap<>();
    private Map<String, CharacterCard> characterCardMap;

    @FXML
    private Label characterLbl;
    @FXML
    private HBox characterCardBox;
    @FXML
    private Text coins1, coins2, coins3;

    public ChooseCharacterCardSceneController() {
        this.stage = new Stage();
        stage.setAlwaysOnTop(false);

    }

    public void setCharacters(Map<String, CharacterCard> characterCards) {
        this.characterCardMap = characterCards;
        loadCharacterCards();
    }


    public void setScene(Scene scene) {
        stage.setScene(scene);
    }

    public void displayOptions() {
        stage.showAndWait();
    }

    public void selectCharacterCard(String name) {
        characterCardSelected = name;
        characterLbl.setText("YOU HAVE SELECTED: " + characterCardSelected);
    }

    public void sendAskCharacterCard() {
        if (characterCardSelected != null) {
            notifyObserver(new AskCharacterCardMessage(characterCardSelected));
            stage.close();
        }
    }

    public void closeStage() {
        stage.close();
    }

    private void loadCharacterCards() {
        List<String> names = characterCardMap.keySet().stream().toList();
        List<Node> cards = characterCardBox.getChildren();
        setImageOnCard(CardMap.characterCardImageMap.get(names.get(0)), cards.get(0));
        mapImageId.put(cards.get(0), names.get(0));
        coins1.setText("cost: " + characterCardMap.get(names.get(0)).getCost());

        setImageOnCard(CardMap.characterCardImageMap.get(names.get(1)), cards.get(1));
        mapImageId.put(cards.get(1), names.get(1));
        coins2.setText("cost: " + characterCardMap.get(names.get(1)).getCost());

        setImageOnCard(CardMap.characterCardImageMap.get(names.get(2)), cards.get(2));
        mapImageId.put(cards.get(2), names.get(2));
        coins3.setText("cost: " + characterCardMap.get(names.get(2)).getCost());

        setCharacterButton();
    }

    private void setCharacterButton() {
        for (Node card : mapImageId.keySet()) {
            assert card != null;
            card.setVisible(true);
            card.setDisable(false);
            card.setOnMouseClicked(MouseEvent -> selectCharacterCard(mapImageId.get(card)));
        }
    }

    private void setImageOnCard(String name, Node n) {
        javafx.scene.image.Image image = new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResource(name)).toExternalForm());
        ((ImageView) n).setImage(image);
        n.setDisable(false);
        n.setVisible(true);
    }

    public void seeCardSelected(){
        if(!(characterCardSelected == null)){
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/fxml/expertScenes/card.fxml"));
            Parent parent;
            try {
                parent = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            CardController cardController = loader.getController();
            Scene cardScene = new Scene(parent);
            cardController.setCharacterCard(characterCardMap.get(characterCardSelected));
            cardController.addAllObserver(getObservers());
            cardController.setScene(cardScene);
            cardController.displayOptions();
        }
    }

}
