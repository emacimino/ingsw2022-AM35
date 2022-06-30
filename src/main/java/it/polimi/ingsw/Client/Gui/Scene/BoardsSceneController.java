package it.polimi.ingsw.Client.Gui.Scene;

import it.polimi.ingsw.Client.RemoteModel;
import it.polimi.ingsw.Model.Wizard.Board;
import it.polimi.ingsw.Model.Wizard.Wizard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class used to control the board display on the GUI
 */
public class BoardsSceneController extends GenericSceneController {
    private final Stage stage;
    @FXML
    public GridPane sky;
    @FXML
    public Text teamsText;

    private final Map<String, BoardPanelController> boardPanelControllerMap = new HashMap<>();

    /**
     * Constructor of the class
     */
    public BoardsSceneController() {
        stage = new Stage();
        stage.setAlwaysOnTop(false);
    }

    /**
     * Method used to set the board
     * @param wizards a list of wizards
     */
    public void setBoards(List<Wizard> wizards) {
        for (int i = 0; i < wizards.size(); i++) {
            if (i == 0) {
                addBoard(0, 0, wizards.get(i).getBoard(), wizards.get(i).getUsername());
            } else if (i == 1) {
                addBoard(1, 0, wizards.get(i).getBoard(), wizards.get(i).getUsername());
            } else if (i == 2) {
                addBoard(0, 2, wizards.get(i).getBoard(), wizards.get(i).getUsername());
            } else if (i == 3) {
                addBoard(1, 2, wizards.get(i).getBoard(), wizards.get(i).getUsername());
            }
        }
    }

    /**
     * Method that adds the board to the player
     * @param x coordinates
     * @param y coordinates
     * @param board players' board
     * @param username  username of the player
     */
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
        controller.setRemoteModel(remoteModel);
        controller.setBoard(board, username);
        sky.add(node, x, y);
        boardPanelControllerMap.put(username, controller);
    }

    /**
     * Method used to close the stage
     *
     */
    public void goBack() {
        stage.close();
    }

    /**
     * Method used to switch scene
     * @param scene set the scene on the stage
     */
    public void setScene(Scene scene) {
        stage.setScene(scene);
    }

    /**
     * Method used to display a stage
     */
    public void display() {
        stage.showAndWait();
    }

    /**
     * update the remote model
     * @param remoteModel remote model updated
     */
    @Override
    public void setRemoteModel(RemoteModel remoteModel){
        this.remoteModel = remoteModel;
        setBoards(remoteModel.getGame().getWizards());
        if(!remoteModel.getTeamOne().isEmpty() || !remoteModel.getTeamTwo().isEmpty()){
            String p1 = remoteModel.getTeamOne().get(0).getUsername();
            String p2 = remoteModel.getTeamOne().get(1).getUsername();
            String p3 = remoteModel.getTeamTwo().get(0).getUsername();
            String p4 = remoteModel.getTeamTwo().get(1).getUsername();
            teamsText.setText("Team One: " + p1 + ", "+ p2 + "; Team Two: "+ p3 + ", "+ p4 );
        }
    }

    /**
     * Method used to update the boards
     * @param wizards a list of wizards in the game
     */
    public void updateBoards(List<Wizard> wizards){
        for(Wizard w : wizards ){
            BoardPanelController controller = boardPanelControllerMap.get(w.getUsername());
            controller.setBoard(w.getBoard(), w.getUsername());
        }
    }
}
