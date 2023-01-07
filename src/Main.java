import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kanban.Http.HttpTaskServer;
import kanban.Http.KVTaskClient;
import kanban.manager.FileBackedTasksManager;
import kanban.manager.InMemoryTaskManager;
import kanban.manager.Managers;
import kanban.manager.TaskManager;
import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;
import kanban.tasks.TaskStatus;

import java.io.File;
import java.util.Collections;

public class Main {

    public static void main(String[] args) {

        KVTaskClient kvTaskClient = new KVTaskClient();

        Gson gson = Managers.getGson();
        String jsonTask = gson.toJson(Collections.singletonList(new Task("Приготовить уху", "Приготовить суп из пойманной рыбы", TaskStatus.NEW)));

        kvTaskClient.save("tasks", jsonTask);

        System.out.println(kvTaskClient);

//        TaskManager manager = Managers.getDefaultFile();
//
//        manager.addNewTask(new Task("Приготовить уху", "Приготовить суп из пойманной рыбы", TaskStatus.NEW,"2021-12-20T23:20:21",60));
//        manager.addNewEpic(new Epic("Попить чай", "Приготовить чай и выпить его", TaskStatus.NEW));
//        manager.addNewSubtask(new Subtask("Заварка", "Заварить заварку в чайничке", TaskStatus.DONE, "2021-12-21T23:20:21", 60, 2));
//        manager.addNewSubtask(new Subtask("Сахар", "Положить сахар и размешать", TaskStatus.NEW, "2021-12-22T23:20:25", 60, 2));
//        manager.addNewSubtask(new Subtask("Лимон", "Положить дольку лимона", TaskStatus.NEW, "2021-12-23T23:21:20", 60, 2));
//
//        manager.getTask(1);
//        manager.getTask(1);
//        manager.getTask(1);
//        manager.getTask(1);
//        manager.getTask(1);
//        manager.getEpic(2);
//        manager.getTask(1);
//        manager.getSubTask(3);
//        manager.getSubTask(4);
//        manager.getSubTask(3);
//        System.out.println(manager.getHistoryManager().getHistory());

//        FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile
//                (new File("src/resources/tasks.csv"));
//
//        System.out.println(fileBackedTasksManager.getTasks());
//        System.out.println(fileBackedTasksManager.getEpics());
//        System.out.println(fileBackedTasksManager.getSubTasks());
//        System.out.println(fileBackedTasksManager.restoredHistory);
    }
}