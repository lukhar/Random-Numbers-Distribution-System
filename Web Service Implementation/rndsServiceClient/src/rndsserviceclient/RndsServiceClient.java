/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rndsserviceclient;

import rnds.service.IOException_Exception;

/**
 *
 * @author lukash
 */
public class RndsServiceClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException_Exception {
        int sequenceLength = Integer.valueOf(args[0]);
        int packagesAmount = Integer.valueOf(args[1]);
        
        for (int i =0; i < packagesAmount; ++i) {
            System.out.println(generateRandomSequence(sequenceLength, 1));
        }
    }

    private static String generateRandomSequence(int sequenceLength, int packagesAmount) throws IOException_Exception {
        rnds.service.RandomNumbersService_Service service = new rnds.service.RandomNumbersService_Service();
        rnds.service.RandomNumbersService port = service.getRandomNumbersServicePort();
        return port.generateRandomSequence(sequenceLength, packagesAmount);
    }
}
