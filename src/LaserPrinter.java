public class LaserPrinter implements ServicePrinter {

    private int printerId;
    private int paperLevel;
    private int tonerLevel;
    private int noOfPrintedDocs;

    public LaserPrinter(int printerId){
        this.printerId = printerId;
    }

    public void printDocument(Document document){
        if(this.paperLevel >= document.getNumberOfPages() && this.tonerLevel >= document.getNumberOfPages()){
            System.out.println(document);
            this.paperLevel = this.paperLevel- document.getNumberOfPages();
            this.tonerLevel = this.tonerLevel- document.getNumberOfPages();
        }
    }

    public void replaceTonerCartridge( ){

    }

    public void refillPaper( ){

    }

    public String toString(){
        return "PrinterID: "+ this.printerId + "Paper Level: "+ this.paperLevel + "Toner Level: " + this.tonerLevel
                + "Documents Printed: " + this.noOfPrintedDocs;
    }

}
