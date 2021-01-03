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

    private final String printerId;
    private int paperLevel;
    private int tonerLevel;
    private int noOfPrintedDocs;
    private ThreadGroup students;

    public LaserPrinter(String printerId, ThreadGroup students){

        this.printerId = printerId;
        this.paperLevel = ServicePrinter.Full_Paper_Tray;
        this.tonerLevel = 50;
        this.noOfPrintedDocs = 0;
        this.students = students;
    }


    public synchronized void printDocument(Document document){
        while(this.paperLevel < document.getNumberOfPages() || this.tonerLevel < document.getNumberOfPages()) {
            try {
                logStatus("[ STUDENT ] \t\t\t- "+ document.getUserID() + " is waiting to acquire printer ",
                        this.toString(), MultiColorTerminal.GREEN);
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        logStatus("[ STUDENT ] \t\t\t- " + document.getUserID() + " acquired printer to print " +
                document.getDocumentName(), this.toString(), MultiColorTerminal.GREEN);

        this.paperLevel = this.paperLevel - document.getNumberOfPages();
        this.tonerLevel = this.tonerLevel - document.getNumberOfPages();
        this.noOfPrintedDocs++;
        logStatus("[ STUDENT ] \t\t\t- " + document.getUserID() + " printed " + document.getDocumentName() +
                " with " + document.getNumberOfPages() + " pages.", this.toString(), MultiColorTerminal.GREEN_BOLD);

        notifyAll();

    }

    @Override
    public synchronized void replaceTonerCartridge(TonerTechnician technician, int replaceAttempt){
        while(this.tonerLevel >= ServicePrinter.Minimum_Toner_Level) {
            try {
                if (hasOperativeStudents()) {
                    logStatus("[ TONER TECHNICIAN ] \t- " + technician.getName() + " waiting to refill toner " +
                            "cartridge for the " + replaceAttempt + getOrdinal(replaceAttempt)+ " time.", this.toString(),
                            MultiColorTerminal.BLUE);
                    wait(5000);
                } else {
                    logStatus("[ TONER TECHNICIAN ] \t- No active students. Aborting toner cartridge refill " +
                            "operation.", this.toString(),
                            MultiColorTerminal.BLUE);
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(this.tonerLevel < ServicePrinter.Minimum_Toner_Level) {
            logStatus("[ TONER TECHNICIAN ] \t- Printer acquired by Toner Technician: " + technician.getName() +
                    " to refill toner cartridge for the " +
                    replaceAttempt + getOrdinal(replaceAttempt) + " time.", this.toString(), MultiColorTerminal.BLUE);
            this.tonerLevel = ServicePrinter.Full_Toner_Level;
            int successesRefills = technician.getSuccessfulRefills() + 1;
            technician.setSuccessfulRefills(successesRefills);
            logStatus("[ TONER TECHNICIAN ] \t- Printer toner cartridge refill complete. Toner level increased to "+
                    this.tonerLevel + ".", this.toString(), MultiColorTerminal.BLUE_BOLD);
        }

        notifyAll();
    }

    @Override
    public synchronized void refillPaper(PaperTechnician technician, int refillAttempt){
        while((this.paperLevel+ServicePrinter.SheetsPerPack) > ServicePrinter.Full_Paper_Tray) {
            try {
                if (hasOperativeStudents()) {
                    logStatus("[ PAPER TECHNICIAN ] \t- " + technician.getName() + " waiting to refill paper for the " +
                            refillAttempt + getOrdinal(refillAttempt) + " time.", this.toString(), MultiColorTerminal.PURPLE);
                    wait(5000);
                } else {
                    logStatus("[ PAPER TECHNICIAN ] \t- No active students. Aborting paper refill operation.",
                            this.toString(), MultiColorTerminal.PURPLE);
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if((this.paperLevel+ServicePrinter.SheetsPerPack) <= ServicePrinter.Full_Paper_Tray) {
            logStatus("[ PAPER TECHNICIAN ] \t- Printer acquired by Paper Technician: " + technician.getName() + " to refill paper for the " +
                    refillAttempt + getOrdinal(refillAttempt) + " time.", this.toString(), MultiColorTerminal.PURPLE);
            this.paperLevel = this.paperLevel + ServicePrinter.SheetsPerPack;
            int successesRefills = technician.getSuccessfulRefills() + 1;
            technician.setSuccessfulRefills(successesRefills);
            logStatus("[ PAPER TECHNICIAN ] \t- Printer paper refill complete. Paper level increased to "+ this.paperLevel + ".",
                    this.toString(), MultiColorTerminal.PURPLE_BOLD);
        }

        notifyAll();

    }

    @Override
    public String toString(){
        return ("[ PrinterID: "+ this.printerId + " | Paper Level: "+ this.paperLevel + " | Toner Level: " + this.tonerLevel
                + " | Documents Printed: " + this.noOfPrintedDocs + " ]");
    }

    private void logStatus(String timeLineEvent, String status, String printColor){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        System.out.println(printColor + dtf.format(now)+ " - " + timeLineEvent + " - " + status +
                MultiColorTerminal.RESET);
    }

    private boolean hasOperativeStudents(){
        return (students.activeCount()>0);
    }

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
