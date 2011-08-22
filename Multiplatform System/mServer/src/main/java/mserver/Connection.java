package mserver;

import generators.RandomNumbersGenerator;

import java.io.*;
import java.net.Socket;

/**
 * Created by IntelliJ IDEA.
 * User: lukash
 * Date: 8/15/11
 * Time: 1:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class Connection implements Runnable {
    private BufferedOutputStream outputWriter;
    private BufferedReader inputReader;
    private RandomNumbersGenerator binaryNumbersGenerator;

    public Connection(Socket clientSocket, RandomNumbersGenerator randomNumbersGenerator) {
        this.binaryNumbersGenerator = randomNumbersGenerator;
        try {
            inputReader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            outputWriter = new BufferedOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {

            int sequenceSize = Integer.parseInt(inputReader.readLine());
            int packagesAmount = Integer.parseInt(inputReader.readLine());

            System.out.println("sequence length : " + sequenceSize + " packages amount : " + packagesAmount);

            byte[] outputData;
            for (int i = 0; i < packagesAmount; ++i) {
                outputData = binaryNumbersGenerator.generateSequence(sequenceSize).getBytes();
                outputWriter.write(outputData, 0, sequenceSize);
            }
            outputWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
