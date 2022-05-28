package it.polimi.ingsw.NetworkUtilities.Message;

public class ServerInfoMessage extends Message{
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
