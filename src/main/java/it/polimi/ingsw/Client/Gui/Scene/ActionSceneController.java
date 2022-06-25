package it.polimi.ingsw.Client.Gui.Scene;

import it.polimi.ingsw.Client.RemoteModel;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.Board;

import it.polimi.ingsw.NetworkUtilities.MoveMotherNatureMessage;
import it.polimi.ingsw.NetworkUtilities.MoveStudentMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ActionSceneController extends GenericSceneController {

    @FXML
    private GridPane sky;
    @FXML
    private Label archipelagoSelectedLbl;
    @FXML
    private Button moveBtn;
    @FXML
    private Button playCharacterBtn;

    private Map<Integer, Archipelago> archipelagoMap = new HashMap<>();
    private Map<Integer, Student> studentMap = new HashMap<>();
    private Archipelago archipelagoSelected;
    private BoardPanelController boardPanelController;
    private Boolean ok = false;
    private Boolean moveMN = false;

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
            if(archipelago == archipelagoSelected){
                archipelagoSelected = null;
                archipelagoSelectedLbl.setText("");
            }else{
                    archipelagoSelected = archipelago;
                archipelagoSelectedLbl.setText("You have selected \n the " + getArchipelagoIndex(archipelago) + " archipelago");
            }
        }
        );
        node.setDisable(false);
        sky.add(node, column, row);
    }

    public void setBoard(Board board) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneController.class.getResource("/fxml/singleBoard.fxml"));
        Node node = null;
        try {
            node = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        boardPanelController = loader.getController();
        boardPanelController.setRemoteModel(remoteModel);
        boardPanelController.setBoard(board, "My");
        sky.add(node, 1, 1);
        ok = true;

    }

    public void loadStudentsMovable(Map<Integer, Student> studentsMovable) {
        while(!ok){}
        studentMap = studentsMovable;
        boardPanelController.setMovableStudentOnEntrance(studentMap);
    }

    public void move() {
        Integer indexStud, indexArch;
        if(moveMN){
            indexArch = getArchipelagoIndex(archipelagoSelected);
            if(indexArch != null){
                notifyObserver(new MoveMotherNatureMessage(indexArch));
            }
        }else {
            Student student = boardPanelController.getStudentToMove();
            indexStud = getStudentIndex(student);
            indexArch = getArchipelagoIndex(archipelagoSelected);
            System.out.println(indexArch + " , " + indexStud);
            if (indexStud != null) {
                notifyObserver(new MoveStudentMessage(indexStud, indexArch));
            }
        }
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
    private Integer getStudentIndex(Student student){
        Integer indexStud = null;
        for (Integer i : studentMap.keySet()) {
            if (studentMap.get(i).equals(student)) {
                indexStud = i;
            }
        }
        return indexStud;
    }

    public void goToBoards() {
        Platform.runLater(() -> SceneController.showWizardsBoards(getObservers()));

    }

    public void setMoveMN(Boolean moveMN) {
        this.moveMN = moveMN;
        moveBtn.setText("Move Mother Nature");
    }

    public void setExpert(){
        playCharacterBtn.setDisable(false);
        playCharacterBtn.setVisible(true);
    }

    public void goToScenePlayCharacter(){
        Platform.runLater(() -> SceneController.showCharacterCardsOption(getObservers()));
    }

    @Override
    public void setRemoteModel(RemoteModel remoteModel) {
        this.remoteModel = remoteModel;
        setBoard(remoteModel.getCurrentBoard());
        setArchipelagos(remoteModel.getArchipelagosMap());
        loadStudentsMovable(remoteModel.getStudentsOnEntranceMap());
    }
}
