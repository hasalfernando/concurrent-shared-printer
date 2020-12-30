public class TonerTechnician extends Technician {

    private String name;
    private ThreadGroup threadGroup;
    private LaserPrinter printer;

    public TonerTechnician(String name, ThreadGroup threadGroup, LaserPrinter printer) {
        super(name, threadGroup, printer);
        this.name = name;
        this.threadGroup = threadGroup;
        this.printer = printer;
    }


    @Override
    public void run(){
        for(int i = 1; i <= 3; i++){
            try{
                this.printer.replaceTonerCartridge(this.name, i);
                sleep((int)Math.random()*1000);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
