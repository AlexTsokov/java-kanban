package kanban.manager;
import kanban.tasks.Task;

import java.util.List;

public interface HistoryManager {

    void add(Task task);

    List<Task> getHistory();

    void clear(); // удаление всей истории

    void remove(int id); // удаление задачи из просмотра

}
