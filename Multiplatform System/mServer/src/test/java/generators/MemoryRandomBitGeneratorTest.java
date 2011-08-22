package generators;

import org.junit.Test;

import java.io.FileNotFoundException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;

/**
 * Created by IntelliJ IDEA.
 * User: lukash
 * Date: 8/9/11
 * Time: 8:01 PM
 */
public class MemoryRandomBitGeneratorTest {
    private final static  String SOURCE = "resources/TestRandomNumbersSource";

    @Test
    public void testShouldReturnNumbersFromGivenSource() throws Exception {
        // given
        int sequenceLength = 4;
        int allocationSize = 4;

        // when
        String sequenceOne = new MemoryRandomBitGenerator(SOURCE, allocationSize).generateSequence(sequenceLength);
        String sequenceTwo = new MemoryRandomBitGenerator(SOURCE, allocationSize).generateSequence(sequenceLength);

        // then
        assertNotNull(sequenceOne);
        assertEquals("Retrieved sequences should be equel", sequenceOne, sequenceTwo);
    }

    @Test
    public void testShouldReturnSequenceOfGivenLength() throws Exception {
        // given
        int sequenceLength = 400;
        int expectedDataSize = (sequenceLength % 8 == 0) ?
                sequenceLength / Character.SIZE : sequenceLength / Character.SIZE + 1;
        int allocationSize = 4;

        // when
        String sequence = new MemoryRandomBitGenerator(SOURCE, allocationSize).generateSequence(sequenceLength);

        // then
        assertNotNull(sequence);
        assertEquals("sequence length differs from expected", expectedDataSize, sequence.length());
    }

    @Test
    public void testExpectFileNotFoundExceptionWhenSourceIsMissing() {
        // given
        String wrongSourcePath = "notExistingFile";

        // when
        try {
            new MemoryRandomBitGenerator(wrongSourcePath, 1).generateSequence(10);

            // then
            fail("exception should have been thrown");
        } catch (Exception ex) {
            assertEquals("expected FileNotFoundException", FileNotFoundException.class, ex.getClass());
        }
    }
}
