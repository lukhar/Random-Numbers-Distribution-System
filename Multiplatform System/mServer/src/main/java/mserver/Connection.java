package mserver;

import generators.RandomNumbersGenerator;

import java.io.*;
import java.net.Socket;

/**
 * Created by IntelliJ IDEA.
 * User: lukash
 * Date: 5/8/11
 * Time: 9:27 PM
 */
public class Connection implements Runnable {
    private BufferedWriter outputWriter;
    private BufferedReader inputReader;
    private RandomNumbersGenerator randomNumbersGenerator;

    public Connection(Socket clientSocket, RandomNumbersGenerator randomNumbersGenerator) {
        this.randomNumbersGenerator = randomNumbersGenerator;
        try {
            inputReader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            outputWriter = new BufferedWriter(
                    new OutputStreamWriter(clientSocket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {

            int sequenceSize = Integer.parseInt(inputReader.readLine());
            int packagesAmount = Integer.parseInt(inputReader.readLine());

            System.out.println("sequence length : " + sequenceSize + " packages amount : " + packagesAmount);

            for (int i = 0; i < packagesAmount; ++i) {
                outputWriter.write(randomNumbersGenerator
                        .generateSequence(sequenceSize));
            }
            outputWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
