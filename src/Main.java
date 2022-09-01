import kanban.manager.Manager;
import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;

public class Main {

    public static void main(String[] args) {

        Manager manager = new Manager();

        Task task1 = new Task("Приготовить уху", "Приготовить суп из пойманной рыбы", manager.taskStatus[0]);
        Task updatedTask1 = new Task("Борщ", "Рыбу не поймали, давай сделаем борщ", manager.taskStatus[0]);
        Epic epic1 = new Epic("Попить чай", "Приготовить чай и выпить его", manager.taskStatus[0]);
        Epic epic2 = new Epic("Отдохнуть под музыку", "Надо расслабиться и послущать музыку", manager.taskStatus[0]);

        manager.addNewTask(task1);
        manager.addNewEpic(epic1);

        Subtask subTask1 = new Subtask("Заварка", "Заварить заварку в чайничке", manager.taskStatus[0], epic1.getId());
        Subtask subTask2 = new Subtask("Сахар", "Положить сахар и размешать", manager.taskStatus[2], epic1.getId());
        Subtask subTask3 = new Subtask("Остудить", "Долить холодной воды", manager.taskStatus[0], epic1.getId());
        Subtask subTask4 = new Subtask("Включить музыку", "Насладится пением", manager.taskStatus[0], epic2.getId());
        Subtask subTask5 = new Subtask("Найти другую песню", "Эта надоела, хочется другую песню", manager.taskStatus[0], epic2.getId());

        manager.addNewSubtask(subTask1);
        manager.addNewSubtask(subTask2);
        manager.addNewSubtask(subTask3);
        manager.addNewSubtask(subTask4);
        manager.addNewSubtask(subTask5);


        System.out.println(manager.getTasks());
        System.out.println(manager.getEpics());
        System.out.println(manager.getSubTasks());
        System.out.println(manager.getCurrentEpicSubTasks(epic1.getId()));

    }
}