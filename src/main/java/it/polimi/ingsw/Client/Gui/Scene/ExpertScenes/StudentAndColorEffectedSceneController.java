package it.polimi.ingsw.Client.Gui.Scene.ExpertScenes;

import it.polimi.ingsw.Client.Gui.Scene.BoardPanelController;
import it.polimi.ingsw.Client.Gui.Scene.GenericSceneController;
import it.polimi.ingsw.Client.Gui.Scene.SceneController;
import it.polimi.ingsw.Client.RemoteModel;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.NetworkUtilities.PlayCharacterMessage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A class that handles the student and colors effect on the scene
 */
public class StudentAndColorEffectedSceneController extends GenericSceneController {


    @FXML
    private StackPane boardStack;
    private Map<Integer, Student> studentMap = new HashMap<>();
    private BoardPanelController boardPanelController;
    private CharacterCard characterCard;

    /**
     * This method is used to set the board
     */
    public void setBoard() {
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
        boardPanelController.setCurrentBoard();
        boardStack.getChildren().add(node);
    }

    /**
     * This method is used to load the movable students
     * @param studentsMovable a map of students
     */
    public void loadStudentsMovable(Map<Integer, Student> studentsMovable) {
        studentMap = studentsMovable;
        boardPanelController.setMovableStudentOnEntrance(studentMap.values().stream().toList());
    }


    /**
     * This method is used to enable expert mode
     */
    private void enableExpert(){
        boardPanelController.enableExpert();
    }

    /**
     * This method is used to switch to next move
     */
    public void selectionComplete(){
        nextMove();
    }

    /**
     * This method is used to go on to the next move
     */
    private void nextMove() {
        System.out.println("in student effect scene: in remote stud from entrance" + remoteModel.getStudentFromEntrance());
        System.out.println("color of student " );
        for(Integer i : remoteModel.getStudentFromEntrance()){
            System.out.println(remoteModel.getStudentsOnEntranceMap().get(i));
        }
        System.out.println("in student effect scene: in remote color" + remoteModel.getColorSelected());
        switch (characterCard.getName()) {
            case "Banker", "Chef" -> notifyObserver(new PlayCharacterMessage(characterCard.getName(), 13, null, null, remoteModel.getColorSelected()));
            case "Minstrel" -> notifyObserver(new PlayCharacterMessage(characterCard.getName(), 13, remoteModel.getStudentFromEntrance(), null, remoteModel.getColorSelected()));
            case "Jester" ->notifyObserver(new PlayCharacterMessage(characterCard.getName(), 13, remoteModel.getStudentFromEntrance(), remoteModel.getStudentsFromCard(), null));
        }
        clearSelection();
    }

    /**
     * This method is used to clear selection
     */
    public void clearSelection(){
        remoteModel.clearSelection();
        boardPanelController.clearSelection();
    }

    /**
     * update the remote model
     * @param remoteModel remote model updated
     */
    @Override
    public void setRemoteModel(RemoteModel remoteModel) {
        this.remoteModel = remoteModel;
        setBoard();
        loadStudentsMovable(remoteModel.getStudentsOnEntranceMap());
        enableExpert();
        characterCard = remoteModel.getCharacterCardMap().get(remoteModel.getActiveCharacterCard());

    }
}
