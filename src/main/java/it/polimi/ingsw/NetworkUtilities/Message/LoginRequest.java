package it.polimi.ingsw.NetworkUtilities.Message;

public class LoginRequest extends Message{
    private static final long serialVersionUID = 9196970831074769370L;

    public LoginRequest(String nickname, String username){
        super(nickname, username, GameStateMessage.LOGIN_REQUEST);
    }

    @Override
    public String toString(){
        return "Login Request : " + "Username=" + getContent() + "}" ;
    }
}
