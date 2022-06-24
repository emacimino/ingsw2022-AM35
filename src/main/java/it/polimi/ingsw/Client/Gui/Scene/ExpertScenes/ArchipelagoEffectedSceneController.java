package it.polimi.ingsw.Client.Gui.Scene.ExpertScenes;

import it.polimi.ingsw.Client.Gui.Scene.ArchipelagoPanelController;
import it.polimi.ingsw.Client.Gui.Scene.GenericSceneController;
import it.polimi.ingsw.Client.Gui.Scene.SceneController;
import it.polimi.ingsw.Client.RemoteModel;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.NetworkUtilities.PlayCharacterMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ArchipelagoEffectedSceneController extends GenericSceneController {

    @FXML
    private GridPane sky;
    @FXML
    private Label archipelagoSelectedLbl;

    private Map<Integer, Archipelago> archipelagoMap = new HashMap<>();
    private Archipelago archipelagoSelected;
    private CharacterCard characterCard;


    private void initialize(){
        setArchipelagos(remoteModel.getArchipelagosMap());
    }
    public void setArchipelagos(Map<Integer, Archipelago> archipelagos) {
        archipelagoMap = archipelagos;
        {
            int indexRow = 0, indexColumn = 0;
            for (Archipelago a : archipelagos.values()) {
                if (indexRow == 0 && indexColumn < 5) {
                    try {
                        loadArchipelagos(a, indexRow, indexColumn);
                        indexColumn++;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (indexRow == 0 && indexColumn - 1 == 4) {
                    indexColumn = indexColumn - 1;
                    indexRow = 1;
                    try {
                        loadArchipelagos(a, indexRow, indexColumn);
                        indexRow = 2;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (indexRow == 2 && indexColumn >= 0) {
                    try {
                        loadArchipelagos(a, indexRow, indexColumn);
                        indexColumn = indexColumn - 1;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (indexRow != 1 && indexColumn + 1 == 0) {
                    indexColumn++;
                    try {
                        indexRow = 1;
                        loadArchipelagos(a, indexRow, indexColumn);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


    }

    private void loadArchipelagos(Archipelago archipelago, int row, int column) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneController.class.getResource("/fxml/archipelago.fxml"));
        Node node = loader.load();
        ArchipelagoPanelController controller = loader.getController();
        controller.setArchipelago(archipelago);
        node.setOnMouseClicked(MouseEvent -> {
                    if (archipelago == archipelagoSelected) {
                        archipelagoSelected = null;
                        archipelagoSelectedLbl.setText("");
                    } else {
                        archipelagoSelected = archipelago;
                        archipelagoSelectedLbl.setText("You have selected \n the " + getArchipelagoIndex(archipelago) + " archipelago");
                    }
                }
        );
        node.setDisable(false);
        sky.add(node, column, row);
    }

    private Integer getArchipelagoIndex(Archipelago archipelago){
        Integer indexArch = null;
        for (Integer i : archipelagoMap.keySet()) {
            if (archipelagoMap.get(i).equals(archipelago)) {
                indexArch = i;
            }
        }
        return indexArch;
    }

    public void selectArchipelago(ActionEvent event){
        remoteModel.setArchipelagoSelected(getArchipelagoIndex(archipelagoSelected));
        nextMove();
    }

    private void nextMove() {
        switch (characterCard.getName()) {
            case "Friar" -> notifyObserver(new PlayCharacterMessage(characterCard.getName(), remoteModel.getArchipelagoSelected(), null, remoteModel.getStudentSelected(), null));
            case "Messenger", "Herbalist" -> notifyObserver(new PlayCharacterMessage(characterCard.getName(), remoteModel.getArchipelagoSelected(), null, null, null));
        }
    }

    @Override
    public void setRemoteModel(RemoteModel remoteModel) {
        this.remoteModel = remoteModel;
        characterCard = remoteModel.getCharacterCardMap().get(remoteModel.getActiveCharacterCard());
        initialize();
    }

    public void goToBoards(ActionEvent event) {
        SceneController.showWizardsBoards(getObservers());

    }
}
