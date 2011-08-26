package rnds.client;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


/**
 * Created by IntelliJ IDEA.
 * User: lukash
 * Date: 8/18/11
 * Time: 11:15 PM
 */
public class ServiceClient {

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        RandomNumbersServiceImplService serviceImpl = new RandomNumbersServiceImplService();
        long connectionEstablishTime = System.nanoTime() - startTime;
        System.out.println(convertToSeconds(connectionEstablishTime));
        RandomNumbersService service = serviceImpl.getRandomNumbersServiceImplPort();
        System.out.println(service.generateSequence(Integer.parseInt(args[0])));
    }

    private static double convertToSeconds(long elapsedTime) {
        double numberOfNanosecondsInSecond = 1000000000.0;

        return elapsedTime / numberOfNanosecondsInSecond;
    }

/*
    public static void main(String[] args) {
        ServiceClient client = new ServiceClient();

        try {
            int sequenceLength = Integer.parseInt(args[0]);
            int packagesAmount = Integer.parseInt(args[1]);
            int sequenceSize = (sequenceLength % Byte.SIZE == 0) ?
                (sequenceLength / Byte.SIZE) : (sequenceLength / Byte.SIZE + 1);

            client.generateSequence(sequenceSize * packagesAmount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/

    public String generateSequence(int sequenceSize) throws MalformedURLException,
            IOException {

        //Code to make a webservice HTTP request
        String responseString = "";
        String outputString = "";

        long startTime = System.nanoTime();
        String wsURL = "http://10.0.1.2:9080/WS/RandomNumbersService";
        URL url = new URL(wsURL);
        URLConnection connection = url.openConnection();
        long connectionEstablishTime = System.nanoTime() - startTime;

        startTime = System.nanoTime();
        HttpURLConnection httpConn = (HttpURLConnection) connection;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();

        String xmlInput =
                "   <soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                        "       <soap:Body>\n" +
                        "           <m:generateSequence xmlns:m=\"http://webservice.rnds/\">\n" +
                        "               <sequenceSize>" + sequenceSize + "</sequenceSize>\n" +
                        "           </m:generateSequence>\n" +
                        "       </soap:Body>\n" +
                        "   </soap:Envelope>";
//        System.out.println(xmlInput);

        byte[] buffer;
        buffer = xmlInput.getBytes();
        bout.write(buffer);
        byte[] b = bout.toByteArray();
        String SOAPAction =
                "\"http://webservice.rnds/RandomNumbersService/generateSequenceRequest\"";
        // Set the appropriate HTTP parameters.
        httpConn.setRequestProperty("Content-Length",
                String.valueOf(b.length));
        httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        httpConn.setRequestProperty("SOAPAction", SOAPAction);
        httpConn.setRequestMethod("POST");
        httpConn.setDoOutput(true);
        httpConn.setDoInput(true);
        OutputStream out = httpConn.getOutputStream();
        //Write the content of the request to the outputstream of the HTTP Connection.
        out.write(b);

        //Ready with sending the request.

        //Read the response.
        InputStreamReader isr =
                new InputStreamReader(httpConn.getInputStream());
        BufferedReader in = new BufferedReader(isr);

        //Write the SOAP message response to a String.
        while ((responseString = in.readLine()) != null) {
            outputString = outputString + responseString;
        }
        long dataTransferTime = System.nanoTime() - startTime;

        startTime = System.nanoTime();
        ((HttpURLConnection) connection).disconnect();
        long connectionCloseTime = System.nanoTime() - startTime;
        //Parse the String output to a org.w3c.dom.Document and be able to reach every node with the org.w3c.dom API.
        Document document = Util.parseXmlFile(outputString);
        NodeList nodeLst = document.getElementsByTagName("return");
        String sequence = nodeLst.item(0).getTextContent();
        System.out.println("Sequence : " + sequence);


        //Write the SOAP message formatted to the console.
        String formattedSOAPResponse = Util.formatXML(outputString);
        System.out.println(formattedSOAPResponse);

        System.out.printf("%d %4.6f %4.6f %4.6f\n",
                sequenceSize,
                convertToSeconds(connectionEstablishTime),
                convertToSeconds(dataTransferTime),
                convertToSeconds(connectionCloseTime));


        return null;
    }

    //format the XML in your String
    public String formatXML(String unformattedXml) {
        return Util.formatXML(unformattedXml);
    }

    private Document parseXmlFile(String in) {
        return Util.parseXmlFile(in);
    }
}
