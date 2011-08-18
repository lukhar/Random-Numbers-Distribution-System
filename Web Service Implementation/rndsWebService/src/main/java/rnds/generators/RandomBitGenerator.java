package rnds.generators;

import java.io.IOException;

public interface RandomBitGenerator {

    public String generateSequence(int sequenceLength) throws IOException;
}