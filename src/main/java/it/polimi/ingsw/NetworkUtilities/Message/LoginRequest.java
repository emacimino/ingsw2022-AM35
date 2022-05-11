package it.polimi.ingsw.NetworkUtilities.Message;



public class LoginRequest extends Message{
    private static final long serialVersionUID = 9196970831074769370L;

    public LoginRequest(String username){
        super(username, GameStateMessage.LOGIN_REQUEST);
    }

    @Override
    public String toString(){
        return "Login Request : " + "Username=" + getMessage() + "}" ;
    }
}
