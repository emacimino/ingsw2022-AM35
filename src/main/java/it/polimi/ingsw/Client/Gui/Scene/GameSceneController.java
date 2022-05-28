package it.polimi.ingsw.Client.Gui.Scene;

import it.polimi.ingsw.Model.FactoryMatch.Game;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GameSceneController extends GenericSceneController implements Initializable {
    private Game game;
    public void setGame(Game game) {
        this.game = game;
    }
    @FXML
    private GridPane sky;
    @FXML
    private ImageView a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12;
    @FXML
    private ImageView c1, c2, c3, c4;
    private List<ImageView> archipelagos, clouds;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        archipelagos.add(0, a1);
        archipelagos.add(1, a2);
        archipelagos.add(2, a3);
        archipelagos.add(3, a4);
        archipelagos.add(4, a5);
        archipelagos.add(5, a6);
        archipelagos.add(6, a7);
        archipelagos.add(7, a8);
        archipelagos.add(8, a9);
        archipelagos.add(9, a10);
        archipelagos.add(10, a11);
        archipelagos.add(11, a12);
        clouds.add(0, c1);
        clouds.add(1, c2);
        clouds.add(2,c3);
        clouds.add(3, c4);

        for(int i=0 ; i < game.getArchipelagos().size(); i++){
            archipelagos.get(i).setVisible(true);
        }



    }
}
