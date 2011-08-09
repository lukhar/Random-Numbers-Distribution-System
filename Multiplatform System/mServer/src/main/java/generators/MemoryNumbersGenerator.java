package generators;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: lukash
 * Date: 8/9/11
 * Time: 7:35 PM
 */
public class MemoryNumbersGenerator implements RandomNumbersGenerator {


    private char[] numbersSource;
    private static int MB = 8000000 / Character.SIZE;

    public MemoryNumbersGenerator(final String sourceFile, int memoryAllocationSize) throws IOException {
        numbersSource = new char[memoryAllocationSize * MB];
        BufferedReader reader = new BufferedReader(
                new FileReader(sourceFile));

        reader.read(numbersSource, 0, memoryAllocationSize * MB);
    }

    @Override
    public String generateSequence(int sequenceLength) throws IOException {
        int sequenceSize = (sequenceLength % 8 == 0) ?
                sequenceLength / Character.SIZE : sequenceLength / Character.SIZE + 1;
        return new String(numbersSource, 0, sequenceSize);
    }
}
