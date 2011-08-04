/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rnds.generators;

import java.io.IOException;

/**
 *
 * @author lukash
 */
interface RandomNumbersGenerator {
     public String generateSequence(int sequenceLength) throws IOException;
}
