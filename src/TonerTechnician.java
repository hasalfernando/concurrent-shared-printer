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

public class TonerTechnician extends Technician {

    public TonerTechnician(String name, ThreadGroup threadGroup, LaserPrinter printer) {
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

    @Override
    public void run(){
        for(int i = 1; i <= 3; i++){
            try{
                this.printer.replaceTonerCartridge(this, i);
                this.totalAttempts+=1;
                int minSleepTime = 1000;
                int maxSleepTime = 3000;
                int sleepTime = RandomNumberGenerator.randomNumberGenerator(minSleepTime, maxSleepTime);
                sleep(sleepTime);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
