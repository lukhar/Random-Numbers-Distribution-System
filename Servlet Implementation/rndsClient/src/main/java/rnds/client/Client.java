package rnds.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Client implements Runnable {

    private String servletAddress;
    private int sequenceLength;
    private int packagesAmount;
    private int sequenceSize;

    public Client(String[] args) {
        if (args.length < 2) {
            System.out.println("Wrong number of parameters Client <servlet_address> <sequence_length> <packages_amount>");
            System.exit(-1);
        }

        this.servletAddress = args[0];
        this.sequenceLength = Integer.valueOf(args[1]);
        this.packagesAmount = Integer.valueOf(args[2]);
        this.sequenceSize = (sequenceLength % Character.SIZE == 0) ?
                (sequenceLength / Character.SIZE) : (sequenceLength / Character.SIZE + 1);
    }

    public static void main(String[] args) {
        new Thread(new Client(args)).start();
    }

    public void run() {
        try {
            URL url = new URL(servletAddress + "?" + "sequenceLength="
                    + sequenceSize + "&" + "packagesAmount=" + packagesAmount);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            HttpURLConnection.setFollowRedirects(true);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            long startTime = System.nanoTime();
            connection.connect();
            long connectionEstablishTime = System.nanoTime() - startTime;

            startTime = System.nanoTime();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            char[] buf = new char[sequenceSize];
            while(reader.read(buf, 0, sequenceSize) != -1) {
//                System.out.println(buf);
            }

            long dataTransferTime = System.nanoTime() - startTime;

            startTime = System.nanoTime();
            reader.close();
            connection.disconnect();
            long connectionCloseTime = System.nanoTime() - startTime;



            System.out.printf("%d %4.6f %4.6f %4.6f\n",
                    sequenceSize * packagesAmount * 2,
                    convertToSeconds(connectionEstablishTime),
                    convertToSeconds(dataTransferTime),
                    convertToSeconds(connectionCloseTime));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double convertToSeconds(long elapsedTime) {
        double numberOfNanosecondsInSecond = 1000000000.0;

        return elapsedTime / numberOfNanosecondsInSecond;
    }
}
