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
        HttpTaskManager manager = Managers.getDefaultHttpManager(url,false); // добавил параметр запроса необходимости восстановления
        KVTaskClient kvTaskClient = new KVTaskClient(url);

        String jsonTask = gson.toJson(Collections.singletonList(new Task("Уху", "Приготовить суп из пойманной рыбы", TaskStatus.NEW)));
        kvTaskClient.put("tasks", jsonTask);

        manager.addNewTask(new Task("Приготовить уху", "Приготовить суп из пойманной рыбы", TaskStatus.NEW, "2021-12-20T23:20:21", 60));
        manager.addNewEpic(new Epic("Попить чай", "Приготовить чай и выпить его", TaskStatus.NEW, "2021-12-21T23:20:00", 60));
        manager.addNewSubtask(new Subtask("Заварка", "Заварить заварку в чайничке", TaskStatus.NEW, "2021-12-21T23:20:21", 60, 2));


        // формируем историю просмотров
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

        // Проверка восстановления с сервера:
        System.out.println("\nДо удаления:");
        System.out.println(manager.getTasks());
        System.out.println(manager.getEpics());
        System.out.println(manager.getSubTasks());
        System.out.println(manager.getHistoryManager().getHistory());

        // Удаляем содержимое менеджера из памяти
        manager.deleteAnyTypeAllTasks();
        // После удаления пусто
        System.out.println("\nПосле удаления:");
        System.out.println(manager.getTasks().size());
        System.out.println(manager.getEpics().size());
        System.out.println(manager.getSubTasks().size());
        System.out.println(manager.getHistoryManager().getHistory());

        // Восстанавливаем
        manager.load();

        // Результат восстановления
        System.out.println("\nПосле восстановления:");
        System.out.println(manager.getTasks());
        System.out.println(manager.getEpics());
        System.out.println(manager.getSubTasks());
        System.out.println(manager.getHistoryManager().getHistory());
        System.out.println("Значение id, с которого пойдет отсчет идентификаторов: " + manager.getCurrentTaskId());
    }
}