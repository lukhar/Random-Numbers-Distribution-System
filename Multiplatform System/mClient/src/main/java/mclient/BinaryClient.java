package mclient;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by IntelliJ IDEA.
 * User: lukash
 * Date: 8/15/11
 * Time: 2:00 AM
 */
public class BinaryClient implements Runnable {
    private Socket socket;
    private int sequenceLength;
    private String serverAddress;
    private int portNumber;
    private int packagesAmount;
    private int sequenceSize;


    public BinaryClient(String[] args) throws IOException {
        if (args.length < 4) {
            System.out.println("Wrong number of parameters BinaryClient <server_address> <port> <sequence_size> <packages_amount>");
            System.exit(-1);
        }
        this.socket = new Socket();
        this.serverAddress = args[0];
        this.portNumber = Integer.parseInt(args[1]);
        this.sequenceLength = Integer.valueOf(args[2]);
        this.packagesAmount = Integer.valueOf(args[3]);
        this.sequenceSize = (sequenceLength % Byte.SIZE == 0) ?
                (sequenceLength / Byte.SIZE) : (sequenceLength / Byte.SIZE + 1);
    }

    public static void main(String[] args) throws IOException {
        new Thread(new BinaryClient(args)).start();
    }

    public void run() {
        try {
            long startTime = System.nanoTime();
            socket.connect(new InetSocketAddress(serverAddress, portNumber));
            long connectionEstablishTime = System.nanoTime() - startTime;

            startTime = System.nanoTime();
            BufferedWriter socketWriter = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            BufferedInputStream inputStream = new BufferedInputStream(socket.getInputStream());

            socketWriter.write(String.valueOf(sequenceSize));
            socketWriter.newLine();
            socketWriter.flush();

            socketWriter.write(String.valueOf(packagesAmount));
            socketWriter.newLine();
            socketWriter.flush();

            byte[] buf = new byte[sequenceSize];
            while(inputStream.read(buf, 0, sequenceSize) != -1);

            long dataTransferTime = System.nanoTime() - startTime;

            startTime = System.nanoTime();
            inputStream.close();
            socketWriter.close();
            socket.close();
            long connectionCloseTime = System.nanoTime() - startTime;

            System.out.printf("%d %4.6f %4.6f %4.6f\n",
                    sequenceSize * packagesAmount * 2,
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
