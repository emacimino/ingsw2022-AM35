package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.NetworkUtilities.*;

//a seconda degli update della view (cli o gui), chiunque è osservatore della view verrà aggiornato
public interface ViewObserver {

    void updateOnServerInfo(String ip, String port);

    void updateOnLogin(LoginResponse loginResponse);

    void updateOnSelectedAssistantCard(AssistantCardMessage assistantCardMessage);


    void updateOnMoveStudent(MoveStudentMessage message);

    void updateOnMoveMotherNature(MoveMotherNatureMessage message);

    void updateOnSelectedCloud(CloudMessage cloud);

    void updateOnSelectedColor(Color color);

    void onDisconnection();
}
