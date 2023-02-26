public class LaserPrinter implements ServicePrinter{

    private String printerName;
    private int currentPaperLevel;
    private int currentTonerLevel;
    private int numOfDocumentsPrinted;
    private ThreadGroup students;

    public LaserPrinter(String printerName, int paperLevel, int tonerLevel, ThreadGroup threadGroup) {
        this.printerName = printerName;
        this.currentPaperLevel = paperLevel;
        this.currentTonerLevel = tonerLevel;
        this.numOfDocumentsPrinted = 0;
        this.students = threadGroup;
    }

    @Override
    public synchronized void printDocument(Document document) {
        int numOfPagesInDoc = document.getNumberOfPages();
        String student = document.getUserID();
        String documentName = document.getDocumentName();
        boolean insufficientPaperLevel = false;
        boolean insufficientTonerLevel = false;
        if(numOfPagesInDoc >= currentPaperLevel)
            insufficientPaperLevel = true;

        if (numOfPagesInDoc >= currentTonerLevel)
            insufficientTonerLevel = true;

        while (insufficientPaperLevel || insufficientTonerLevel) {

            if (insufficientPaperLevel && insufficientTonerLevel) {
                System.out.println("Insufficient paper and toner levels. - " +
                        "Paper count - " + currentPaperLevel +
                        "Toner count - " + currentTonerLevel
                );
            } else if (insufficientTonerLevel) {
                System.out.println("Insufficient toner level." +
                        "Toner level - " + currentTonerLevel);
            } else {
                System.out.println("Insufficient paper count. - " +
                        "Paper count - " + currentPaperLevel);
            }
            try{
                wait();
                if (numOfPagesInDoc > currentPaperLevel) {
                    insufficientPaperLevel = true;
                } else {
                    insufficientPaperLevel = false;
                }
                if (numOfPagesInDoc > currentTonerLevel) {
                    insufficientTonerLevel = true;
                } else {
                    insufficientTonerLevel = false;
                }
            } catch (InterruptedException e){ e.printStackTrace();}

        }

        if(numOfPagesInDoc < currentPaperLevel && numOfPagesInDoc < currentTonerLevel){
            System.out.println("Printing " + documentName + "\n" +
                    documentName+ " length is " + numOfPagesInDoc + " sheets.");
            currentTonerLevel -= numOfPagesInDoc;
            currentPaperLevel -= numOfPagesInDoc;
            System.out.println("         ");
            System.out.println(toString());
            System.out.println("         ");

            numOfDocumentsPrinted++;

            System.out.println("=> Printed "+documentName+ " of "  +Thread.currentThread().getName() +" successfully" +  ".\n");
        }

        notifyAll();
    }


    @Override
    public synchronized void replaceTonerCartridge() {
        //boolean visited = false; Used previously for exiting the while
        while (currentTonerLevel >=Minimum_Toner_Level ) {
            //if(visited) return;
            if(areThereAnyStudentsLeft()) {
                return;
            }
            System.out.println("Checking the toner level... \n" +
                    "There is no need of replacing the toner - " +
                    "toner count - " + currentTonerLevel);
            try {
                wait(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //visited = true;
        }
        currentTonerLevel = Full_Toner_Level;
        System.out.println("Successfully replaced toner cartridge.");
        notifyAll();
    }


    private boolean areThereAnyStudentsLeft() {
        if (students.activeCount() == 0) { //getting the number of students from the thread group related to students.
            return true;
        } else {
            return false;
        }

    }

    @Override
    public synchronized void refillPaper() {

        //boolean visited = false;
        while ((currentPaperLevel + SheetsPerPack) > Full_Paper_Tray)  {
                //if(visited) return;

            if(areThereAnyStudentsLeft()) {
                return;
            }
            System.out.println("Waiting...papers to be added.");
            try {
                wait(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

                //visited = true;
        }


        currentPaperLevel += SheetsPerPack;
        System.out.println("Successfully added a paper sheets pack.");
        notifyAll();

    }


    public int getCurrentPaperLevel() {
        return currentPaperLevel;
    }

    public int getCurrentTonerLevel() {
        return currentTonerLevel;
    }


    @Override
    public String toString() {
        return"[Printer Id=" + printerName +
                ", Current Paper Level=" + currentPaperLevel +
                ", Current Toner Level=" + currentTonerLevel +
                ", Number of Documents Printed=" + numOfDocumentsPrinted +
                ']';
    }

}
