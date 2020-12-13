public class LaserPrinter implements ServicePrinter {

    private int printerId;
    private int paperLevel;
    private int tonerLevel;
    private int noOfPrintedPapers;

    public LaserPrinter(int printerId){
        this.printerId = printerId;
    }

    public void printDocument(Document document){

        System.out.println(document);
    }

    public void replaceTonerCartridge( ){

    }

    public void refillPaper( ){

    }

    public String toString(){
        return "PrinterID: "+ this.printerId + "Paper Level: "+ this.paperLevel + "Toner Level: " + this.tonerLevel
                + "Documents Printed: " + this.noOfPrintedPapers;
    }

}
