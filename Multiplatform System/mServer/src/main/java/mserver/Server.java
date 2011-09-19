package mserver;

import generators.FileRandomBitGenerator;
import generators.RandomBitGenerator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by IntelliJ IDEA.
 * User: lukash
 * Date: 5/8/11
 * Time: 6:01 PM
 */
public class Server extends Thread {

    ServerSocket serverSocket;
    private int portNumber;
    private RandomBitGenerator generator;

    public Server(int portNumber) throws IOException {
        this.portNumber = portNumber;
        this.generator= new FileRandomBitGenerator("/opt/RandomNumbersSource");

        serverSocket = new ServerSocket(portNumber);
        System.out.println("Server started on port " + portNumber);
        this.start();
    }

    public Server(ServerSocket serverSocket, int portNumber, RandomBitGenerator generator) {
        this.serverSocket = serverSocket;
        this.portNumber = portNumber;
        this.generator = generator;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                Connection connection = new Connection(clientSocket, generator);
                new Thread(connection).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            new Server(Integer.parseInt(args[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
