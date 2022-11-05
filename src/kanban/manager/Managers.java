package kanban.manager;

import java.io.File;

public class Managers extends InMemoryHistoryManager {


    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getDefaultFile() {
        return new FileBackedTasksManager(new File("C:/Users/Alex/dev/java-kanban/src/resources/tasks.csv"));
    }

}
