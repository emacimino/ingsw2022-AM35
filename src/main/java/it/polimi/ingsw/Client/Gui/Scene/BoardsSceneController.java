package it.polimi.ingsw.Client.Gui.Scene;

import it.polimi.ingsw.Model.Wizard.Board;
import it.polimi.ingsw.Model.Wizard.Wizard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.List;

public class BoardsSceneController extends GenericSceneController {
    @FXML
    private GridPane sky;
    @FXML
    private Button backBtn;

    public void setBoards(List<Wizard> wizards) {
        for (int i = 0; i < wizards.size(); i++) {
            if (i == 0) {
                addBoard(0, 0, wizards.get(i).getBoard(), wizards.get(i).getUsername());
            } else if (i == 1) {
                addBoard(1, 0, wizards.get(i).getBoard(), wizards.get(i).getUsername());
            } else if (i == 2) {
                System.out.println("tre giocatori in boardsSceneController");
                addBoard(0, 2, wizards.get(i).getBoard(), wizards.get(i).getUsername());
            } else if (i == 3) {
                addBoard(1, 2, wizards.get(i).getBoard(), wizards.get(i).getUsername());
            }
        }
    }

    private void addBoard(int x, int y, Board board, String username) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneController.class.getResource("/fxml/singleBoard.fxml"));
        Node node = null;
        try {
            node = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BoardPanelController controller = loader.getController();
        controller.setBoard(board, username);
        sky.add(node, x, y);
    }

    public void goBack(ActionEvent event) {
        SceneController.backScene();
    }

}
