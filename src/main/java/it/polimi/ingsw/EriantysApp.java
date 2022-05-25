package it.polimi.ingsw;

import it.polimi.ingsw.Client.CLI;
import it.polimi.ingsw.Client.Client;
import it.polimi.ingsw.Server.Server;

import java.io.IOException;
import java.util.Scanner;

public class EriantysApp {
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        switch (input) {
            case "server" -> {
                Server server;
                try {
                    server = new Server();
                    server.run();
                } catch (IOException e) {
                    System.err.println("Impossible to initialize the server: " + e.getMessage() + "!");
                }
            }
            case "client -cli" -> {
                Client client = new CLI("127.0.0.1", 1234);
                client.run();
            }
            case "client -gui" -> System.out.println("TO DO");
        }
    }
}
