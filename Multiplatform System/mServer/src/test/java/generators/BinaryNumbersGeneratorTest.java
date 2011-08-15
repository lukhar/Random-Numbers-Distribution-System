package generators;

import mserver.BinaryConnection;
import org.junit.Test;

import java.io.FileNotFoundException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;
import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by IntelliJ IDEA.
 * User: lukash
 * Date: 8/15/11
 * Time: 1:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class BinaryNumbersGeneratorTest {

    private final static  String SOURCE = "resources/TestRandomNumbersSource";

    @Test
    public void testShouldReturnNumbersFromGivenSource() throws Exception {
        // given
        int sequenceLength = 400;

        // when
        byte[] sequenceOne = new BinaryNumbersGenerator(SOURCE).generateSequence(sequenceLength);
        byte[] sequenceTwo = new BinaryNumbersGenerator(SOURCE).generateSequence(sequenceLength);

        // then
        assertNotNull(sequenceOne);
        assertThat(sequenceOne).isEqualTo(sequenceTwo);

    }

    @Test
    public void testShouldReturnSequenceOfGivenLength() throws Exception {
        // given
        int expectedLength = 400;

        // when
        byte[] sequence = new BinaryNumbersGenerator(SOURCE).generateSequence(expectedLength);

        // then
        assertNotNull(sequence);
        assertEquals("sequence length differs from expected", expectedLength, sequence.length);
    }

    @Test
    public void testExpectFileNotFoundExceptionWhenSourceIsMissing() {
        // given
        String wrongSourcePath = "notExistingFile";

        // when
        try {
            new BinaryNumbersGenerator(wrongSourcePath).generateSequence(10);

            // then
            fail("exception should have been thrown");
        } catch (Exception ex) {
            assertEquals("expected FileNotFoundException", FileNotFoundException.class, ex.getClass());
        }
    }
}
