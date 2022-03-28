import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private final BlockingQueue<Task> tasks;
    private final AtomicInteger waitingPeriod;

    static {
        new AtomicInteger();
    }

    private final AtomicInteger waitingTasks = new AtomicInteger(0);
    public int currentId = 0, currentArrivalTime = 0, currentServiceTime = 0, finishTime = 0, timeOfArrival = 0;
    private final int id;
    private boolean closed = false;
    public boolean processed = false;

    public Server(int id, AtomicInteger waitingPeriod) {
        this.id = id;
        this.waitingPeriod = waitingPeriod;
        this.tasks = new ArrayBlockingQueue<>(1000);

    }

    public BlockingQueue<Task> getTasks() {
        return tasks;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }


    public int getId() {
        return id;
    }

    public AtomicInteger getWaitingTasks() {
        return waitingTasks;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    void addTask(Task newTask) {
        try {
            tasks.put(newTask);
            waitingPeriod.getAndAdd(newTask.getServiceTime());
        } catch (InterruptedException interruptedException) {
            System.out.println("Interrupted Exception caught!");
        }
    }

    public String toString() {
        String result;
        if (tasks.size() == 0 && !processed) {
            result = "closed";
        } else {
            result = tasks.toString();
        }
        return result;
    }

    @Override
    public void run() {

        while (!closed) {
            try {
                Task task = tasks.take();
                processed = true;
                currentArrivalTime = task.getArrivalTime();
                currentServiceTime = task.getServiceTime();
                currentId = task.getId();
                timeOfArrival = SimulationManager.currentTime;
                finishTime = SimulationManager.currentTime + task.getServiceTime();
                Thread.sleep(currentServiceTime * 1000L);
                while (SimulationManager.currentTime < finishTime)
                    Thread.sleep(100);
                waitingPeriod.getAndAdd((-1) * task.getServiceTime());
                processed = false;
                finishTime = 0;
                timeOfArrival = 0;

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        setClosed(true);

    }
}