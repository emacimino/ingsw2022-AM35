package it.polimi.ingsw.Server.Message;

import com.google.gson.Gson;

public class logRequest implements Message{
    private String username;

    public logRequest(String username) {
        this.username = username;
        this.getMessage();
    }

    public Object loggingRequest() {
        Gson gson = new Gson();
        gson.toJson(username);
        return gson;
    }


    @Override
    public Object getMessage() {
        return this.loggingRequest();
    }
}
