/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rnds.service;

import java.io.IOException;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import rnds.generators.FileRandomBitGenerator;
import rnds.generators.RandomBitGenerator;

/**
 *
 * @author lukash
 */
@WebService(serviceName = "RandomNumbersService")
public class RandomNumbersService {
    /**
     * Web service operation
     */
    @WebMethod(operationName = "generateRandomSequence")
    public String generateRandomSequence(@WebParam(name = "sequenceLength")
            final int sequenceLength, @WebParam(name = "packagesAmount") int packagesAmount) throws IOException {
            
        RandomBitGenerator randomBitGenerator = 
                new FileRandomBitGenerator("/opt/RandomNumbersSource");
        
        StringBuilder output = new StringBuilder(sequenceLength * packagesAmount);
        
        for (int i = 0; i < packagesAmount; i++) {
           output.append(randomBitGenerator.generateSequence(sequenceLength));
        }
        
        return output.toString();
    }
}
