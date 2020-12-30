import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Student extends Thread {

    private String name;
    private LaserPrinter printer;
    private ThreadGroup group;

    public Student(String name, LaserPrinter printer, ThreadGroup group){
        super(group, name);
        this.name = name;
        this.printer = printer;
        this.group = group;

    }

    @Override
    public void run() {

        for(int i = 1; i < 6; i++){
            Document doc = new Document(this.getName(), "Document-"+i, pageNumberGenerator());
            System.out.println(this + " is ready to print " + doc.getDocumentName());
            this.printer.printDocument(doc);
            System.out.println(this + " finished printing "+ doc.getDocumentName() + " with " + doc.getNumberOfPages() +
                    " pages.");

            try{
                sleep((int)Math.random()*1000);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }

        }
    }


    private int pageNumberGenerator(){
        Random r = new Random();
        int low = 10;
        int high = 100;
        return (r.nextInt(high-low) + low);
    }

    @Override
    public String toString(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return (dtf.format(now)+ " - " + "Student: " + this.name);
    }
}
