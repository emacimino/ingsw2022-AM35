package it.polimi.ingsw.NetworkUtilities;

import java.io.Serial;

public class ServerInfoMessage extends Message{
    @Serial
    private static final long serialVersionUID = 1366025390070694073L;
    private final String ip;
    private final String port;

    public ServerInfoMessage(String ip, String port) {
        this.ip = ip;
        this.port = port;
        setType(TypeMessage.SERVER_INFO);
    }

    public String getIp() {
        return ip;
    }

    public String getPort() {
        return port;
    }
}
