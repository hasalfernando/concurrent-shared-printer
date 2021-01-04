/** *********************************************************************
 * File:      Student.java  (Thread)
 * Author:    Hasal Fernando
 * Contents:  6SENG002W CWK
 *            This represents the Student thread which is printing documents
 *            after creating them.
 * Date:      20/12/20
 * Version:   1.0
 ************************************************************************ */

import utility.MultiColorTerminal;
import utility.RandomNumberGenerator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Student extends Thread {

    //A student's attributes.
    private String name;
    private LaserPrinter printer;
    private ThreadGroup group;
    private List<String> printedBookList=new ArrayList<String>();

    //Student constructor.
    public Student(String name, LaserPrinter printer, ThreadGroup group){
        super(group, name);
        this.name = name;
        this.printer = printer;
        this.group = group;

    }

    //Student thread's run method.
    @Override
    public void run() {

        for(int i = 1; i <= 5; i++){
            //Generate a random number of pages between one and ten to the documents owned by a student.
            int minPageCount = 1;
            int maxPageCount = 10;
            int pageCount = RandomNumberGenerator.randomNumberGenerator(minPageCount, maxPageCount);
            //Document creation.
            Document doc = new Document(this.getName(), "Document-"+i, pageCount);
            System.out.println(MultiColorTerminal.GREEN + this + " is ready to print " + doc.getDocumentName() +
                    MultiColorTerminal.RESET);
            this.printer.printDocument(doc);
            //Add book to student's printed book list.
            this.printedBookList.add(doc.getDocumentName());
            try{
                //Sleep for a random amount of time after printing a document.
                int lowestSleepTime = 1000;
                int maximumSleepTime = 3000;
                int sleepTime = RandomNumberGenerator.randomNumberGenerator(lowestSleepTime, maximumSleepTime);
                sleep(sleepTime);
            }
            catch (InterruptedException e){ //Catch any interruptions to the thread.
                e.printStackTrace();
            }
        }
    }

    public List<String> getPrintedBookList(){
        return printedBookList;
    }

    @Override
    public String toString(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return (dtf.format(now)+ " - [ STUDENT ] \t\t\t- " + this.name);
    }
}
