/** *********************************************************************
 * File:      LaserPrinter.java  (Monitor Class)
 * Author:    Hasal Fernando
 * Contents:  6SENG002W CWK
 *            This represents the LaserPrinter which is a monitor.
 * Date:      20/12/20
 * Version:   1.0
 ************************************************************************ */

import utility.MultiColorTerminal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LaserPrinter implements ServicePrinter {

    //Laser printer attributes.
    private final String printerId;
    private int paperLevel;
    private int tonerLevel;
    private int noOfPrintedDocs;
    private ThreadGroup students;

    //LaserPrinter constructor.
    public LaserPrinter(String printerId, ThreadGroup students){

        this.printerId = printerId;
        this.paperLevel = ServicePrinter.Full_Paper_Tray;
        this.tonerLevel = 50;
        this.noOfPrintedDocs = 0;
        this.students = students;
    }

    //When a student is printing a document.
    public synchronized void printDocument(Document document){
        //Wait while there are not enough pages or toner to print the document.
        while(this.paperLevel < document.getNumberOfPages() || this.tonerLevel < document.getNumberOfPages()) {
            try {
                logStatus("[ STUDENT ] \t\t\t- "+ document.getUserID() + " is waiting to acquire printer ",
                        this.toString(), MultiColorTerminal.GREEN);
                wait();
            } catch (InterruptedException e) { //Catch any interruptions to the thread.
                e.printStackTrace();
            }
        }
        //Exits while loop when there are necessary papers and toner to print the document and student acquires the printer.
        logStatus("[ STUDENT ] \t\t\t- " + document.getUserID() + " acquired printer to print " +
                document.getDocumentName(), this.toString(), MultiColorTerminal.GREEN);

        //Change the paper and toner level accordingly.
        this.paperLevel = this.paperLevel - document.getNumberOfPages();
        this.tonerLevel = this.tonerLevel - document.getNumberOfPages();
        this.noOfPrintedDocs++;
        logStatus("[ STUDENT ] \t\t\t- " + document.getUserID() + " printed " + document.getDocumentName() +
                " with " + document.getNumberOfPages() + " pages.", this.toString(), MultiColorTerminal.GREEN_BOLD);

        //Notify all other threads that this thread has finished its execution.
        notifyAll();

    }

    //When the toner technician is trying to replace toner in the printer.
    @Override
    public synchronized void replaceTonerCartridge(TonerTechnician technician, int replaceAttempt){
        //While there is more or equal level of toner than the minimum accepted toner level in the printer.
        while(this.tonerLevel >= ServicePrinter.Minimum_Toner_Level) {
            try {
                //Wait only if there are students who are operating the printer.
                if (hasOperativeStudents()) {
                    logStatus("[ TONER TECHNICIAN ] \t- " + technician.getName() + " waiting to refill toner " +
                            "cartridge for the " + replaceAttempt + getOrdinal(replaceAttempt)+ " time.", this.toString(),
                            MultiColorTerminal.BLUE);
                    wait(5000);
                } else { //If there are no students operating the printer, abort the toner replacement process.
                    logStatus("[ TONER TECHNICIAN ] \t- No active students. Aborting toner cartridge refill " +
                            "operation.", this.toString(), MultiColorTerminal.BLUE);
                    break;
                }
            } catch (InterruptedException e) { //Catch any interruptions to the thread.
                e.printStackTrace();
            }
        }

        //Acquire printer only if the toner level is lower than the minimum accepted toner level.
        if(this.tonerLevel < ServicePrinter.Minimum_Toner_Level) {
            logStatus("[ TONER TECHNICIAN ] \t- Printer acquired by Toner Technician: " + technician.getName() +
                    " to refill toner cartridge for the " +
                    replaceAttempt + getOrdinal(replaceAttempt) + " time.", this.toString(), MultiColorTerminal.BLUE);
            //Change current toner level to the refilled toner level.
            this.tonerLevel = ServicePrinter.Full_Toner_Level;
            //Increase successful refills by one and update the technician's attribute.
            int successesRefills = technician.getSuccessfulRefills() + 1;
            technician.setSuccessfulRefills(successesRefills);
            logStatus("[ TONER TECHNICIAN ] \t- Printer toner cartridge refill complete. Toner level increased to "+
                    this.tonerLevel + ".", this.toString(), MultiColorTerminal.BLUE_BOLD);
        }

        //Notify all other threads that this thread has finished its execution.
        notifyAll();
    }

    //When the paper technician is trying to refill paper in the printer.
    @Override
    public synchronized void refillPaper(PaperTechnician technician, int refillAttempt){
        //While not enough paper is consumed to be replaced by a pack of sheets.
        while((this.paperLevel+ServicePrinter.SheetsPerPack) > ServicePrinter.Full_Paper_Tray) {
            try {
                //Wait only if there are students who are operating the printer.
                if (hasOperativeStudents()) {
                    logStatus("[ PAPER TECHNICIAN ] \t- " + technician.getName() + " waiting to refill paper for the " +
                            refillAttempt + getOrdinal(refillAttempt) + " time.", this.toString(), MultiColorTerminal.PURPLE);
                    wait(5000);
                } else { //If there are no students operating the printer, abort the paper replacement process.
                    logStatus("[ PAPER TECHNICIAN ] \t- No active students. Aborting paper refill operation.",
                            this.toString(), MultiColorTerminal.PURPLE);
                    break;
                }
            } catch (InterruptedException e) { //Catch any interruptions to the thread.
                e.printStackTrace();
            }
        }

        //Acquire printer only if the paper level is at a point which it can be filled with a pack of sheets.
        if((this.paperLevel+ServicePrinter.SheetsPerPack) <= ServicePrinter.Full_Paper_Tray) {
            logStatus("[ PAPER TECHNICIAN ] \t- Printer acquired by Paper Technician: " + technician.getName() +
                    " to refill paper for the " + refillAttempt + getOrdinal(refillAttempt) + " time.", this.toString(),
                    MultiColorTerminal.PURPLE);
            //Update current paper level.
            this.paperLevel = this.paperLevel + ServicePrinter.SheetsPerPack;
            //Increase successful refills by one and update the technician's attribute.
            int successesRefills = technician.getSuccessfulRefills() + 1;
            technician.setSuccessfulRefills(successesRefills);
            logStatus("[ PAPER TECHNICIAN ] \t- Printer paper refill complete. Paper level increased to "+ this.paperLevel + ".",
                    this.toString(), MultiColorTerminal.PURPLE_BOLD);
        }

        //Notify all other threads that this thread has finished its execution.
        notifyAll();

    }

    @Override
    public String toString(){
        return ("[ PrinterID: "+ this.printerId + " | Paper Level: "+ this.paperLevel + " | Toner Level: " + this.tonerLevel
                + " | Documents Printed: " + this.noOfPrintedDocs + " ]");
    }

    //Used to log status in the console.
    private void logStatus(String timeLineEvent, String status, String printColor){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        System.out.println(printColor + dtf.format(now)+ " - " + timeLineEvent + "\n- " + status +
                MultiColorTerminal.RESET);
    }

    //Checking if there are any active student threads from students thread group.
    private boolean hasOperativeStudents(){
        return (students.activeCount()>0);
    }

    //Get the ordinal to print technician's attempt number.
    private String getOrdinal(int number){

        String ordinal = "";
        switch (number) {
            case 1:
                ordinal = "st";
                break;
            case 2:
                ordinal = "nd";
                break;
            case 3:
                ordinal = "rd";
                break;
        }
        return ordinal;
    }

    public String getPrinterId() {
        return printerId;
    }

    public int getPaperLevel() {
        return paperLevel;
    }

    public int getTonerLevel() {
        return tonerLevel;
    }

    public int getNoOfPrintedDocs() {
        return noOfPrintedDocs;
    }
}
