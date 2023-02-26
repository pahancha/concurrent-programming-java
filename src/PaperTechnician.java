public class PaperTechnician extends Thread {

    private LaserPrinter printer;
    private int numOfRefillAttempts = 3;

    //use to get random sleep time
    private int minSleepTime = 1000;
    private int maxSleepTime = 5000;

    public PaperTechnician(String name, ThreadGroup threadGroup, LaserPrinter printer) {
        super(threadGroup, name);
        this.printer = printer;
    }

    @Override
    public void run(){
        for (int i = 0; i < numOfRefillAttempts; i++) {
            printer.refillPaper();
            try {
                Thread.sleep(getRandomSleepTime(minSleepTime, maxSleepTime));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.printf("Paper Technician finished attempts to refill printer with paper packs.");
    }

    public int getRandomSleepTime(int min, int max){
        return (int) ((Math.random() * (max - min)) + min);
    }
}
