package rnds.generator.model;

import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by IntelliJ IDEA.
 * User: lukash
 * Date: 8/13/11
 * Time: 11:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class BinaryRandomBitGeneratorTest extends RandomBitGeneratorTest {

    @Test
    public void testShouldRetrieveBinaryDataFromGivenSource() throws IOException {
        // given
        int expectedDataLength = 400;
        BinaryRandomBitGenerator generator = new BinaryRandomBitGenerator(SOURCE);

        // when
        byte[] output = generator.generateSequence(expectedDataLength);

        // then
        assertNotNull(output);
        assertEquals("wrong data size", expectedDataLength, output.length);
    }
}
