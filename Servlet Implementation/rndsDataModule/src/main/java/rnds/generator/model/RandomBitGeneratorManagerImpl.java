package rnds.generator.model;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class RandomBitGeneratorManagerImpl implements RandomBitGeneratorManager {

	private final static String DEFAULT_GENERATOR_PATH = "/dev/urandom";

	/* (non-Javadoc)
	 * @see rnds.generator.model.RandomBitGeneratorManager#getSequenceFromStream(java.io.InputStream, int)
	 */
	@Override
	public String getSequenceFromStream(InputStream randomNumbersSource,
			int randomSequenceLength) throws IOException {

		StringBuilder resultBuilder = new StringBuilder(
				randomSequenceLength + Integer.SIZE);

		do {
			resultBuilder.append(Integer.toBinaryString(randomNumbersSource
					.read()));
		} while (resultBuilder.length() < randomSequenceLength);

		return resultBuilder.substring(0, randomSequenceLength);
	}

	/* (non-Javadoc)
	 * @see rnds.generator.model.RandomBitGeneratorManager#getSequenceFromDefaultGenerator(int)
	 */
	@Override
	public String getSequenceFromDefaultGenerator(int randomSequenceLength)
			throws IOException {
		InputStream randomNumbersSource = new BufferedInputStream(
				new FileInputStream(DEFAULT_GENERATOR_PATH));
		StringBuilder resultBuilder = new StringBuilder(
				randomSequenceLength + Integer.SIZE);

		do {
			resultBuilder.append(Integer.toBinaryString(randomNumbersSource
					.read()));
		} while (resultBuilder.length() < randomSequenceLength);

		return resultBuilder.substring(0, randomSequenceLength);
	}

}
