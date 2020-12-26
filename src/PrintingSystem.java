public class PrintingSystem {

    public static void main(String[] args) {

        //Thread Groups
        ThreadGroup studentGroup = new ThreadGroup("Students");
        ThreadGroup technicianGroup = new ThreadGroup("Technicians");

        LaserPrinter printer = new LaserPrinter(1, studentGroup);

        Student student1 = new Student("John", printer, studentGroup);
        Student student2 = new Student("Elgar", printer, studentGroup);
        Student student3 = new Student("Quinton", printer, studentGroup);
        Student student4 = new Student("Ashley", printer, studentGroup);

        Technician paperTechnician = new PaperTechnician();
        Technician tonerTechnician = new TonerTechnician();

    }
}
