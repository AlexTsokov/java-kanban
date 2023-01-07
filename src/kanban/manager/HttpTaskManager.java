package kanban.manager;

import com.google.gson.Gson;
import kanban.Http.KVTaskClient;
import kanban.tasks.Task;
import kanban.tasks.TaskStatus;

import java.io.File;
import java.util.Collections;

public class HttpTaskManager extends FileBackedTasksManager {

    private KVTaskClient kvTaskClient = new KVTaskClient();
    private Gson gson = Managers.getGson();

    public HttpTaskManager() {
        super(null);
    }

    @Override
    protected void save() {
        String jsonTask = gson.toJson(Collections.singletonList(new Task("Приготовить уху", "Приготовить суп из пойманной рыбы", TaskStatus.NEW)));
        kvTaskClient.save("tasks", jsonTask);
        //для эпиков, сабтасков, хистори
    }
}

//метод на загрущку