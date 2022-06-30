package it.polimi.ingsw.Client.Gui.Scene;

import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Cloud;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.NetworkUtilities.CloudMessage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A class used to display the UI during the game
 */
public class GameSceneController extends GenericSceneController {
    private Game game;
    private int indexRow = 0;
    private int indexColumn = 0;
    private final HBox cloudsOne = new HBox();
    private final HBox cloudsTwo = new HBox();
    private boolean chooseCloud = false;
    private final Map<Integer, ArchipelagoPanelController> archipelagoControllerMap = new HashMap<>();
    private final Map<ArchipelagoPanelController, Node> archipelagoNodeMap = new HashMap<>();
    private final Map<Integer, CloudPanelController> cloudControllerMap = new HashMap<>();
    private final Map<Node, CloudPanelController> cloudNodeMap = new HashMap<>();
    @FXML
    public GridPane sky;
    @FXML
    public HBox characterCardBox;
    @FXML
    public HBox assistantBox;

    /**
     * Set method for initializing a game
     * @param game the current game
     */
    public void setGame(Game game) {
        this.game = game;
        cloudControllerMap.clear();
        archipelagoControllerMap.clear();
        initialize();
    }


    /**
     * This method is used to initialize all the game variables in the GUI
     */
    private void initialize() {
        {
            for (Integer i = 0; i < game.getArchipelagos().size(); i++) {
                Archipelago a = game.getArchipelagos().get(i);
                if (indexRow == 0 && indexColumn < 5) {
                    try {
                        loadArchipelagos(i, a, indexRow, indexColumn);
                        indexColumn++;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (indexRow == 0 && indexColumn - 1 == 4) {
                    indexColumn = indexColumn - 1;
                    indexRow = 1;
                    try {
                        loadArchipelagos(i, a, indexRow, indexColumn);
                        indexRow = 2;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (indexRow == 2 && indexColumn >= 0) {
                    try {
                        loadArchipelagos(i, a, indexRow, indexColumn);
                        indexColumn = indexColumn - 1;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (indexRow != 1 && indexColumn + 1 == 0) {
                    indexColumn++;
                    try {
                        indexRow = 1;
                        loadArchipelagos(i, a, indexRow, indexColumn);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        {

            indexRow = 1;
            indexColumn = 1;
            int pos = 1;
            HBox hBox = cloudsOne;
            sky.add(cloudsOne, indexColumn, indexRow);
            for (int i = 0; i < game.getClouds().size(); i++) {
                Cloud cloud = game.getClouds().get(i);
                try {
                    loadCloud(i + 1, cloud, pos, hBox);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (pos == 1) {
                    pos = 2;
                } else if (indexColumn != 3) {
                    pos = 1;
                    indexColumn = 3;
                    hBox = cloudsTwo;
                    sky.add(cloudsTwo, indexColumn, indexRow);
                }

            }
        }

        for (int i = 0; i < game.getAssistantsCardsPlayedInRound().size(); i++)
            loadAssistantCard(game.getAssistantsCardsPlayedInRound().get(i), i);
    }
    /**
     * This method is used to load an archipelago on screen
     * @param i of the archipelago to load
     * @param archipelago the archipelago to load
     * @param row coordinates
     * @param column coordinates
     * @throws IOException if some input is not valid
     */
    private void loadArchipelagos(Integer i, Archipelago archipelago, int row, int column) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneController.class.getResource("/fxml/archipelago.fxml"));
        Node node = loader.load();
        ArchipelagoPanelController controller = loader.getController();
        controller.setArchipelago(archipelago);
        sky.add(node, column, row);
        archipelagoControllerMap.put(i + 1, controller);
        archipelagoNodeMap.put(controller, node);

    }

    /**
     * This method is used to load a cloud on screen
     * @param i of the cloud to load
     * @param cloud the cloud to load
     * @param pos position on screen
     * @param hBox a box containing the students
     * @throws IOException if some input is not valid
     */
    private void loadCloud(Integer i, Cloud cloud, int pos, HBox hBox) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneController.class.getResource("/fxml/cloud.fxml"));
        Node node = loader.load();
        CloudPanelController controller = loader.getController();
        controller.setCloud(cloud);
        if (chooseCloud) {
            node.setOnMouseClicked(mouseEvent -> selectCloud(i));
        }
        VBox vBox;
        Node tmp = new Pane();
        tmp.setVisible(false);
        if (pos == 1) {
            vBox = new VBox();
            vBox.setAlignment(Pos.BOTTOM_CENTER);
            vBox.getChildren().add(tmp);
            vBox.getChildren().add(node);
            hBox.getChildren().add(vBox);
        } else {
            vBox = new VBox();
            vBox.setAlignment(Pos.TOP_CENTER);
            vBox.getChildren().add(node);
            vBox.getChildren().add(tmp);
            hBox.getChildren().add(vBox);
        }
        cloudControllerMap.put(i, controller);
        cloudNodeMap.put(node, controller);
    }

    /**
     * A method used to quit and close the application

     */
    public void quit() {
        System.exit(0);
    }

    public void loadCharacterCards(Map<String, CharacterCard> characterCards) {
        List<String> names = characterCards.keySet().stream().toList();
        List<Node> cards = characterCardBox.getChildren();
        setImageOnCard(CardMap.characterCardImageMap.get(names.get(0)), cards.get(0));
        setImageOnCard(CardMap.characterCardImageMap.get(names.get(1)), cards.get(1));
        setImageOnCard(CardMap.characterCardImageMap.get(names.get(2)), cards.get(2));
    }

    /**
     * This method is used to load an image on a card
     * @param name card name
     * @param n node representing the card
     */
    private void setImageOnCard(String name, Node n) {
        javafx.scene.image.Image image = new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResource(name)).toExternalForm());
        ((ImageView) n).setImage(image);
        n.setVisible(true);
    }

    /**
     * This method is used to load an assistant card on screen
     * @param assistantsCards assistant card to load
     * @param pos position on screen
     */
    public void loadAssistantCard(AssistantsCards assistantsCards, int pos) {
        List<Node> cards = assistantBox.getChildren();
        setImageOnCard(CardMap.assistantsCardsImageMap.get(assistantsCards), cards.get(pos));
    }

    /**
     * This method is used to open a board
     */
    public void goToBoards() {
        SceneController.showWizardsBoards(getObservers());

    }

    /**
     * This method is used to enable the selection of the cloud
     */
    public void enableCloud() {
        chooseCloud = true;
        for(Node n : cloudNodeMap.keySet()){
            n.setDisable(false);
            CloudPanelController c = cloudNodeMap.get(n);
            n.setOnMouseClicked(mouseEvent -> selectCloud(getIntegerCloudControllerPanel(c)));

        }
    }


    private int getIntegerCloudControllerPanel(CloudPanelController cloudPanelController){
        Integer index = null;
        for (Integer i : cloudControllerMap.keySet()) {
            if (cloudControllerMap.get(i).equals(cloudPanelController)) {
                index = i;
            }
        }
        return index;
    }

    private void selectCloud(Integer cloud) {
        for(Node n : cloudNodeMap.keySet()){
            n.setDisable(true);

        }
        notifyObserver(new CloudMessage(cloud));

    }


    public void updateArchipelagoOnInfoGame(List<Archipelago> archipelagos) { //da 0 a 11 max
        for (int i = 0; i < archipelagos.size(); i++) {
            ArchipelagoPanelController controller = archipelagoControllerMap.get(i+1);
            Archipelago a = archipelagos.get(i);
            controller.setArchipelago(a);
        }
        for (int i = archipelagos.size() ; i < archipelagoControllerMap.size(); i++) {
            ArchipelagoPanelController controller = archipelagoControllerMap.get(i+1);
            sky.getChildren().remove(archipelagoNodeMap.get(controller));
        }
    }

    public void updateCloudsOnInfoGame(List<Cloud> clouds) {
        for (int i = 1; i <= clouds.size(); i++) {
            CloudPanelController controller = cloudControllerMap.get(i);
            Cloud c = clouds.get(i-1);
            controller.setCloud(c);
        }
        for(Node n : cloudNodeMap.keySet())
            n.setDisable(true);

    }

    public void updateAssistantCardOnInfoGame(List<AssistantsCards> assistantsCardsPlayedInRound) {
        for(Node n : assistantBox.getChildren())
            n.setVisible(false);
        for (int i = 0; i < assistantsCardsPlayedInRound.size(); i++)
            loadAssistantCard(assistantsCardsPlayedInRound.get(i), i);
    }
}
