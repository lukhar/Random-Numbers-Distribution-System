package rnds.generator.model;

import org.junit.Test;

import java.io.IOException;

import static org.fest.assertions.Assertions.assertThat;


public class NativeRandomBitGeneratorTest extends RandomBitGeneratorTest {

     @Test
    public void testShouldGenerateRandomSequence() throws IOException {
        // given
        NativeRandomBitGenerator nativeNumbersGenerator = new NativeRandomBitGenerator();
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
        NativeRandomBitGenerator nativeNumbersGenerator = new NativeRandomBitGenerator();

        // when
        String sequence = nativeNumbersGenerator.generateSequence(sequenceLength);

        // then
        assertThat(sequence).isNotNull().hasSize(sequenceLength);
    }
}
