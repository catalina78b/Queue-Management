import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Scheduler {

    private final List<Server> servers;
    private Strategy strategy;


    public Scheduler(int maxNoServers) {
        servers = new ArrayList<>(maxNoServers);
        for (int i = 0; i < maxNoServers; i++) {
            Server server = new Server(i, new AtomicInteger(0));
            server.setClosed(false);
            servers.add(server);
            Thread thread = new Thread(server);
            thread.start();
        }
        changeStrategy(SelectionPolicy.SHORTEST_TIME);
    }

    public List<Server> getServers() {
        return servers;
    }

    public void closeServers() {
        for (Server s : servers) {
            s.setClosed(true);
        }
    }

    public void changeStrategy(SelectionPolicy selectionPolicy) {
        if (selectionPolicy == SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new ConcreteStrategyQueue();
        }
        if (selectionPolicy == SelectionPolicy.SHORTEST_TIME) {
            strategy = new ConcreteStrategyTime();
        }
    }

    public void dispatchTask(Task t) {
        changeStrategy(SelectionPolicy.SHORTEST_TIME);
        strategy.addTask(servers, t);
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Server server : servers) {
            result.append("Queue ").append(server.getId()).append(": ").append(server).append("\n");
            if (server.processed)
                result.append("The first client in queue: ").append("(").append(server.currentId).append(",").append(server.currentArrivalTime).append(",").append(server.currentServiceTime - (SimulationManager.currentTime - server.timeOfArrival)).append(")");
            result.append("\n");
        }
        return result.toString();
    }
}
