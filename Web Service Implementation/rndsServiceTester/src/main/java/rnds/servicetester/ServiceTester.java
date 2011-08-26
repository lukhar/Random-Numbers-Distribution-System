package rnds.servicetester;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: lukash
 * Date: 8/26/11
 * Time: 4:30 PM
 */
public class ServiceTester implements Runnable {

    private int packagesAmount;
    private int sequenceLength;
    private String serviceAddress;
    private int sequenceSize;
    private HttpURLConnection connection;
    private byte[] encodedInput;
    private URL serviceUrl;

    public ServiceTester(String[] args) {
        this.serviceAddress = args[0];
        this.sequenceLength = Integer.parseInt(args[1]);
        this.packagesAmount = Integer.parseInt(args[2]);
        this.sequenceSize = (sequenceLength % Byte.SIZE == 0) ? (sequenceLength / Byte.SIZE) : (sequenceLength / Byte.SIZE + 1);
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Wrong number of parameters - proper usage: ServiceTester <service_address> <sequence_length> <packages_amount>");
            System.exit(-1);
        }

        new Thread(new ServiceTester(args)).start();
    }

    @Override
    public void run() {
        long startTime = System.nanoTime();
        try {
            initializeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long connectionEstablishTime = System.nanoTime() - startTime;

        startTime = System.nanoTime();
        for (int i = 0; i < packagesAmount; ++i) {
            try {
                transferData(i);
            } catch (IOException e) {
                e.printStackTrace();
                connection.disconnect();
            }
        }
        long dataTransferTime = System.nanoTime() - startTime;

        startTime = System.nanoTime();
        closeConnection();
        long connectionCloseTime = System.nanoTime() - startTime;

        System.out.printf("%d %4.6f %4.6f %4.6f\n",
                sequenceSize * packagesAmount,
                convertToSeconds(connectionEstablishTime),
                convertToSeconds(dataTransferTime),
                convertToSeconds(connectionCloseTime));
    }

    private void closeConnection() {
        connection.disconnect();
    }

    private void initializeConnection() throws IOException {
        serviceUrl = new URL(serviceAddress);
        this.connection = (HttpURLConnection) serviceUrl.openConnection();

        String xmlInput =
                "   <soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                        "       <soap:Body>\n" +
                        "           <m:generateSequence xmlns:m=\"http://webservice.rnds/\">\n" +
                        "               <sequenceSize>" + sequenceSize + "</sequenceSize>\n" +
                        "           </m:generateSequence>\n" +
                        "       </soap:Body>\n" +
                        "   </soap:Envelope>";

        encodedInput = encode(xmlInput);
        setupConnectionParameters();
        connection.connect();
    }

    private void transferData(int iteration) throws IOException {
        if (iteration > 0) {
            connection = (HttpURLConnection) serviceUrl.openConnection();
            setupConnectionParameters();
        }

        OutputStream outputStream = connection.getOutputStream();

        outputStream.write(encodedInput);

        InputStreamReader isr =
                new InputStreamReader(connection.getInputStream());
        BufferedReader in = new BufferedReader(isr);

        //Write the SOAP message response to a String.
        String responseString = "";
        String outputString = "";
        while ((responseString = in.readLine()) != null) {
//            outputString = outputString + responseString;
        }

        outputStream.close();
        isr.close();
    }

    private void setupConnectionParameters() throws ProtocolException {
        String SOAPAction =
                "\"http://webservice.rnds/RandomNumbersService/generateSequenceRequest\"";

        connection.setRequestProperty("Content-Length",
                String.valueOf(encodedInput.length));
        connection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        connection.setRequestProperty("SOAPAction", SOAPAction);
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setDoInput(true);
    }

    private byte[] encode(String xmlInput) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] buffer;
        buffer = xmlInput.getBytes();
        bout.write(buffer);

        return bout.toByteArray();
    }

    private static double convertToSeconds(long elapsedTime) {
        double numberOfNanosecondsInSecond = 1000000000.0;

        return elapsedTime / numberOfNanosecondsInSecond;
    }
}
