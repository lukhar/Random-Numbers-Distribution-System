package rnds.generators;

import java.io.*;


public class NativeNumbersGenerator implements RandomNumbersGenerator {
    private static final String DEFAULT_GENERATOR_PATH = "/dev/urandom";
    private InputStream randomNumbersSource;

    public NativeNumbersGenerator() throws FileNotFoundException {
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
}
