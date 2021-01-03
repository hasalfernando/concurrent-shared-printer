import utility.MultiColorTerminal;

public class PrintingSystem {

    public static void main(String[] args) {

        //Thread Groups
        ThreadGroup studentGroup = new ThreadGroup("Students");
        ThreadGroup technicianGroup = new ThreadGroup("Technicians");

        LaserPrinter printer = new LaserPrinter("CANON ImageCLASS LBP-6030", studentGroup);

        Student student1 = new Student("John", printer, studentGroup);
        Student student2 = new Student("Elgar", printer, studentGroup);
        Student student3 = new Student("Quinton", printer, studentGroup);
        Student student4 = new Student("Ashley", printer, studentGroup);

        Technician paperTechnician = new PaperTechnician("Brendon", technicianGroup, printer);
        Technician tonerTechnician = new TonerTechnician("Dale", technicianGroup, printer);

        student1.start();
        student2.start();
        student3.start();
        student4.start();
        paperTechnician.start();
        tonerTechnician.start();

        try {
            student1.join();
            student2.join();
            student3.join();
            student4.join();
            paperTechnician.join();
            tonerTechnician.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n----------FINAL STATUS----------\n");
        System.out.println(MultiColorTerminal.GREEN + "STUDENTS\n" +
                "Student \t Printed Documents List\n"+
                student1.getName() + "\t\t" + student1.getPrintedBookList() + "\n" +
                student2.getName() + "\t\t" + student2.getPrintedBookList() + "\n" +
                student3.getName() + "\t\t" + student3.getPrintedBookList() + "\n" +
                student4.getName() + "\t\t" + student4.getPrintedBookList() + "\n" +
                MultiColorTerminal.RESET
        );
        System.out.println("TECHNICIANS\n" +
                "Technician \t  Total Attempts \t Successful\n"+
                MultiColorTerminal.PURPLE + paperTechnician.getName() + "\t\t\t\t" + ((PaperTechnician) paperTechnician).getTotalAttempts() + "\t\t\t\t  "
                + ((PaperTechnician) paperTechnician).getSuccessfulRefills() + "\n" +
                MultiColorTerminal.BLUE + tonerTechnician.getName() + "\t\t\t\t" + ((TonerTechnician) tonerTechnician).getTotalAttempts() + "\t\t\t\t  "
                + ((TonerTechnician) tonerTechnician).getSuccessfulRefills() + "\n"
        );
        System.out.println(MultiColorTerminal.YELLOW + "PRINTER\n" + printer.getPrinterId() +
                "Attribute \t Current Level \t Full Capacity\n"+
                "Paper Level \t  " + printer.getPaperLevel() + "\t\t\t  " + ServicePrinter.Full_Paper_Tray + "\n" +
                "Toner Level \t  " + printer.getTonerLevel() + "\t\t\t  " + ServicePrinter.Full_Toner_Level + "\n" +
                "A total of " + printer.getNoOfPrintedDocs() + " documents were printed." + MultiColorTerminal.RESET
        );
    }
}
