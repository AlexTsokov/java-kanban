import kanban.manager.FileBackedTasksManager;
import kanban.manager.Managers;
import kanban.manager.TaskManager;
import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;
import kanban.tasks.TaskStatus;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

       TaskManager manager = Managers.getDefaultFile();

        manager.addNewTask(new Task("Приготовить уху", "Приготовить суп из пойманной рыбы", TaskStatus.NEW));
        manager.addNewEpic(new Epic("Попить чай", "Приготовить чай и выпить его", TaskStatus.NEW));
        manager.addNewSubtask(new Subtask("Заварка", "Заварить заварку в чайничке", TaskStatus.DONE, 2));
        manager.addNewSubtask(new Subtask("Сахар", "Положить сахар и размешать", TaskStatus.NEW, 2));

        manager.getTask(1);
        manager.getTask(1);
        manager.getTask(1);
        manager.getTask(1);
        manager.getTask(1);
        manager.getEpic(2);
        manager.getTask(1);
        manager.getSubTask(3);
        manager.getSubTask(4);

        /* FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile
                (new File("C:/Users/Alex/dev/java-kanban/src/resources/tasks.csv"));
        */
        // Закомменчено восстановление истории
    }
}