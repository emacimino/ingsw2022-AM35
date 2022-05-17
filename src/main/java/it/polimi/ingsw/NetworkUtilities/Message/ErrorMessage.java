package it.polimi.ingsw.NetworkUtilities.Message;

public class ErrorMessage extends Message {
    private static final long serialVersionUID = 8580493153335998493L;

    private final String error;
    public ErrorMessage(String message) {
        setType(GameStateMessage.ERROR);
        this.error = message;
    }

    public String getError() {
        return error;
    }
}
