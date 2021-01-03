public class PaperTechnician extends Technician {

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

    @Override
    public void run(){
        for(int i = 1; i <= 3; i++){
            try{
                this.printer.refillPaper(this, i);
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
