/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rnds.main;

import java.io.IOException;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import rnds.generators.NativeNumbersGenerator;

/**
 *
 * @author lukash
 */
@WebService(serviceName = "RandomNumbersService")
public class RandomNumbersService {
    
    /** This is a sample web service operation */
    @WebMethod(operationName = "getRandomNumbersSequence")
    public String getRandomNumbersSequence(@WebParam(name = "sequenceLength") int sequneceLength) throws IOException {
        return new NativeNumbersGenerator().generateSequence(sequneceLength);
    }
}
