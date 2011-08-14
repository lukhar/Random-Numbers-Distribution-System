package rnds.generator.model;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: lukash
 * Date: 8/13/11
 * Time: 11:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class BinaryRandomBitGenerator {
    private InputStream inputStream;

    public BinaryRandomBitGenerator(String source) throws FileNotFoundException {
        this.inputStream = new BufferedInputStream(new FileInputStream(source));
    }

    public byte[] generateSequence(int dataLength) throws IOException {
        int off = 0;
        int read;
        byte[] output = new byte[dataLength];
        try {
            do {
                read = inputStream.read(output, off, dataLength);
                off = read;
                dataLength = dataLength - read;
            } while (read != -1 && dataLength > 0);
        } finally {
            inputStream.close();
        }
        return output;
    }
}
