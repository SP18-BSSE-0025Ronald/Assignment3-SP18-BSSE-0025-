
package diningphilosopher.s5;




import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;


public class DiningPhilosopherS5 {

  static int philosopher = 5;
    static philosopher philosophers[] = new philosopher[philosopher];
    static Fork forks[] = new Fork[philosopher];

    static class Fork
    {

        public Semaphore mutex = new Semaphore(1);

        void grab()
        {
            try
            {
                mutex.acquire();
            }
            catch (Exception e)
            {
                e.printStackTrace(System.out);
            }
        }

        void release()
        {
            mutex.release();
        }

        boolean isFree()
        {
            return mutex.availablePermits() > 0;
        }

    }

    static class philosopher extends Thread
    {

        public int number;
        public Fork leftchopstick;
        public Fork rightchopstick;

        philosopher(int num, Fork left, Fork right)
        {
            number = num;
            leftchopstick = left;
            rightchopstick = right;
        }

        public void run()
        {
           while (true) 
            {
                leftchopstick.grab();
                System.out.println("philosopher " + (number+1) + " grabs left chopstick.");
                rightchopstick.grab();
                System.out.println("philosopher " + (number+1) + " grabs right chopstick.");
                eat();
                leftchopstick.release();
                System.out.println("philosopher " + (number+1) + " releases left chopstick.");
                rightchopstick.release();
                System.out.println("philosopher " + (number+1) + " releases right chopstick.");
            }
        }
        void eat() 
        {
            try 
            {
               System.out.println("Philosopher " + (number+1) + " Eats " );
            
            }
            catch (Exception e)
            {
                e.printStackTrace(System.out);
            }
        }

    }

    public static void main(String argv[]) {

        for (int i = 0; i < philosopher; i++) {
            forks[i] = new Fork();
        }

        for (int i = 0; i < philosopher; i++) {
            philosophers[i] = new philosopher(i, forks[i], forks[(i + 1) % philosopher]);
            philosophers[i].start();
        }

        while (true) {
            try {
                // sleep 1 sec
                Thread.sleep(1000);

                // check for deadlock
                boolean deadlock = true;
                for (Fork f : forks) {
                    if (f.isFree()) {
                        deadlock = false;
                        break;
                    }
                }
                if (deadlock) {
                    Thread.sleep(1000);
                    System.out.println("All Philosopher's Eat");
                    break;
                }
            }
            catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }

        System.out.println("Program Ended!");
        System.exit(0);
    }

}

    
    



 

