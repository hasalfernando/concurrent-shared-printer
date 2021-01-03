/** *********************************************************************
 * File:      Student.java  (Thread)
 * Author:    Hasal Fernando
 * Contents:  6SENG002W CWK
 *            This represents the Student thread.
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

    private String name;
    private LaserPrinter printer;
    private ThreadGroup group;
    private List<String> printedBookList=new ArrayList<String>();

    public Student(String name, LaserPrinter printer, ThreadGroup group){
        super(group, name);
        this.name = name;
        this.printer = printer;
        this.group = group;

    }

    @Override
    public void run() {

        for(int i = 1; i <= 5; i++){
            int minPageCount = 1;
            int maxPageCount = 10;
            int pageCount = RandomNumberGenerator.randomNumberGenerator(minPageCount, maxPageCount);
            Document doc = new Document(this.getName(), "Document-"+i, pageCount);
            System.out.println(MultiColorTerminal.GREEN + this + " is ready to print " + doc.getDocumentName() +
                    MultiColorTerminal.RESET);
            this.printer.printDocument(doc);
            this.printedBookList.add(doc.getDocumentName());
            try{
                int sleepTime = RandomNumberGenerator.randomNumberGenerator(1000, 3000);
                sleep(sleepTime);
            }
            catch (InterruptedException e){
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
