import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Student extends Thread {

    private String name;
    private LaserPrinter printer;
    private ThreadGroup group;
    private List<String> printedBookList=new ArrayList<String>();

    public Student(String name, LaserPrinter printer, ThreadGroup group){
        super(group, name);
        this.name = name;
        this.printer = printer;
        this.group = group;

    }

    @Override
    public void run() {

        for(int i = 1; i <= 5; i++){
            int pageCount = RandomNumberGenerator.randomNumberGenerator(1, 10);
            Document doc = new Document(this.getName(), "Document-"+i, pageCount);
            System.out.println(this + " is ready to print " + doc.getDocumentName());
            this.printer.printDocument(doc);
            this.printedBookList.add(doc.getDocumentName());
            try{
                int sleepTime = RandomNumberGenerator.randomNumberGenerator(1000, 3000);
                sleep(sleepTime);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public List<String> getPrintedBookList(){
        return printedBookList;
    }

    @Override
    public String toString(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return (dtf.format(now)+ " - " + "Student: " + this.name);
    }
}
