package rnds.server;

import rnds.webservice.RandomNumbersServiceImpl;

import javax.xml.ws.Endpoint;

/**
 * Created by IntelliJ IDEA.
 * User: lukash
 * Date: 8/18/11
 * Time: 10:33 PM
 */
public class Publisher {
    public static void main(String[] args) {
        String serviceAddress = "http://10.0.1.2:9080/WS/RandomNumbersService";
        Endpoint endpoint = Endpoint.publish(serviceAddress, new RandomNumbersServiceImpl());

        System.out.println("service published at: " + serviceAddress);
    }
}
