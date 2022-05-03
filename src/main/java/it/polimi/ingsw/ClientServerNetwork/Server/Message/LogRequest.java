package it.polimi.ingsw.ClientServerNetwork.Server.Message;

import com.google.gson.Gson;

public class LogRequest implements Message{
    private String username;

    public Object logRequest(String username) {
        this.username = username;
        return this.getMessage();
    }

    public Object loggingRequest() {
        Gson gson = new Gson();
        gson.toJson(username);
        return gson;
    }


    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public Object getGsonMessage() {
        return this.loggingRequest();
    }
}
