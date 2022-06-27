package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.ExpertMatch.CharacterCards.CharacterCard;
import it.polimi.ingsw.NetworkUtilities.Message;
import it.polimi.ingsw.Observer.Observable;
import it.polimi.ingsw.Observer.Observer;
import it.polimi.ingsw.Server.ClientConnection;

public abstract class ViewInterface extends Observable implements Observer{

    public abstract void update(Message message) ;
    public abstract void sendMessage(Message message);
}