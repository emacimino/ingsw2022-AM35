package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.SchoolsLands.Archipelago;
import it.polimi.ingsw.Model.SchoolsMembers.Student;
import it.polimi.ingsw.Model.Wizard.AssistantsCards;
import it.polimi.ingsw.NetworkUtilities.Message.Message;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.Server.ClientConnection;

import java.util.List;

public abstract class ViewInterface extends Observable implements Observer{

    public abstract void playCharacterCard(CharacterCard card);

    public abstract void moveMotherNature(Integer archipelago);

    public abstract void update(Message message) ;
    public abstract void sendMessage(Message message);
    public abstract void showGenericMessage(Message message);
    public abstract ClientConnection getClientConnection();
}