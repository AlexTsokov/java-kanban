import kanban.manager.FileBackedTasksManager;
import kanban.manager.TaskManager;
import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;
import kanban.tasks.TaskStatus;

import java.io.File;

public class Main {

    public static void main(String[] args) {

        TaskManager manager = FileBackedTasksManager.getDefaultFile();

        manager.addNewTask(new Task("Приготовить уху", "Приготовить суп из пойманной рыбы", TaskStatus.NEW));
        manager.addNewEpic(new Epic("Попить чай", "Приготовить чай и выпить его", TaskStatus.NEW));
        manager.addNewSubtask(new Subtask("Заварка", "Заварить заварку в чайничке", TaskStatus.DONE, 2));
        manager.addNewSubtask(new Subtask("Сахар", "Положить сахар и размешать", TaskStatus.NEW, 2));
        manager.addNewSubtask(new Subtask("Лимон", "Положить дольку лимона", TaskStatus.NEW, 2));

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
        System.out.println(manager.getHistoryManager().getHistory());

        FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile
                (new File("src/resources/tasks.csv"));

        System.out.println(fileBackedTasksManager.getTasks());
        System.out.println(fileBackedTasksManager.getEpics());
        System.out.println(fileBackedTasksManager.getSubTasks());
        System.out.println(fileBackedTasksManager.restoredHistory);
    }
}