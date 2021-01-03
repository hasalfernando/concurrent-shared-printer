package utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WriteStatusFile {

    public static void writeToFile(String status){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HH.mm.ss");
        LocalDateTime now = LocalDateTime.now();
        try {
            PrintWriter writer = new PrintWriter(System.getProperty("user.dir")+"\\src\\logs\\FinalPrinterStatus_"
                    + dtf.format(now)+".txt", "UTF-8");
            writer.println(status);
            writer.close();
            System.out.println("Writing to file successful\nThank you for using the printer.");
        } catch (IOException e) {
            System.out.println("File writing unsuccessful. Error Occurred!");
            e.printStackTrace();
        }
    }
}
