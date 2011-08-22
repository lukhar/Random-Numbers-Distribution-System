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
public class NativeRandomBitGeneratorTest extends TestCase {

    @Test
    public void testShouldGenerateRandomSequence() throws IOException {
        // given
        NativeRandomBitGenerator nativeRandomBitGenerator = new NativeRandomBitGenerator();
        int sequenceLength = 12;

        // when
        String sequenceOne = nativeRandomBitGenerator.generateSequence(sequenceLength);
        String sequenceTwo = nativeRandomBitGenerator.generateSequence(sequenceLength);

        // then
        assertThat(sequenceOne).isNotEqualTo(sequenceTwo);
    }

    @Test
    public void testShouldGenerateSequenceWithGivenLength() throws IOException {
        // given
        int sequenceLength = 1023;
        NativeRandomBitGenerator nativeRandomBitGenerator = new NativeRandomBitGenerator();

        // when
        String sequence = nativeRandomBitGenerator.generateSequence(sequenceLength);

        // then
        assertThat(sequence).isNotNull().hasSize(sequenceLength);
    }
}
