package rnds.client;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import rnds.webservice.RandomNumbersService;
import rnds.webservice.RandomNumbersServiceImplService;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
    /* public static void main(String[] args) {
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
    }*/

    public static void main(String[] args) {
        ServiceClient client = new ServiceClient();

        try {
            client.generateSequence(50000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String generateSequence(int sequenceSize) throws MalformedURLException,
            IOException {

        //Code to make a webservice HTTP request
        String responseString = "";
        String outputString = "";
        String wsURL = "http://10.0.1.2:9080/WS/RandomNumbersService";
        URL url = new URL(wsURL);
        URLConnection connection = url.openConnection();
        HttpURLConnection httpConn = (HttpURLConnection) connection;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        String xmlInput =
                "  <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://litwinconsulting.com/webservices/\">\n" +
                        "   <soapenv:Header/>\n" +
                        "   <soapenv:Body>\n" +
                        "      <web:generateSequence>\n" +
                        "         <web:sequenceSize>" + sequenceSize + "</web:sequenceSize>\n" +
                        "      </web:generateSequence>\n" +
                        "   </soapenv:Body>\n" +
                        "  </soapenv:Envelope>";

        byte[] buffer;
        buffer = xmlInput.getBytes();
        bout.write(buffer);
        byte[] b = bout.toByteArray();
        String SOAPAction =
                "http://litwinconsulting.com/webservices/GetWeather";
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
        out.close();
        //Ready with sending the request.

        //Read the response.
        InputStreamReader isr =
                new InputStreamReader(httpConn.getInputStream());
        BufferedReader in = new BufferedReader(isr);

        //Write the SOAP message response to a String.
        while ((responseString = in.readLine()) != null) {
            outputString = outputString + responseString;
        }
        //Parse the String output to a org.w3c.dom.Document and be able to reach every node with the org.w3c.dom API.
        Document document = parseXmlFile(outputString);
        NodeList nodeLst = document.getElementsByTagName("return");
        String weatherResult = nodeLst.item(0).getTextContent();
        System.out.println("Sequence : " + weatherResult);

        //Write the SOAP message formatted to the console.
        String formattedSOAPResponse = formatXML(outputString);
        System.out.println(formattedSOAPResponse);
        return weatherResult;
    }

    //format the XML in your String
    public String formatXML(String unformattedXml) {
        try {
            Document document = parseXmlFile(unformattedXml);
            OutputFormat format = new OutputFormat(document);
            format.setIndenting(true);
            format.setIndent(3);
            format.setOmitXMLDeclaration(true);
            Writer out = new StringWriter();
            XMLSerializer serializer = new XMLSerializer(out, format);
            serializer.serialize(document);
            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Document parseXmlFile(String in) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(in));
            return db.parse(is);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
