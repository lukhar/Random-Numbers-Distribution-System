package rnds.generator.model;

import java.io.IOException;

public interface RandomBitGenerator {

    public String generateSequence(int sequenceLength) throws IOException;
}