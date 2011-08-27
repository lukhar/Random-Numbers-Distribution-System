package rnds.client;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: lukash
 * Date: 8/14/11
 * Time: 11:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServletClient implements Runnable {
    private String servletAddress;
    private int sequenceLength;
    private int packagesAmount;
    private int sequenceSize;

    public ServletClient(String[] args) {
        if (args.length < 3) {
            System.out.println("Wrong number of parameters ServletClient <servlet_address> <sequence_length> <packages_amount>");
            System.exit(-1);
        }

        this.servletAddress = args[0];
        this.sequenceLength = Integer.valueOf(args[1]);
        this.packagesAmount = Integer.valueOf(args[2]);
        this.sequenceSize = (sequenceLength % Byte.SIZE == 0) ?
                (sequenceLength / Byte.SIZE) : (sequenceLength / Byte.SIZE +1);
    }

    public static void main(String[] args) {
        new Thread(new ServletClient(args)).start();
    }

    public void run() {
        try {
            long startTime = System.nanoTime();
            URL url = new URL(servletAddress + "?" + "sequenceSize="
                    + sequenceSize + "&" + "packagesAmount=" + packagesAmount);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            HttpURLConnection.setFollowRedirects(true);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/octet-stream");

            connection.connect();
            long connectionEstablishTime = System.nanoTime() - startTime;

            startTime = System.nanoTime();

            InputStream stream = new BufferedInputStream(connection.getInputStream());

            byte[] buf = new byte[sequenceSize];
            while(stream.read(buf, 0, sequenceSize) != -1) {
//                print(buf);
            }

            long dataTransferTime = System.nanoTime() - startTime;

            startTime = System.nanoTime();
            stream.close();
            connection.disconnect();
            long connectionCloseTime = System.nanoTime() - startTime;



            System.out.printf("%d %4.6f %4.6f %4.6f\n",
                    sequenceSize * packagesAmount,
                    convertToSeconds(connectionEstablishTime),
                    convertToSeconds(dataTransferTime),
                    convertToSeconds(connectionCloseTime));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void print(byte[] buf) {
        for(byte b : buf) {
            System.out.print(b);
        }
    }

    private double convertToSeconds(long elapsedTime) {
        double numberOfNanosecondsInSecond = 1000000000.0;

        return elapsedTime / numberOfNanosecondsInSecond;
    }

}
