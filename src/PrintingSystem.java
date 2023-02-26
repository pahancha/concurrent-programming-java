public class PrintingSystem {
    public static void main(String[] args) {
        ThreadGroup studentsGroup = new ThreadGroup("Students' thread group");
        ThreadGroup techniciansGroup = new ThreadGroup("Technicians' thread group");

        LaserPrinter printer = new LaserPrinter("HP OfficeJet 250",10, 50,studentsGroup);

        Thread tonerTechnician = new TonerTechnician("Toner Technician", techniciansGroup,printer);
        Thread paperTechnician = new PaperTechnician("Paper Technician", techniciansGroup,printer);

        Thread student1 = new Student("Student1",studentsGroup,printer,paperTechnician, tonerTechnician);
        Thread student2 = new Student("Student2",studentsGroup,printer, paperTechnician, tonerTechnician);
        Thread student3 = new Student("Student3",studentsGroup,printer, paperTechnician, tonerTechnician);
        Thread student4 = new Student("Student4",studentsGroup,printer, paperTechnician, tonerTechnician);



        student1.start();
        student2.start();
        student3.start();
        student4.start();

        tonerTechnician.start();
        paperTechnician.start();

        try {
            student1.join();
            student2.join();
            student3.join();
            student4.join();
            paperTechnician.join();
            tonerTechnician.join();
        } catch (InterruptedException ex) {
            System.out.println(ex.toString());
        }
        System.out.println();
        System.out.println();
        System.out.println("*************************************************************************************************************");
        System.out.println("                                    Students Printed their Documents!                                        ");
        System.out.println();
        System.out.println("SUMMARY: ");
        System.out.println(printer);
        System.out.println("************************************************************************************************************");

    }
}
