import com.google.gson.Gson;
import kanban.Http.KVServer;
import kanban.Http.KVTaskClient;
import kanban.manager.HttpTaskManager;
import kanban.manager.Managers;
import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;
import kanban.tasks.TaskStatus;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        Gson gson = Managers.getGson();
        KVServer kvServer = new KVServer();
        kvServer.start();
        URI url = URI.create("http://localhost:8078/");
        HttpTaskManager manager = Managers.getDefaultHttpManager();
        KVTaskClient kvTaskClient = new KVTaskClient(url);

        String jsonTask = gson.toJson(Collections.singletonList(new Task("Уху", "Приготовить суп из пойманной рыбы", TaskStatus.NEW)));
        kvTaskClient.put("tasks", jsonTask);

        manager.addNewTask(new Task("Приготовить уху", "Приготовить суп из пойманной рыбы", TaskStatus.NEW, "2021-12-20T23:20:21", 60));
        manager.addNewEpic(new Epic("Попить чай", "Приготовить чай и выпить его", TaskStatus.NEW, "2021-12-21T23:20:00", 60));
        manager.addNewSubtask(new Subtask("Заварка", "Заварить заварку в чайничке", TaskStatus.NEW, "2021-12-21T23:20:21", 60, 2));

        manager.getTask(1);
        manager.getTask(1);
        manager.getTask(1);
        manager.getTask(1);
        manager.getTask(1);
        manager.getEpic(2);
        manager.getTask(1);
        manager.getSubTask(3);

        // Проверка загрузки с сервера:

        System.out.println(kvTaskClient.load("tasks"));
        System.out.println(kvTaskClient.load("epics"));
        System.out.println(kvTaskClient.load("subtasks"));
        System.out.println(kvTaskClient.load("history"));

        // Проверка восстановления состояния с сервера в новый менеджер:

        HttpTaskManager manager2 = manager.load();
        System.out.println(manager2.getTasks());
        System.out.println(manager2.getEpics());
        System.out.println(manager2.getSubTasks());
        System.out.println(manager2.restoredHistory);
    }
}