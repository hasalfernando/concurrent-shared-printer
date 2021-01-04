/** *********************************************************************
 * File:      MultiColorTerminal.java  (Class)
 * Author:    Hasal Fernando
 * Contents:  6SENG002W CWK
 *            This represents the utility that is used to print colored
 *            outputs in the console.
 * Date:      20/12/20
 * Version:   1.0
 ************************************************************************ */

package utility;

public class MultiColorTerminal {

    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE

    // Bold
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE

}
