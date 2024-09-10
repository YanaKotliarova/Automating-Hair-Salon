package Server;

import dao.ConnectionFactory;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] arg) {
        ConnectionFactory.Connect();

        try (ServerSocket serverSocket = new ServerSocket(2525)) {
            System.out.println("Сервер начал работу");
            while (true) {
                Socket finalClientAccepted = serverSocket.accept();
                ClientThread clientThread = new ClientThread(finalClientAccepted);
                new Thread(clientThread).run();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}