package it.polimi.ingsw;

import it.polimi.ingsw.Client.Cli.CLI;
import it.polimi.ingsw.Client.Client;

import java.io.IOException;

public class ClientApp
{
    public static void main(String[] args) throws IOException {
        CLI client = new CLI("127.0.0.1", 1234);
        client.run();
    }
}

