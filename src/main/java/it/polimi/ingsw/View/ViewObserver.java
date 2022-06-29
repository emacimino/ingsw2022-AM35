package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.NetworkUtilities.*;

/**
 * This class update every client connected through the update of the view that is observed
 */
//a seconda degli update della view (cli o gui), chiunque è osservatore della view verrà aggiornato
public interface ViewObserver {

    /**
     * Update the info of the server chosen
     * @param ip the chosen ip
     * @param port the chosen port
     */
    void updateOnServerInfo(String ip, String port);

    /**
     * Update the info after the login
     * @param loginResponse communicate if the login was successful
     */
    void updateOnLogin(LoginResponse loginResponse);

    /**
     * Update the info on the used Assistant Card
     * @param assistantCardMessage assistant card used
     */
    void updateOnSelectedAssistantCard(AssistantCardMessage assistantCardMessage);

    /**
     * Update the info on the moved student
     * @param message student moved
     */
    void updateOnMoveStudent(MoveStudentMessage message);

    /**
     * Update the info on mother nature
     * @param message Mother Nature position
     */
    void updateOnMoveMotherNature(MoveMotherNatureMessage message);

    /**
     * Update the info on the used cloud
     * @param cloud cloud chosen
     */
    void updateOnSelectedCloud(CloudMessage cloud);

    /**
     * Update everyone when a client disconnect himself
     */
    void onDisconnection();
}
