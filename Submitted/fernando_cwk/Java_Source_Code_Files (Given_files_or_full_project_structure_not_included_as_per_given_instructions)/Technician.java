/** *********************************************************************
 * File:      Technician.java  (Thread)
 * Author:    Hasal Fernando
 * Contents:  6SENG002W CWK
 *            This is the abstract Technician thread which represents the
 *            attributes which both types of technicians should have.
 * Date:      20/11/20
 * Version:   1.0
 ************************************************************************ */

public abstract class Technician extends Thread {

    //Technician's attributes.
    protected LaserPrinter printer;
    protected String name;
    protected int totalAttempts = 0;
    protected int successfulRefills = 0;

    //Constructor for Technician.
    public Technician(String name, ThreadGroup threadGroup, LaserPrinter printer){
        super(threadGroup, name);
        this.printer = printer;
        this.name = name;

    }

}
