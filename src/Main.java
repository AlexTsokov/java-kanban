import kanban.manager.InMemoryTaskManager;
import kanban.manager.Managers;
import kanban.manager.TaskManager;
import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;
import kanban.tasks.TaskStatus;

public class Main {

    public static void main(String[] args) {

        TaskManager manager = new Managers().getDefault();

        Task task1 = new Task("Приготовить уху", "Приготовить суп из пойманной рыбы", TaskStatus.NEW);
        Epic epic1 = new Epic("Попить чай", "Приготовить чай и выпить его", TaskStatus.NEW);
        Epic epic2 = new Epic("Отдохнуть под музыку", "Надо расслабиться и послущать музыку", TaskStatus.NEW);

        manager.addNewTask(task1);
        manager.addNewEpic(epic1);
        manager.addNewEpic(epic2);

        Subtask subTask1 = new Subtask("Заварка", "Заварить заварку в чайничке", TaskStatus.DONE, epic1.getId());
        Subtask subTask2 = new Subtask("Сахар", "Положить сахар и размешать", TaskStatus.NEW, epic1.getId());
        Subtask subTask3 = new Subtask("Остудить", "Долить холодной воды", TaskStatus.NEW, epic1.getId());
        Subtask subTask4 = new Subtask("Включить музыку", "Насладится пением", TaskStatus.NEW, epic2.getId());
        Subtask subTask5 = new Subtask("Найти другую песню", "Эта надоела, хочется другую песню", TaskStatus.NEW, epic2.getId());

        manager.addNewSubtask(subTask1);
        manager.addNewSubtask(subTask2);
        manager.addNewSubtask(subTask3);
        manager.addNewSubtask(subTask4);
        manager.addNewSubtask(subTask5);

        System.out.println(manager.getTasks());
        System.out.println(manager.getEpics());
        System.out.println(manager.getSubTasks());
        System.out.println(manager.getCurrentEpicSubTasks(epic1.getId()));
        System.out.println(manager.getSubtaskIds(epic1.getId()));

        manager.getEpic(2);
        manager.getEpic(3);
        manager.getEpic(2);
        manager.getTask(1);
        manager.getSubTask(4);
        manager.getSubTask(5);
        manager.getTask(1);
        System.out.println(InMemoryTaskManager.historyManager.getHistory());

    }
}