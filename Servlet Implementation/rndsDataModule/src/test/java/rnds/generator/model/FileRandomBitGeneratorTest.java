package rnds.generator.model;

import org.junit.Test;

import java.io.FileNotFoundException;

import static junit.framework.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: lukash
 * Date: 8/6/11
 * Time: 2:51 PM
 */
public class FileRandomBitGeneratorTest extends RandomBitGeneratorTest {

    @Test
    public void testShouldReturnNumbersFromGivenSource() throws Exception {
        // given
        int sequenceLength = 400;

        // when
        String sequenceOne = new FileRandomBitGenerator(SOURCE).generateSequence(sequenceLength);
        String sequenceTwo = new FileRandomBitGenerator(SOURCE).generateSequence(sequenceLength);

        // then
        assertNotNull(sequenceOne);
        assertEquals("Retrieved sequences should be equel", sequenceOne, sequenceTwo);
    }

    @Test
    public void testShouldReturnSequenceOfGivenLength() throws Exception {
        // given
        int expectedLength = 400;

        // when
        String sequence = new FileRandomBitGenerator(SOURCE).generateSequence(expectedLength);

        // then
        assertNotNull(sequence);
        assertEquals("sequence length differs from expected", expectedLength, sequence.length());
    }

    @Test
    public void testExpectFileNotFoundExceptionWhenSourceIsMissing() {
        // given
        String wrongSourcePath = "notExistingFile";

        // when
        try {
            new FileRandomBitGenerator(wrongSourcePath).generateSequence(10);

            // then
            fail("exception should have been thrown");
        } catch (Exception ex) {
            assertEquals("expected FileNotFoundException", FileNotFoundException.class, ex.getClass());
        }
    }
}
