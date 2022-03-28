import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class SimulationManager implements Runnable {
    public int timeLimit;
    public int maxProcessingTime;
    public int minProcessingTime;
    public int maxArrivalTime;
    public int minArrivalTime;
    public int numberOfServers;
    public int numberOfClients;
    public static volatile int currentTime = 0;
    public File fileResult = new File("fileResult.txt");
    public float totalServiceTime = 0;
    public int peakTime = 0, peakNbOfTasks = 0;
    private Scheduler scheduler;
    private final SimulationFrame frame;
    private List<Task> generatedTasks;
    List<Task> toBeRemoved;
    Thread thread;

    public SimulationManager() {

        frame = new SimulationFrame();
        frame.buttonStart.addActionListener(e -> {
            frame.jTextArea.setText("");
            timeLimit = Integer.parseInt(frame.textField1.getText());
            numberOfClients = Integer.parseInt(frame.textField2.getText());
            numberOfServers = Integer.parseInt(frame.textField3.getText());
            minArrivalTime = Integer.parseInt(frame.textField4.getText());
            maxArrivalTime = Integer.parseInt(frame.textField5.getText());
            minProcessingTime = Integer.parseInt(frame.textField6.getText());
            maxProcessingTime = Integer.parseInt(frame.textField7.getText());
            toBeRemoved = new ArrayList<>();
            generatedTasks = generateNRandomTasks();
            generatedTasks.sort(Comparator.comparingInt(Task::getArrivalTime));
            thread = new Thread(this);
            thread.start();
        });

    }

    private List<Task> generateNRandomTasks() {
        List<Task> genTasks = new ArrayList<>();
        for (int i = 0; i < numberOfClients; i++) {
            Task t = new Task((int) Math.floor(Math.random() * (maxArrivalTime - minArrivalTime + 1) + minArrivalTime), (int) Math.floor(Math.random() * (maxProcessingTime - minProcessingTime + 1) + minProcessingTime), i + 1);
            genTasks.add(t);
        }
        return genTasks;
    }

    private String getResult(int currentTime) {

        StringBuilder result = new StringBuilder("\nTime: " + currentTime + "\n");
        result.append("Waiting clients: ");
        for (Task i : generatedTasks) {
            result.append(i.toString());
        }
        result.append("\n").append(scheduler.toString());


        return result.toString();
    }

    @Override
    public void run() {
        scheduler = new Scheduler(numberOfServers);
        currentTime = 0;

        try {
            FileWriter file;
            file = new FileWriter(fileResult.toString());

            while (currentTime <= timeLimit) {
                for (Task task : generatedTasks
                ) {
                    if (task.getArrivalTime() == currentTime) {
                        scheduler.dispatchTask(task);
                        totalServiceTime += task.getServiceTime();
                        toBeRemoved.add(task);
                    }

                }
                generatedTasks.removeAll(toBeRemoved);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException interruptedException) {
                    System.out.println(interruptedException.getMessage());
                }
                String result = getResult(currentTime);
                System.out.println(result);
                frame.print(result);
                frame.displayCurrentTime(currentTime);
                file.write(result);
                computePeakTime();
                currentTime++;
            }
            scheduler.closeServers();
            file.write("Average service time: " + totalServiceTime / numberOfClients + '\n');
            frame.print("Average service time: " + totalServiceTime / numberOfClients + '\n');
            file.write("Peak time: " + peakTime + '\n');
            frame.print("Peak time: " + peakTime + '\n');

            try {
                file.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void computePeakTime() {
        int totalTasks = 0;
        for (Server server : scheduler.getServers()) {
            totalTasks += server.getTasks().size();
            if (server.processed)
                totalTasks++;
        }
        if (totalTasks > peakNbOfTasks) {
            peakNbOfTasks = totalTasks;
            peakTime = currentTime;
        }

    }

}



