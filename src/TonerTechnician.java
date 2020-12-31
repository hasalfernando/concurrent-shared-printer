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
                int sleepTime = RandomNumberGenerator.randomNumberGenerator(1000, 3000);
                sleep(sleepTime);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
