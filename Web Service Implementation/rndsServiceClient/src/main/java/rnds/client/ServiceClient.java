package rnds.client;

import rnds.webservice.RandomNumbersService;
import rnds.webservice.RandomNumbersServiceImplService;


/**
 * Created by IntelliJ IDEA.
 * User: lukash
 * Date: 8/18/11
 * Time: 11:15 PM
 */
public class ServiceClient {
    public static void main(String[] args) {
        RandomNumbersServiceImplService serviceImpl = new RandomNumbersServiceImplService();
        RandomNumbersService service = serviceImpl.getRandomNumbersServiceImplPort();
        System.out.println(service.generateSequence(Integer.parseInt(args[0])));
    }
}
