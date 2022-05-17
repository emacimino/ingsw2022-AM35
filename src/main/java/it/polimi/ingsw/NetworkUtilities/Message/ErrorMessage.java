package it.polimi.ingsw.NetworkUtilities.Message;

public class ErrorMessage extends Message {
    private final String error;
    public ErrorMessage(String message) {
        setType(GameStateMessage.ERROR);
        this.error = message;
    }

    public String getError() {
        return error;
    }
}
