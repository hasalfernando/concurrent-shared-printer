public abstract class Technician extends Thread {

    protected LaserPrinter printer;
    protected String name;
    protected int totalAttempts = 0;
    protected int successfulRefills = 0;

    public Technician(String name, ThreadGroup threadGroup, LaserPrinter printer){
        super(threadGroup, name);
        this.printer = printer;
        this.name = name;

    }

}
