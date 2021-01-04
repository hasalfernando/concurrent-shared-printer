/** *********************************************************************
 * File:      PaperTechnician.java  (Thread)
 * Author:    Hasal Fernando
 * Contents:  6SENG002W CWK
 *            This thread represents the Paper Technician who is responsible of
 *            refilling papers when the printer runs out of paper.
 * Date:      20/12/20
 * Version:   1.0
 ************************************************************************ */

import utility.RandomNumberGenerator;

public class PaperTechnician extends Technician {

    //Constructor for Paper Technician.
    public PaperTechnician(String name, ThreadGroup threadGroup, LaserPrinter printer){
        super(name, threadGroup, printer);
    }

    public void setSuccessfulRefills(int successfulAttempt){
        this.successfulRefills = successfulAttempt;
    }

    public int getSuccessfulRefills(){
        return this.successfulRefills;
    }

    public int getTotalAttempts(){
        return this.totalAttempts;
    }

    //Paper technician's run method.
    @Override
    public void run(){
        //Attempting three times to refill paper.
        for(int i = 1; i <= 3; i++){
            try{
                this.printer.refillPaper(this, i);
                this.totalAttempts+=1;
                //Sleep for a random amount of time after each attempt.
                int minSleepTime = 1000;
                int maxSleepTime = 3000;
                int sleepTime = RandomNumberGenerator.randomNumberGenerator(minSleepTime, maxSleepTime);
                sleep(sleepTime);
            }
            catch (InterruptedException e){ //Catch any interruptions to the thread.
                e.printStackTrace();
            }
        }
    }

}
