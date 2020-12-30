public abstract class Technician extends Thread {

    private LaserPrinter printer;
    private String name;

    public Technician(String name, ThreadGroup threadGroup, LaserPrinter printer){
        super(threadGroup, name);
        this.printer = printer;
    }
}
