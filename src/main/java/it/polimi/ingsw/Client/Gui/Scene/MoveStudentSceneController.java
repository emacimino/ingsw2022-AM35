package it.polimi.ingsw.Client.Gui.Scene;

import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.Board;

import it.polimi.ingsw.NetworkUtilities.Message.MoveStudentMessage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.*;

import javafx.scene.transform.Rotate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MoveStudentSceneController extends GenericSceneController {

    @FXML
    private GridPane sky;

    private Map<Integer, Archipelago> archipelagoMap = new HashMap<>();
    private Map<Integer, Student> studentMap = new HashMap<>();
    private Board board;
    private Archipelago archipelagoSelected;
    private BoardPanelController boardPanelController;
    private Boolean ok = false;

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
                    archipelagoSelected = archipelago;
                    moveStudent();
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
        boardPanelController.setBoard(board, "My");
        sky.add(node, 1, 1);
        ok = true;

    }

    public void loadStudentsMovable(Map<Integer, Student> studentsMovable) {
        while(!ok){
           ;
        }
        studentMap = studentsMovable;
        boardPanelController.setMovableStudentOnEntrance(studentMap);
    }

    private void moveStudent() {
        System.out.println("DO something:" );
        Integer indexStud = null, indexArch = null;
        for (Integer i : studentMap.keySet()) {
            if (studentMap.get(i).equals(boardPanelController.getStudentToMove())) {
                indexStud = i;
            }
        }
        for (Integer i : archipelagoMap.keySet()) {
            if (archipelagoMap.get(i).equals(archipelagoSelected)) {
                indexArch = i;
            }
        }
        System.out.println(indexArch + " , " + indexStud);
        if (indexStud != null && indexArch != null) {
            notifyObserver(new MoveStudentMessage(indexStud, indexArch));
        }
    }


}
