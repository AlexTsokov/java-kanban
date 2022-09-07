package kanban.manager;

import kanban.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> history = new ArrayList<>();
    private int historyListSize = 10;

    @Override
    public void add(Task task) {
        if (history.size() > historyListSize) {
            history.remove(0);
        }
        history.add(task);
    }

    public void setHistoryListSize(int historyListSize) {
        this.historyListSize = historyListSize;
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }
}
