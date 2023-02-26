public class TonerTechnician extends Thread {

    private LaserPrinter printer;
    private int numOfReplaceAttempts = 3;
    //use to get random sleep time
    private int minSleepTime = 1000;
    private int maxSleepTime = 5000;

    public TonerTechnician(String name, ThreadGroup threadGroup, LaserPrinter printer){
        super(threadGroup, name);
        this.printer = printer;
    }
    @Override
    public void run(){
        for (int i = 0; i < numOfReplaceAttempts; i++) {
            printer.replaceTonerCartridge();

            try {
                Thread.sleep(getRandomSleepTime(minSleepTime,maxSleepTime));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int getRandomSleepTime(int min, int max){
        return (int) ((Math.random() * (max - min)) + min);
    }
}

