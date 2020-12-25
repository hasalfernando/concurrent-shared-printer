public class Student extends Thread {

    private String name;
    private LaserPrinter printer;
    private ThreadGroup group;

    public Student(String name, LaserPrinter printer, ThreadGroup group){
        this.name = name;
        this.printer = printer;
        this.group = group;
    }

    @Override
    public void run() {

        for(int i = 0; i<5; i++){
            Document doc = new Document(this.getName(), "Doc-"+i, (int)Math.random()*1000);
        }
    }
}
