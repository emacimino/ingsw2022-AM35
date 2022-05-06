package it.polimi.ingsw.ClientServerNetwork.Server.Message;

import com.google.gson.Gson;

public class LoginAnswer implements Message {


    public Object loginAnswer(){
        String message = "Successful login";
        Gson gson = new Gson();
        gson.toJson(message);
        return gson;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public Object getGsonMessage() {

        return this.loginAnswer();

    }
}
