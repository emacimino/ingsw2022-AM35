package it.polimi.ingsw.Server.Message;

import com.google.gson.Gson;

public class LoginAnswer implements Message {


    public Object loginAnswer(){
        String message = "Successful login";
        Gson gson = new Gson();
        gson.toJson(message);
        return gson;
    }
    @Override
    public Object getMessage() {

        return this.loginAnswer();

    }
}
