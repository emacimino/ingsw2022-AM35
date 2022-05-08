package it.polimi.ingsw.NetworkUtilities.Message;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.FactoryMatch.Player;

public class LoginReply extends Message{

    private static final long serialVersionUID = 2194735574340893334L;
    private final boolean loginRequestSuccess;
    private final boolean connectionSuccess;
    private final String username;

    public LoginReply(boolean loginRequestSuccess, boolean connectionSuccess, String username){
        super("Username: "+username, GameStateMessage.LOGIN_REPLY);
        this.username = username;
        this.connectionSuccess = connectionSuccess;
        this.loginRequestSuccess = loginRequestSuccess;
    }

    public boolean loginRequestSuccess() {
        return loginRequestSuccess;
    }

    public boolean connectionSuccess() {
        return connectionSuccess;
    }

    @Override
    public String toString(){
        return "Login Reply : " + "Username=" + username + "Connection : " + connectionSuccess() + "}" ;
    }
}
