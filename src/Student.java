public class Student extends Thread{

    private Printer printer;
    private String documentName = "Document";
    private int numOfDocsPerStudent = 5;

    //use to generate random page counts
    private int minNumOfPaperPerDoc = 1;
    private int maxNumOfPaperPerDoc = 10;

    private Thread tonerTechnician;
    private Thread paperTechnician;

    //use to generate random sleep time
    int minSleepTime = 1000;
    int maxSleepTime = 5000;
    public Student(String name,ThreadGroup threadGroup, Printer printer){
        super(threadGroup, name);
        this.printer = printer;
    }

    public Student(String name,ThreadGroup threadGroup, Printer printer, Thread tonerTechnician, Thread paperTechnician){
        super(threadGroup, name);
        this.printer = printer;
        this.tonerTechnician = tonerTechnician;
        this.paperTechnician = paperTechnician;
    }
    @Override
    public void run(){
        for(int i = 1; i <= numOfDocsPerStudent; i++){
            Document document = new Document(this.getName(),documentName + i,getRandomNumber(minNumOfPaperPerDoc, maxNumOfPaperPerDoc));
            printer.printDocument(document);

            try {
                Thread.sleep(getRandomSleepTime(minSleepTime, maxSleepTime));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "printer=" + printer +
                ", documentName='" + documentName + '\'' +
                ", numOfDocsPerStudent=" + numOfDocsPerStudent +
                ", tonerTechnician=" + tonerTechnician +
                ", paperTechnician=" + paperTechnician +
                '}';
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public int getRandomSleepTime(int min, int max){
        return (int) ((Math.random() * (max - min)) + min);
    }
}
