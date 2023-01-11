package kanban.manager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import kanban.Http.KVTaskClient;
import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

public class HttpTaskManager extends FileBackedTasksManager {

    private KVTaskClient kvTaskClient;
    private Gson gson = Managers.getGson();

    public HttpTaskManager(URI uri, boolean isLoad) throws IOException, InterruptedException {
        super();
        kvTaskClient = new KVTaskClient(uri);
        if (isLoad) {
            load();
        }
    }

    @Override
    public void save() {
        kvTaskClient.put("tasks", gson.toJson(getTasks()));
        kvTaskClient.put("epics", gson.toJson(getEpics()));
        kvTaskClient.put("subtasks", gson.toJson(getSubTasks()));
        kvTaskClient.put("history", gson.toJson(getHistoryManager().getHistory().stream().map(Task::getId).collect(Collectors.toList())));
    }

    public void load() throws IOException, InterruptedException {
        List<Task> tasks = gson.fromJson(kvTaskClient.load("tasks"),
                new TypeToken<List<Task>>() {
                }.getType());
        List<Epic> epics = gson.fromJson(kvTaskClient.load("epics"),
                new TypeToken<List<Epic>>() {
                }.getType());
        List<Subtask> subTasks = gson.fromJson(kvTaskClient.load("subtasks"),
                new TypeToken<List<Subtask>>() {
                }.getType());
        List<Integer> history = gson.fromJson(kvTaskClient.load("history"),
                new TypeToken<List<Integer>>() {
                }.getType());
        if (tasks != null) {
            for (Task task : tasks) {
                this.tasks.put(task.getId(), task);
                findAndSetMaxId(task.getId());
            }
        }
        if (epics != null) {
            for (Epic epic : epics) {
                this.epics.put(epic.getId(), epic);
                findAndSetMaxId(epic.getId());
            }
        }
        if (subTasks != null) {
            for (Subtask subTask : subTasks) {
                this.subTasks.put(subTask.getId(), subTask);
                findAndSetMaxId(subTask.getId());
            }
        }
        if (history != null) {
            for (Integer taskId : history) {
                if (this.tasks.containsKey(taskId)) {
                    historyManager.add(this.tasks.get(taskId));
                } else if (this.epics.containsKey(taskId)) {
                    historyManager.add(this.epics.get(taskId));
                } else if (this.subTasks.containsKey(taskId)) {
                    historyManager.add(this.subTasks.get(taskId));
                }
            }
        }
    }
}