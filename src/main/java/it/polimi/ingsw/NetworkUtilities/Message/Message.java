package it.polimi.ingsw.NetworkUtilities.Message;

import java.io.Serial;
import java.io.Serializable;

public abstract class Message implements Serializable {
    @Serial
    private static final long serialVersionUID = 6345566953463396637L;
    private TypeMessage type;

    public void setType(TypeMessage type) {
        this.type = type;
    }

    public TypeMessage getType() {
        return type;
    }


}
