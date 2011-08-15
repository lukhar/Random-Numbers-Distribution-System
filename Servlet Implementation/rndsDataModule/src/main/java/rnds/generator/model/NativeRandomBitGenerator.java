package rnds.generator.model;

import java.io.*;

public class NativeRandomBitGenerator implements RandomBitGenerator {

	 private static final String DEFAULT_GENERATOR_PATH = "/dev/urandom";
    private InputStream randomNumbersSource;

    public NativeRandomBitGenerator() throws FileNotFoundException {
        this.randomNumbersSource = new BufferedInputStream(
                new FileInputStream(DEFAULT_GENERATOR_PATH));
    }

    @Override
    public String generateSequence(int sequenceLength) throws IOException {
        StringBuilder resultBuilder = new StringBuilder(
                sequenceLength + Integer.SIZE);

        do {
            resultBuilder.append(Integer.toBinaryString(randomNumbersSource.read()));
        } while (resultBuilder.length() < sequenceLength);

        return resultBuilder.substring(0, sequenceLength);
    }

    @Override
    protected void finalize() throws Throwable {
        randomNumbersSource.close();
        super.finalize();
    }
}
