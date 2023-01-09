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

public class HttpTaskManager extends FileBackedTasksManager {

    private KVTaskClient kvTaskClient;
    private Gson gson = Managers.getGson();
    private URI uri;

    public HttpTaskManager(URI serverUri) {
        super(serverUri);
        this.uri = serverUri;
        kvTaskClient = new KVTaskClient(serverUri);
    }

    @Override
    public void save() {
        kvTaskClient.put("tasks", gson.toJson(getTasks()));
        kvTaskClient.put("epics", gson.toJson(getEpics()));
        kvTaskClient.put("subtasks", gson.toJson(getSubTasks()));
        kvTaskClient.put("history", gson.toJson(getHistoryManager().getHistoryIds()));
    }

    public HttpTaskManager load() throws IOException, InterruptedException {
        final HttpTaskManager taskManager = new HttpTaskManager(uri);
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
        for (Task task : tasks) {
            taskManager.addNewTask(task);
        }
        for (Epic epic : epics) {
            taskManager.addNewEpic(epic);
        }
        for (Subtask subTask : subTasks) {
            taskManager.addNewSubtask(subTask);
        }
        taskManager.restoredHistory = history;
        return taskManager;
    }
}