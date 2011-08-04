package rnds.generator.model;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.TestCase;

import org.junit.Test;

public class RandomBitGeneratorManagerTest extends TestCase {

    @Test
    public void testShouldGenerateBitSequnenceFromStream() throws IOException {
        // given
        InputStream randomNumbersSource = new BufferedInputStream(
                new FileInputStream("/dev/urandom"));
        int randomSequenceLength = 400;

        // when
        String bitValuesSequence = new RandomBitGeneratorManagerImpl()
                .getSequenceFromStream(randomNumbersSource,
                        randomSequenceLength);

        // then
        assertNotNull(bitValuesSequence);
        assertEquals(randomSequenceLength, bitValuesSequence.length());
        assertEquals(true, bitValuesSequence.matches("^[01]{400}$"));
    }

    @Test
    public void testShouldGenerateBitSequenceFromDefaultGenerator() throws IOException {
        // given
        int randomSequenceLength = 400;

        // when
        String bitValuesSequence = new RandomBitGeneratorManagerImpl()
                .getSequenceFromDefaultGenerator(randomSequenceLength);

        // then
        assertNotNull(bitValuesSequence);
        assertEquals(randomSequenceLength, bitValuesSequence.length());
        assertEquals(true, bitValuesSequence.matches("^[01]{400}$"));
    }

}
