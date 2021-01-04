/** *********************************************************************
 * File:      WriteStatusFile.java  (Class)
 * Author:    Hasal Fernando
 * Contents:  6SENG002W CWK
 *            This is the utility class which is used to write the final
 *            status of the printer to a file, based on the user's choice.
 * Date:      20/12/20
 * Version:   1.0
 ************************************************************************ */

package utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WriteStatusFile {

    //Writes final status after printing, to a file with appended date and time to its name.
    public static void writeToFile(String status){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HH.mm.ss");
        LocalDateTime now = LocalDateTime.now();
        try {
            PrintWriter writer = new PrintWriter(System.getProperty("user.dir")+"\\src\\logs\\FinalPrinterStatus_"
                    + dtf.format(now)+".txt", "UTF-8");
            writer.println(status);
            writer.close();
            System.out.println("Writing to file successful\nThank you for analysing the printer.");
        } catch (IOException e) {
            System.out.println("File writing unsuccessful. Error Occurred!");
            e.printStackTrace();
        }
    }
}
