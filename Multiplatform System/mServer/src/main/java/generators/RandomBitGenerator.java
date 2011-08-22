package generators;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: lukash
 * Date: 5/8/11
 * Time: 6:03 PM
 */
public interface RandomBitGenerator {

    public String generateSequence(int sequenceLength) throws IOException;
}
