package rnds.client;

import sun.util.resources.LocaleNames_da;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Client implements Runnable {

    private String servletAddress;
    private String sequenceLength;

    public Client(String[] args) {
        if (args.length < 2) {
            System.out.println("Wrong number of parameters Client <servlet_address> <sequence_length>");
            return;
        }

        this.servletAddress = args[0];
        this.sequenceLength = args[1];
    }

    public static void main(String[] args) {
        new Thread(new Client(args)).start();
    }

    public void run() {
        try {
            URL url = new URL(servletAddress);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);

            long startTime = System.nanoTime();
            connection.connect();
            long connectionEstablishTime = System.nanoTime() - startTime;

            startTime = System.nanoTime();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(connection.getOutputStream()));
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            writer.write("sequenceLength=" + sequenceLength);
            writer.newLine();
            writer.flush();
            while (reader.readLine() != null) ;
            long dataTransferTime = System.nanoTime() - startTime;

            startTime = System.nanoTime();
            reader.close();
            writer.close();
            connection.disconnect();
            long connectionCloseTime = System.nanoTime() - startTime;

            long sequenceSize = Long.valueOf(sequenceLength) / 8;

            System.out.printf("%d %4.6f %4.6f %4.6f\n",
                    sequenceSize,
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
