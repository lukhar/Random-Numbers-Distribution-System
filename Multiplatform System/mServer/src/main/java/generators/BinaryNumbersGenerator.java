package generators;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: lukash
 * Date: 8/15/11
 * Time: 1:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class BinaryNumbersGenerator {
    private InputStream inputStream;

    public BinaryNumbersGenerator(String source) throws FileNotFoundException {
        this.inputStream = new BufferedInputStream(new FileInputStream(source));
    }

    public byte[] generateSequence(int dataLength) throws IOException {
        int off = 0;
        int read;
        byte[] output = new byte[dataLength];
        do {
            read = inputStream.read(output, off, dataLength);
            off = read;
            dataLength = dataLength - read;
        } while (read != -1 && dataLength > 0);
        return output;
    }

    @Override
    protected void finalize() throws Throwable {
        inputStream.close();
        super.finalize();
    }
}
