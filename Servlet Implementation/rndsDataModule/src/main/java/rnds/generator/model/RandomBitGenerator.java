package rnds.generator.model;

import java.io.IOException;
import java.io.InputStream;

public interface RandomBitGenerator {

    public String generateSequence(int sequenceLength) throws IOException;
}