/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rnds.main;

/**
 *
 * @author lukash
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException_Exception {
       int randomSequenceLenght = Integer.parseInt(args[0]);
       System.out.println(getRandomNumbersSequence(randomSequenceLenght));
    }

    private static String getRandomNumbersSequence(int sequenceLength) throws IOException_Exception {
        rnds.main.RandomNumbersService_Service service = new rnds.main.RandomNumbersService_Service();
        rnds.main.RandomNumbersService port = service.getRandomNumbersServicePort();
        return port.getRandomNumbersSequence(sequenceLength);
    }
    
}
