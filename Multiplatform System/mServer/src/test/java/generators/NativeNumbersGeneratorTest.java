package generators;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created by IntelliJ IDEA.
 * User: lukash
 * Date: 5/8/11
 * Time: 6:09 PM
 */
public class NativeNumbersGeneratorTest extends TestCase {

    @Test
    public void testShouldGenerateRandomSequence() throws IOException {
        // given
        NativeNumbersGenerator nativeNumbersGenerator = new NativeNumbersGenerator();
        int sequenceLength = 12;

        // when
        String sequenceOne = nativeNumbersGenerator.generateSequence(sequenceLength);
        String sequenceTwo = nativeNumbersGenerator.generateSequence(sequenceLength);

        // then
        assertThat(sequenceOne).isNotEqualTo(sequenceTwo);
    }

    @Test
    public void testShouldGenerateSequenceWithGivenLength() throws IOException {
        // given
        int sequenceLength = 1023;
        NativeNumbersGenerator nativeNumbersGenerator = new NativeNumbersGenerator();

        // when
        String sequence = nativeNumbersGenerator.generateSequence(sequenceLength);

        // then
        assertThat(sequence).isNotNull().hasSize(sequenceLength);
    }
}
