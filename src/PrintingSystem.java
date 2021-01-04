/** *********************************************************************
 * File:      PrintingSystem.java  (Class)
 * Author:    Hasal Fernando
 * Contents:  6SENG002W CWK
 *            This represents the Printing System which is responsible of
 *            creating all the threads, thread groups and starting the
 *            execution of all those threads.
 * Date:      20/12/20
 * Version:   1.0
 ************************************************************************ */

import utility.MultiColorTerminal;
import utility.WriteStatusFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrintingSystem {

    public static void main(String[] args) {

        //Thread Groups creation.
        ThreadGroup studentGroup = new ThreadGroup("Students");
        ThreadGroup technicianGroup = new ThreadGroup("Technicians");

        //Laser printer monitor creation.
        LaserPrinter printer = new LaserPrinter("CANON ImageCLASS LBP-6030", studentGroup);

        //Student threads creation.
        Student student1 = new Student("John", printer, studentGroup);
        Student student2 = new Student("Elgar", printer, studentGroup);
        Student student3 = new Student("Quinton", printer, studentGroup);
        Student student4 = new Student("Ashley", printer, studentGroup);

        //List to store student objects.
        List<Student> studentList =new ArrayList<Student>();
        studentList.add(student1);
        studentList.add(student2);
        studentList.add(student3);
        studentList.add(student4);

        //Technician thread creation.
        Technician paperTechnician = new PaperTechnician("Brendon", technicianGroup, printer);
        Technician tonerTechnician = new TonerTechnician("Dale", technicianGroup, printer);

        //Starting all threads.
        student1.start();
        student2.start();
        student3.start();
        student4.start();
        paperTechnician.start();
        tonerTechnician.start();

        //Join threads and make the main thread wait till all processes of every thread finish.
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

        //Printing the final status of the system.
        System.out.println("\n----------FINAL STATUS----------\n");

        //Variables used to print student status.
        String studentStatus = "STUDENTS\n" + "Student \t Printed Documents List\n";
        for(int i = 0; i < studentList.size(); i++){
            studentStatus = (studentStatus.concat(studentList.get(i).getName() + "\t\t" +
                    studentList.get(i).getPrintedBookList()+"\n"));
        }

        //Variables used to print technician status.
        String technicianStatusInitializer = "TECHNICIANS\nTechnician \t  Total Attempts \t Successful\n";
        String paperTechnicianStatus = (paperTechnician.getName() + "\t\t\t\t" +
                ((PaperTechnician) paperTechnician).getTotalAttempts() + "\t\t\t\t  "
                + ((PaperTechnician) paperTechnician).getSuccessfulRefills() + "\n");

        String tonerTechnicianStatus = (tonerTechnician.getName() + "\t\t\t\t" +
                ((TonerTechnician) tonerTechnician).getTotalAttempts() + "\t\t\t\t  "
                + ((TonerTechnician) tonerTechnician).getSuccessfulRefills() + "\n");

        //Variables used to print printer status.
        String printerStatusInitializer = "PRINTER - " + printer.getPrinterId() + "\n" +
                "Attribute \t Current Level \t Full Capacity\n";
        String printerStatus =
                "Paper Level \t  " + printer.getPaperLevel() + "\t\t\t  " + ServicePrinter.Full_Paper_Tray + "\n" +
                "Toner Level \t  " + printer.getTonerLevel() + "\t\t\t  " + ServicePrinter.Full_Toner_Level + "\n" +
                "A total of " + printer.getNoOfPrintedDocs() + " documents were printed.";

        String finalStatus = studentStatus + technicianStatusInitializer + paperTechnicianStatus + tonerTechnicianStatus
                + printerStatusInitializer+ printerStatus;
        //Printing Student status.
        System.out.println(MultiColorTerminal.GREEN + studentStatus);

        //Printing Technician status.
        System.out.println(MultiColorTerminal.RESET + technicianStatusInitializer + MultiColorTerminal.PURPLE +
                paperTechnicianStatus + MultiColorTerminal.BLUE + tonerTechnicianStatus);

        //Printing Printer status.
        System.out.println(MultiColorTerminal.RESET + printerStatusInitializer + MultiColorTerminal.YELLOW +
                printerStatus + MultiColorTerminal.RESET);

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nDo you want to save the final status of printer to a file?(Please press the relevant number." +
                "\n1. Yes\n2.No");
        if(scanner.nextInt()==1){
            WriteStatusFile.writeToFile(finalStatus);
        }
        else{
            System.out.println("No logs saved!\nThank you for using the printer.");
        }
    }
}
