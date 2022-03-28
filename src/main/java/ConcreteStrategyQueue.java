import java.util.List;

public class ConcreteStrategyQueue implements Strategy {
    int id = 0;
    int min = 999;

    @Override
    public void addTask(List<Server> serverList, Task task) {
        for (Server server : serverList) {
            if (server.getWaitingTasks().intValue() < min) {
                min = server.getWaitingTasks().intValue();
                id = server.getId();
            }
        }
        for (Server server : serverList) {
            if (server.getId() == id) {
                server.setClosed(false);
                server.addTask(task);
            }
        }
    }
}
