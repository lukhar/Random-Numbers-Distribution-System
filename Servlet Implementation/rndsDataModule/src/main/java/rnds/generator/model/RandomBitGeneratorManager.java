package rnds.generator.model;

import java.io.IOException;
import java.io.InputStream;

public interface RandomBitGeneratorManager {

	public String getSequenceFromStream(InputStream randomNumbersSource,
			int randomSequenceLength) throws IOException;

	public String getSequenceFromDefaultGenerator(int randomSequenceLength)
			throws IOException;
}