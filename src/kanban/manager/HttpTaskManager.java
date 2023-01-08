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
import java.util.Map;

public class HttpTaskManager extends FileBackedTasksManager {

    private KVTaskClient kvTaskClient;
    private Gson gson = Managers.getGson();

    public HttpTaskManager(URI serverUri) {
        super(serverUri);
        kvTaskClient = new KVTaskClient(serverUri);
    }

    @Override
    public void save() {
        kvTaskClient.put("tasks", gson.toJson(getTasks()));
        kvTaskClient.put("epics", gson.toJson(getEpics()));
        kvTaskClient.put("subtasks", gson.toJson(getSubTasks()));
        kvTaskClient.put("history", gson.toJson(getHistoryManager().getHistory()));
    }

    public void load() throws IOException, InterruptedException {
        Map<Integer, Task> tasks = gson.fromJson(kvTaskClient.load("tasks"),
                new TypeToken<Map<Integer, Task>>() {
                }.getType());
        Map<Integer, Epic> epics = gson.fromJson(kvTaskClient.load("epics"),
                new TypeToken<Map<Integer, Epic>>() {
                }.getType());
        Map<Integer, Subtask> subTasks = gson.fromJson(kvTaskClient.load("subtasks"),
                new TypeToken<Map<Integer, Subtask>>() {
                }.getType());
        List<Task> history = gson.fromJson(kvTaskClient.load("history"),
                new TypeToken<List<Task>>() {
                }.getType());
        for (Map.Entry<Integer, Task> task : tasks.entrySet()) {
            addNewTask(task.getValue());
        }
        for (Map.Entry<Integer, Epic> epic : epics.entrySet()) {
            addNewEpic(epic.getValue());
        }
        for (Map.Entry<Integer, Subtask> subTask : subTasks.entrySet()) {
            addNewSubtask(subTask.getValue());
        }
        for (Task task : history) {
            int taskId = task.getId();
            if (tasks.containsKey(taskId)) {
                getTask(taskId);
            } else if (subTasks.containsKey(taskId)) {
                getSubTask(taskId);
            } else {
                getEpic(taskId);
            }
        }
    }
}