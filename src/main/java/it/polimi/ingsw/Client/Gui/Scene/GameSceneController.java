package it.polimi.ingsw.Client.Gui.Scene;

import it.polimi.ingsw.Model.FactoryMatch.Game;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Cloud;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.io.IOException;

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

//modificare dinamicamente id

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




}
