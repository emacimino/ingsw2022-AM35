package it.polimi.ingsw.NetworkUtilities;

import java.io.Serial;

public class ErrorMessage extends Message {
    @Serial
    private static final long serialVersionUID = 8580493153335998493L;

    private final String error;
    public ErrorMessage(String message) {
        setType(TypeMessage.ERROR);
        this.error = message;
    }

    public String getError() {
        return error;
    }
}
