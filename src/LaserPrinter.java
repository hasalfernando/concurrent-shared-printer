import java.awt.print.Paper;
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
        this.paperLevel = 250;
        this.tonerLevel = 50;
        this.noOfPrintedDocs = 0;
        this.students = students;
    }


    public synchronized void printDocument(Document document){
        logStatus("Pre-Print", this.toString());
        while(this.paperLevel < document.getNumberOfPages() || this.tonerLevel < document.getNumberOfPages()) {
            try {
                logStatus("[ Student ] "+ document.getUserID() + " is waiting to acquire printer ", this.toString());
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        logStatus("[ Student ] Printer acquired by student: " + document.getUserID() + " to print " + document.getDocumentName(), this.toString());

        this.paperLevel = this.paperLevel - document.getNumberOfPages();
        this.tonerLevel = this.tonerLevel - document.getNumberOfPages();
        this.noOfPrintedDocs++;
        logStatus("[ Student ] " + document.getUserID() + " printed " + document.getDocumentName() +
                " with " + document.getNumberOfPages() + " pages.", this.toString());

        notifyAll();

    }

    @Override
    public synchronized void replaceTonerCartridge(TonerTechnician technician, int replaceAttempt){
        while(this.tonerLevel >= ServicePrinter.Minimum_Toner_Level) {
            try {
                if (hasOperativeStudents()) {
                    logStatus("Toner Technician: " + technician.getName() + " waiting to refill toner cartridge for the " +
                            replaceAttempt + getOrdinal(replaceAttempt)+ " time.", this.toString());
                    wait(5000);
                } else {
                    logStatus("No active students. Aborting toner cartridge refill operation.", this.toString());
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(this.tonerLevel < ServicePrinter.Minimum_Toner_Level) {
            logStatus("Printer acquired by Toner Technician: " + technician.getName() + " to refill toner cartridge for the " +
                    replaceAttempt + getOrdinal(replaceAttempt) + " time.", this.toString());
            this.tonerLevel = ServicePrinter.Full_Toner_Level;
            int successesRefills = technician.getSuccessfulRefills() + 1;
            technician.setSuccessfulRefills(successesRefills);
            logStatus("Printer toner cartridge refill complete. Toner level increased to "+ this.tonerLevel + ".", this.toString());
        }

        notifyAll();
    }

    @Override
    public synchronized void refillPaper(PaperTechnician technician, int refillAttempt){
        while((this.paperLevel+ServicePrinter.SheetsPerPack) > ServicePrinter.Full_Paper_Tray) {
            try {
                if (hasOperativeStudents()) {
                    logStatus("Paper Technician: " + technician.getName() + " waiting to refill paper for the " +
                            refillAttempt + getOrdinal(refillAttempt) + " time.", this.toString());
                    wait(5000);
                } else {
                    logStatus("No active students. Aborting paper refill operation.", this.toString());
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if((this.paperLevel+ServicePrinter.SheetsPerPack) <= ServicePrinter.Full_Paper_Tray) {
            logStatus("Printer acquired by Paper Technician: " + technician.getName() + " to refill paper for the " +
                    refillAttempt + getOrdinal(refillAttempt) + " time.", this.toString());
            this.paperLevel = this.paperLevel + ServicePrinter.SheetsPerPack;
            int successesRefills = technician.getSuccessfulRefills() + 1;
            technician.setSuccessfulRefills(successesRefills);
            logStatus("Printer paper refill complete. Paper level increased to "+ this.paperLevel + ".", this.toString());
        }

        notifyAll();

    }

    @Override
    public String toString(){
        return ("[ PrinterID: "+ this.printerId + " | Paper Level: "+ this.paperLevel + " | Toner Level: " + this.tonerLevel
                + " | Documents Printed: " + this.noOfPrintedDocs + " ]");
    }

    private void logStatus(String timeLineEvent, String status){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        System.out.println(dtf.format(now)+ " - " + timeLineEvent + "\n- " + status);
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
