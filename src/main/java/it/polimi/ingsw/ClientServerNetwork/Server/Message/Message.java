package it.polimi.ingsw.ClientServerNetwork.Server.Message;

import java.io.Serializable;

public interface Message extends Serializable {

    String getMessage();

    Object getGsonMessage();

}
