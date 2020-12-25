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
        if(this.paperLevel >= document.getNumberOfPages() && this.tonerLevel >= document.getNumberOfPages()){
            System.out.println(document);
            this.paperLevel = this.paperLevel- document.getNumberOfPages();
            this.tonerLevel = this.tonerLevel- document.getNumberOfPages();
        }
    }

    public void replaceTonerCartridge( ){

    }

    @Override
    public void refillPaper( ){
        while((this.paperLevel+ServicePrinter.SheetsPerPack) > ServicePrinter.Full_Paper_Tray){
            try {
                if (hasOperativeStudents()) {
                    wait(5000);
                }
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString(){
        return new String("[ PrinterID: "+ this.printerId + "Paper Level: "+ this.paperLevel + "Toner Level: " + this.tonerLevel
                + "Documents Printed: " + this.noOfPrintedDocs + " ]");
    }

    private boolean hasOperativeStudents(){
        return (students.activeCount()>0);
    }
}
