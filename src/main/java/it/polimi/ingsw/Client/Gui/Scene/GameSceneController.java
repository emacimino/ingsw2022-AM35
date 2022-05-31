package it.polimi.ingsw.Client.Gui.Scene;

import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Cloud;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GameSceneController extends GenericSceneController{
    private Game game;
    private int indexRow = 0;
    private int indexColumn = 0;
    public void setGame(Game game) {
        this.game = game;
        initialize();
    }
    @FXML
    private GridPane sky;
    @FXML
    private Button boardBtn, quitBtn;
    @FXML
    private HBox characterCardBox;
    @FXML
    private HBox assistantBox;


    private void initialize() {
        { for(Archipelago a : game.getArchipelagos()) {
            if(indexRow == 0 && indexColumn < 5) {
                try {
                    loadArchipelagos(a, indexRow, indexColumn);
                    indexColumn++;
                }catch (IOException e ) {
                    e.printStackTrace();
                }
            }else if(indexRow == 0 && indexColumn - 1 == 4){
                indexColumn = indexColumn -1;
                indexRow = 1;
                try {
                    loadArchipelagos(a, indexRow, indexColumn);
                    indexRow = 2;
                }catch (IOException e ) {
                    e.printStackTrace();
                }
            }else if(indexRow == 2 && indexColumn >= 0){
                try {
                    loadArchipelagos(a, indexRow, indexColumn);
                    indexColumn = indexColumn -1;
                }catch (IOException e ) {
                    e.printStackTrace();
                }
            }else if(indexRow != 1 && indexColumn + 1 == 0){
                indexColumn ++;
                try {
                    indexRow = 1;
                    loadArchipelagos(a, indexRow, indexColumn);
                }catch (IOException e ) {
                    e.printStackTrace();
                }
            }
        }}
        {indexRow = 1;
        indexColumn = 1;
        int pos = 1;
        HBox hBox = new HBox();
        sky.add(hBox, indexColumn, indexRow);
        for(Cloud cloud : game.getClouds()){
            try {
                loadCloud(cloud, pos, hBox);
            }catch (Exception e){
                e.printStackTrace();
            }
            if(pos == 1){
                pos = 2;
            }else {
                pos = 1;
                indexColumn = 3;
                hBox = new HBox();
                sky.add(hBox, indexColumn, indexRow);
            }
        }}
        if(game.getAssistantsCardsPlayedInRound().size()>0){
            for(int i = 0; i < game.getAssistantsCardsPlayedInRound().size(); i++)
                loadAssistantCard(game.getAssistantsCardsPlayedInRound().get(i), i);
        }
    }

    private void loadArchipelagos (Archipelago archipelago, int row, int column) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneController.class.getResource("/fxml/archipelago.fxml"));
        Node node = loader.load();
        ArchipelagoPanelController controller = loader.getController();
        controller.setArchipelago(archipelago);
        sky.add(node, column, row);
    }

    private void loadCloud(Cloud cloud, int pos, HBox hBox) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneController.class.getResource("/fxml/cloud.fxml"));
        Node node = loader.load();
        CloudPanelController controller = loader.getController();
        controller.setCloud(cloud);
        VBox vBox;
        Node tmp = new Pane();
        tmp.setVisible(false);
        if(pos == 1) {
            vBox = new VBox();
            vBox.setAlignment(Pos.BOTTOM_CENTER);
            vBox.getChildren().add(tmp);
            vBox.getChildren().add(node);
            hBox.getChildren().add(vBox);
        }
        else {
            vBox = new VBox();
            vBox.setAlignment(Pos.TOP_CENTER);
            vBox.getChildren().add(node);
            vBox.getChildren().add(tmp);
            hBox.getChildren().add(vBox);
        }
    }

    public void quit(ActionEvent event){
        System.exit(0);
    }

    public void loadCharacterCards(Map<String, CharacterCard> characterCards){
        List<String> names = characterCards.keySet().stream().toList();
        List<Node> cards = characterCardBox.getChildren();
        setImageOnCard(CardMap.characterCardImageMap.get(names.get(0)), cards.get(0));
        setImageOnCard(CardMap.characterCardImageMap.get(names.get(1)), cards.get(1));
        setImageOnCard(CardMap.characterCardImageMap.get(names.get(2)), cards.get(2));
    }

    private void setImageOnCard(String name, Node n){
        javafx.scene.image.Image image = new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResource(name)).toExternalForm());
        ((ImageView)n).setImage(image);
        n.setDisable(false);
        n.setVisible(true);
    }

    public void loadAssistantCard(AssistantsCards assistantsCards, int pos){
        List<Node> cards = assistantBox.getChildren();
        setImageOnCard(CardMap.assistantsCardsImageMap.get(assistantsCards), cards.get(pos));
    }

    public void goToBoards(ActionEvent event){
        SceneController.showWizardsBoards(getObservers() ,game.getWizards());

    }


}
