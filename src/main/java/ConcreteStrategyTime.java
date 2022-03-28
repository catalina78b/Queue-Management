import java.util.List;

public class ConcreteStrategyTime implements Strategy {

    int id = -1;
    int min = 999;

    @Override
    public void addTask(List<Server> serverList, Task task) {
        for (Server server : serverList) {
            if (server.getWaitingPeriod().intValue() < min) {

                min = server.getWaitingPeriod().intValue();

                id = server.getId();
            }
        }
        System.out.println("Q: " + id + " TIME " + min);
        for (Server server : serverList) {
            if (server.getId() == id) {
                server.setClosed(false);
                server.addTask(task);
            }
        }
    }
}
