import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LaserPrinter implements ServicePrinter {

    //Monitor Class

    private int printerId;
    private int paperLevel;
    private int tonerLevel;
    private int noOfPrintedDocs;
    private ThreadGroup students;

    public LaserPrinter(int printerId, ThreadGroup students){

        this.printerId = printerId;
        this.paperLevel = ServicePrinter.Full_Paper_Tray;
        this.tonerLevel = ServicePrinter.Full_Toner_Level;
        this.noOfPrintedDocs = 0;
        this.students = students;
    }

    public synchronized void printDocument(Document document){
        logStatus("Pre-Print", this.toString());
        while(this.paperLevel < document.getNumberOfPages() || this.tonerLevel < document.getNumberOfPages()) {
            try {
                logStatus("Waiting to acquire printer ", this.toString());
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        logStatus("Printer acquired ", this.toString());

        this.paperLevel = this.paperLevel - document.getNumberOfPages();
        this.tonerLevel = this.tonerLevel - document.getNumberOfPages();
        this.noOfPrintedDocs++;
        logStatus("Student " + document.getUserID() + " printed " + document.getDocumentName() +
                " with " + document.getNumberOfPages() + " pages.", this.toString());

        notifyAll();

    }

    @Override
    public synchronized void replaceTonerCartridge(String technician, int replaceAttempt){
        while(this.tonerLevel >= ServicePrinter.Minimum_Toner_Level) {
            try {
                if (hasOperativeStudents()) {
                    logStatus("Technician " + technician + " waits to refill toner cartridge for the " +
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
        logStatus("Printer acquired by technician " + technician + " to refill toner cartridge for the "+
                replaceAttempt + getOrdinal(replaceAttempt)+ " time.", this.toString());
        if(this.tonerLevel < ServicePrinter.Minimum_Toner_Level){
            this.tonerLevel = ServicePrinter.PagesPerTonerCartridge;
            logStatus("Printer toner cartridge refill complete.", this.toString());
        }
    }

    @Override
    public synchronized void refillPaper(String technician, int refillAttempt){
        while((this.paperLevel+ServicePrinter.SheetsPerPack) > ServicePrinter.Full_Paper_Tray){
            try {
                if (hasOperativeStudents()) {
                    logStatus("Technician " + technician + " waits to refill paper for the " +
                            refillAttempt + getOrdinal(refillAttempt)+ " time.", this.toString());
                    wait(5000);
                }
                else {
                    logStatus("No active students. Aborting paper refill operation.", this.toString());
                    break;
                }
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            logStatus("Printer acquired by technician "+ technician + " to refill paper for the "+
                    refillAttempt + getOrdinal(refillAttempt)+ " time.", this.toString());
            if(this.paperLevel + ServicePrinter.SheetsPerPack < ServicePrinter.Full_Paper_Tray){
                this.paperLevel = this.paperLevel + ServicePrinter.SheetsPerPack;
                logStatus("Printer paper refill complete.", this.toString());
            }
        }
    }

    @Override
    public String toString(){
        return new String("[ PrinterID: "+ this.printerId + " Paper Level: "+ this.paperLevel + " Toner Level: " + this.tonerLevel
                + " Documents Printed: " + this.noOfPrintedDocs + " ]");
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
                ordinal = "st";
                break;
        }
        return ordinal;
    }
}
