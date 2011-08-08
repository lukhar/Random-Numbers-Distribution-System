package mclient;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
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
    private String serverAddress;
    private int portNumber;
    private int packagesAmount;


    public Client(String[] args) throws IOException {
        if (args.length < 4) {
            System.out.println("Wrong number of parameters Client <server_address> <port> <sequence_size> <packages_amount");
            return;
        }
        this.socket = new Socket();
        this.serverAddress = args[0];
        this.portNumber = Integer.parseInt(args[1]);
        this.sequenceLength = args[2];
        this.packagesAmount = Integer.parseInt(args[3]);
    }

    public static void main(String[] args) throws IOException {
        new Thread(new Client(args)).start();
    }

    public void run() {
        try {
            long startTime = System.nanoTime();
            socket.connect(new InetSocketAddress(serverAddress, portNumber));
            long connectionEstablishTime = System.nanoTime() - startTime;

            startTime = System.nanoTime();
            BufferedWriter socketWriter = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader socketReader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            socketWriter.write(sequenceLength);
            socketWriter.newLine();
            socketWriter.flush();
            while (socketReader.readLine() != null);
            long dataTransferTime = System.nanoTime() - startTime;

            startTime = System.nanoTime();
            socketReader.close();
            socketWriter.close();
            socket.close();
            long connectionCloseTime = System.nanoTime() - startTime;

            long sequenceSize = Long.valueOf(sequenceLength) / 8;

            System.out.printf("%d %4.6f %4.6f %4.6f\n",
                    sequenceSize,
                    convertToSeconds(connectionEstablishTime),
                    convertToSeconds(dataTransferTime),
                    convertToSeconds(connectionCloseTime));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double convertToSeconds(long elapsedTime) {
        double numberOfNanosecondsInSecond = 1000000000.0;

        return elapsedTime / numberOfNanosecondsInSecond;
    }
}
