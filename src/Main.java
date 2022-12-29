import kanban.manager.FileBackedTasksManager;
import kanban.manager.InMemoryTaskManager;
import kanban.manager.Managers;
import kanban.manager.TaskManager;
import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;
import kanban.tasks.TaskStatus;

import java.io.File;

public class Main {

    public static void main(String[] args) {

//        TaskManager manager = FileBackedTasksManager.getDefaultFile();
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
//
//        FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile
//                (new File("src/resources/tasks.csv"));
//
//        System.out.println(fileBackedTasksManager.getTasks());
//        System.out.println(fileBackedTasksManager.getEpics());
//        System.out.println(fileBackedTasksManager.getSubTasks());
//        System.out.println(fileBackedTasksManager.restoredHistory);
        TaskManager manager = Managers.getDefault();
        Task task1 = new Task("25", "Приготовить суп из пойманной рыбы", TaskStatus.NEW, "2022-12-21T23:20:22", 60);
        Task task2 = new Task("24", "Приготовить суп из пойманной рыбы", TaskStatus.NEW, "2022-12-21T23:20:23", 10);
        Task task3 = new Task("21", "Приготовить суп из пойманной рыбы", TaskStatus.NEW, "2022-12-21T21:22:21", 20);
        manager.addNewTask(task1);
        manager.addNewTask(task2);
        manager.addNewTask(task3);
        Epic epic1 = new Epic("Попить чай", "Приготовить чай и выпить его", TaskStatus.NEW);
        manager.addNewEpic(epic1);
        manager.addNewSubtask(new Subtask("Заварка", "Заварить заварку в чайничке", TaskStatus.DONE, "2022-11-21T20:20:00", 15, epic1.getId()));
        manager.addNewSubtask(new Subtask("Сахар", "Положить сахар и размешать", TaskStatus.NEW, "2022-11-21T21:15:00", 10, epic1.getId()));
        manager.addNewSubtask(new Subtask("Лимон", "Положить лимон", TaskStatus.NEW, "2022-11-21T21:25:00", 25, epic1.getId()));
        System.out.println(epic1.getStartTime());
        System.out.println(epic1.getEpicEndTime());
        System.out.println(epic1.getEpicDuration(manager.getCurrentEpicSubTasks(epic1.getId())));
        manager.removeSubtaskById(7);
        System.out.println(epic1.getStartTime());
        System.out.println(epic1.getEpicEndTime());
        System.out.println(epic1.getEpicDuration(manager.getCurrentEpicSubTasks(epic1.getId())));
    }
}