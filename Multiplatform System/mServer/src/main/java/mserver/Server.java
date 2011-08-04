package mserver;

import generators.FileNumbersGenerator;
import generators.NativeNumbersGenerator;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static com.sun.activation.registries.LogSupport.log;

/**
 * Created by IntelliJ IDEA.
 * User: lukash
 * Date: 5/8/11
 * Time: 6:01 PM
 */
public class Server extends Thread {

    ServerSocket serverSocket;
    private int portNumber;

    public Server(int portNumber) throws IOException {
        this.portNumber = portNumber;

        serverSocket = new ServerSocket(portNumber);
        System.out.println("Server started on port " + portNumber);
        this.start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                Connection connection = new Connection(clientSocket, new FileNumbersGenerator("resources/TestRandomNumbersSource"));
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
