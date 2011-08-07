/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rnds.generators;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author lukash
 */
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
}
