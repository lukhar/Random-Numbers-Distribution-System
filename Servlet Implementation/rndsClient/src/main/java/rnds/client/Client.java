package rnds.client;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpProtocolParamBean;
import org.apache.http.protocol.HTTP;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Client implements Runnable {

    private String servletAddress;
    private String sequenceLength;
    private String packagesAmount;

    public Client(String[] args) {
        if (args.length < 2) {
            System.out.println("Wrong number of parameters Client <servlet_address> <sequence_length> <packages_amount>");
            return;
        }

        this.servletAddress = args[0];
        this.sequenceLength = args[1];
        this.packagesAmount = args[2];
    }

    public static void main(String[] args) {
        new Thread(new Client(args)).start();
    }

    public void run() {
        try {
            String query = "sequenceLength=" + URLEncoder.encode(sequenceLength, "UTF-8");

            URL url = new URL(servletAddress+ "?" + "sequenceLength="
                    + sequenceLength + "&" + "packagesAmount=" + packagesAmount);
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


//            while (reader.readLine() != null) {
////               System.out.println(response);
//            }

            long dataTransferTime = System.nanoTime() - startTime;

            startTime = System.nanoTime();
            reader.close();
            connection.disconnect();
            long connectionCloseTime = System.nanoTime() - startTime;

            long sequenceSize = Long.valueOf(sequenceLength) * Long.valueOf(packagesAmount) / 8;

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
