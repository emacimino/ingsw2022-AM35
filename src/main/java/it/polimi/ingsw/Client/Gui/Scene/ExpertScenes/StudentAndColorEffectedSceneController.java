package it.polimi.ingsw.Client.Gui.Scene.ExpertScenes;

import it.polimi.ingsw.Client.Gui.Scene.BoardPanelController;
import it.polimi.ingsw.Client.Gui.Scene.GenericSceneController;
import it.polimi.ingsw.Client.Gui.Scene.SceneController;
import it.polimi.ingsw.Client.RemoteModel;
import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.Board;
import it.polimi.ingsw.NetworkUtilities.PlayCharacterMessage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StudentAndColorEffectedSceneController extends GenericSceneController {


    @FXML
    private StackPane boardStack;
    private Map<Integer, Student> studentMap = new HashMap<>();
    private BoardPanelController boardPanelController;
    private Boolean ok = false;
    private CharacterCard characterCard;

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
        boardStack.getChildren().add(node);
        ok = true;

    }


    public void loadStudentsMovable(Map<Integer, Student> studentsMovable) {
        while(!ok){}
        studentMap = studentsMovable;
        boardPanelController.setMovableStudentOnEntrance(studentMap);
    }



    private void enableExpert(){
        boardPanelController.enableExpert();
    }

    public void selectionComplete(){
        nextMove();
    }

    private void nextMove() {
        System.out.println("in student effect scene: in remote stud from entrance" + remoteModel.getStudentFromEntrance());
        System.out.println("color of student " );
        for(Integer i : remoteModel.getStudentFromEntrance()){
            System.out.println(remoteModel.getStudentsOnEntranceMap().get(i));
        }
        System.out.println("in student effect scene: in remote color" + remoteModel.getColorSelected());
        switch (characterCard.getName()) {

            case "Minstrel" -> notifyObserver(new PlayCharacterMessage(characterCard.getName(), 13, remoteModel.getStudentFromEntrance(), null, remoteModel.getColorSelected()));
            case "Jester" ->notifyObserver(new PlayCharacterMessage(characterCard.getName(), 13, remoteModel.getStudentFromEntrance(), remoteModel.getStudentsFromCard(), null));
        }
        clearSelection();
    }

    public void clearSelection(){
        remoteModel.clearSelection();
        boardPanelController.clearSelection();
    }

    @Override
    public void setRemoteModel(RemoteModel remoteModel) {
        this.remoteModel = remoteModel;
        setBoard(remoteModel.getCurrentBoard());
        loadStudentsMovable(remoteModel.getStudentsOnEntranceMap());
        enableExpert();
        characterCard = remoteModel.getCharacterCardMap().get(remoteModel.getActiveCharacterCard());

    }
}
