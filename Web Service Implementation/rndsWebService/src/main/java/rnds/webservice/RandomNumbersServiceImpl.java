package rnds.webservice;

import rnds.generators.FileRandomBitGenerator;
import rnds.generators.RandomBitGenerator;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: lukash
 * Date: 8/18/11
 * Time: 10:21 PM
 */
@WebService(endpointInterface = "rnds.webservice.RandomNumbersService")
public class RandomNumbersServiceImpl implements RandomNumbersService {
    @Override
    public String generateSequence(int sequenceSize) throws IOException {
        String output;
        RandomBitGenerator generator = new FileRandomBitGenerator("/opt/RandomNumbersSource");
        output = generator.generateSequence(sequenceSize);

        return output;
    }
}
