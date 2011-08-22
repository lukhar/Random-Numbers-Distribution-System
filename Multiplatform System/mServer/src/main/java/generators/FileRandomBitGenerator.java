package generators;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: lukash
 * Date: 7/3/11
 * Time: 10:16 PM
 */
public class FileRandomBitGenerator implements RandomBitGenerator {
    private final BufferedInputStream randomNumbersSource;

    public FileRandomBitGenerator(final String source) throws FileNotFoundException {
        this.randomNumbersSource = new BufferedInputStream(
                new FileInputStream(source));
    }

    @Override
    public String generateSequence(int sequenceLength) throws IOException {
        StringBuilder resultBuilder = new StringBuilder(
                sequenceLength + Integer.SIZE);

        do {
            resultBuilder.append(randomNumbersSource.read());
        } while (resultBuilder.length() < sequenceLength);

        return resultBuilder.substring(0, sequenceLength);
    }

    @Override
    protected void finalize() throws Throwable {
        randomNumbersSource.close();
        super.finalize();
    }
}
