public class PaperTechnician extends Technician {

    private String name;
    private ThreadGroup threadGroup;
    private LaserPrinter printer;

    public PaperTechnician(String name, ThreadGroup threadGroup, LaserPrinter printer){
        super(name, threadGroup, printer);
        this.name = name;
        this.threadGroup = threadGroup;
        this.printer = printer;
    }

    @Override
    public void run(){
        for(int i = 1; i <= 3; i++){
            try{
                this.printer.refillPaper(this.name, i);
                sleep((int)Math.random()*1000);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

}
