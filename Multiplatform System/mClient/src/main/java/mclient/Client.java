package mclient;

import java.io.*;
import java.net.Socket;
import java.sql.Time;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: lukash
 * Date: 5/8/11
 * Time: 10:13 PM
 */
public class Client implements Runnable {

    private Socket socket;
    private String sequenceLength;

    public Client(String[] args) throws IOException {
        if (args.length < 3) {
            System.out.println("Wrong number of parameters Client <server_address> <port> <sequence_size>");
            return;
        }
        this.socket = new Socket(args[0], Integer.parseInt(args[1]));
        this.sequenceLength = args[2];
    }

    public static void main(String[] args) throws IOException {
        new Thread(new Client(args)).start();
    }

    public void run() {
        try {
            BufferedWriter socketWriter = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader socketReader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            long startTime = System.nanoTime();

            socketWriter.write(sequenceLength);
            socketWriter.newLine();
            socketWriter.flush();
            socketReader.readLine();

            long estimatedTime = System.nanoTime() - startTime;

            System.out.println("Elapsed time: " +
                    TimeUnit.MILLISECONDS.convert(estimatedTime, TimeUnit.MICROSECONDS));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
