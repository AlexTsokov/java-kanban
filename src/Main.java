import com.google.gson.Gson;
import kanban.Http.KVServer;
import kanban.Http.KVTaskClient;
import kanban.manager.Managers;
import kanban.manager.TaskManager;
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
        TaskManager manager = Managers.getDefaultHttpManager();
        KVTaskClient kvTaskClient = new KVTaskClient(url);

        String jsonTask = gson.toJson(Collections.singletonList(new Task("Уху", "Приготовить суп из пойманной рыбы", TaskStatus.NEW)));
        kvTaskClient.put("tasks", jsonTask);
        System.out.println(kvTaskClient);

        manager.addNewTask(new Task("Приготовить уху", "Приготовить суп из пойманной рыбы", TaskStatus.NEW, "2021-12-20T23:20:21", 60));
        manager.addNewEpic(new Epic("Попить чай", "Приготовить чай и выпить его", TaskStatus.NEW));
        manager.addNewSubtask(new Subtask("Заварка", "Заварить заварку в чайничке", TaskStatus.DONE, "2021-12-21T23:20:21", 60, 2));
        manager.addNewSubtask(new Subtask("Сахар", "Положить сахар и размешать", TaskStatus.NEW, "2021-12-22T23:20:25", 60, 2));
        manager.addNewSubtask(new Subtask("Лимон", "Положить дольку лимона", TaskStatus.NEW, "2021-12-23T23:21:20", 60, 2));

        manager.getTask(1);
        manager.getTask(1);
        manager.getTask(1);
        manager.getTask(1);
        manager.getTask(1);
        manager.getEpic(2);
        manager.getTask(1);
        manager.getSubTask(3);
        manager.getSubTask(4);
        manager.getSubTask(3);

        // Проверка загрузки с сервера:

        System.out.println(kvTaskClient.load("tasks"));
        System.out.println(kvTaskClient.load("epics"));
        System.out.println(kvTaskClient.load("subtasks"));
        System.out.println(kvTaskClient.load("history"));
    }
}