/** *********************************************************************
 * File:      RandomNumberGenerator.java  (Class)
 * Author:    Hasal Fernando
 * Contents:  6SENG002W CWK
 *            This represents a random number generator which is
 *            used to generate random sleep times and page counts.
 * Date:      20/12/20
 * Version:   1.0
 ************************************************************************ */

package utility;

import java.util.Random;

public final class RandomNumberGenerator {

    //Generates a random number, given the lowest and highest possible integers.
    public static synchronized int randomNumberGenerator(int low, int high){
        Random r = new Random();
        return (r.nextInt(high-low) + low);
    }
}
