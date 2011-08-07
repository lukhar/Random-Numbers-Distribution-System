/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rnds.generators;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author lukash
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
            resultBuilder.append(Integer.toBinaryString(randomNumbersSource.read()));
        } while (resultBuilder.length() < sequenceLength);

        return resultBuilder.substring(0, sequenceLength);
    }
}
