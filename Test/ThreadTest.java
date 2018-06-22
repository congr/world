public class ThreadTest {
    static class RunnableDemo implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getId());
        }
    }

    public static void main(String[] args)  {
        new RunnableDemo().run();

        for (int i = 0; i < 5; i++) {
            Thread demo = new Thread(new RunnableDemo());
            demo.start();
        }
    }
}
