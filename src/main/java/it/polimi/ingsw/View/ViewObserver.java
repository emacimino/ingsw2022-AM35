package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsLands.Cloud;
import it.polimi.ingsw.Model.SchoolsMembers.Color;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.NetworkUtilities.Message.CurrentGameMessage;
import it.polimi.ingsw.NetworkUtilities.Message.LoginResponse;

//a seconda degli update della view (cli o gui), chiunque è osservatore della view verrà aggiornato
public interface ViewObserver {

    void updateOnServerInfo(String ip, String port);

    void updateOnLogin(LoginResponse loginResponse);

    void updateOnSelectedAssistantCard(AssistantsCards assistantsCards);


    void updateOnMoveStudent(Student student, Archipelago archipelago);

    void updateOnMoveMotherNature(Archipelago archipelago);

    void updateOnSelectedCloud(Cloud cloud);

    void updateOnSelectedColor(Color color);

    void onDisconnection();
}
