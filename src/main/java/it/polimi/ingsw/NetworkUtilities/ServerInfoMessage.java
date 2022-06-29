package it.polimi.ingsw.NetworkUtilities;

import java.io.Serial;

/**
 * Class used to send information messages
 */
public class ServerInfoMessage extends Message{
    @Serial
    private static final long serialVersionUID = 1366025390070694073L;
    private final String ip;
    private final String port;

    /**
     * Constructor of the class
     * @param ip a string containing the IP of the server
     * @param port a string containng the port of the server
     */
    public ServerInfoMessage(String ip, String port) {
        this.ip = ip;
        this.port = port;
        setType(TypeMessage.SERVER_INFO);
    }

    /**
     * Method that returns the IP contained in the message
     * @return a string
     */
    public String getIp() {
        return ip;
    }

    /**
     * Method that returns the port contained in the message
     * @return a string
     */
    public String getPort() {
        return port;
    }
}
