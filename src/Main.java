import kanban.manager.Manager;
import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;

public class Main {

    public static void main(String[] args) {

        Manager manager = new Manager();

        Task task1 = new Task(manager.setId(), "Приготовить уху", "Приготовить суп из пойманной рыбы", manager.taskStatus[0]);
        Task updatedTask1 = new Task(task1.getiD(), "Борщ", "Рыбу не поймали, давай сделаем борщ", manager.taskStatus[0]);
        Epic epic1 = new Epic(manager.setId(), "Попить чай", "Приготовить чай и выпить его", manager.taskStatus[0]);
        Epic epic2 = new Epic(manager.setId(), "Отдохнуть под музыку", "Надо расслабиться и послущать музыку", manager.taskStatus[0]);
        Subtask subTask1 = new Subtask(manager.setId(), "Заварка", "Заварить заварку в чайничке", manager.taskStatus[0], epic1.getiD());
        Subtask subTask2 = new Subtask(manager.setId(), "Сахар", "Положить сахар и размешать", manager.taskStatus[2], epic1.getiD());
        Subtask subTask3 = new Subtask(manager.setId(), "Остудить", "Долить холодной воды", manager.taskStatus[0], epic1.getiD());
        Subtask subTask4 = new Subtask(manager.setId(), "Включить музыку", "Насладится пением", manager.taskStatus[0], epic2.getiD());
        Subtask subTask5 = new Subtask(manager.setId(), "Найти другую песню", "Эта надоела, хочется другую песню", manager.taskStatus[0], epic2.getiD());

        manager.createTasksList(task1);
        manager.createEpicsList(epic1);
        manager.createEpicsList(epic2);
        manager.createSubTaskList(subTask1);
        manager.createSubTaskList(subTask2);
        manager.createSubTaskList(subTask3);
        manager.createSubTaskList(subTask4);
        manager.createSubTaskList(subTask5);
        manager.getEpicsList();
        System.out.println(manager.getTasks());
        System.out.println(manager.getEpics());
        System.out.println(manager.getSubTasks());

        System.out.println(manager.getCurrentEpicSubTasks(3));
        manager.epicStatusCheckAndChange(3);
        System.out.println(epic2);
        manager.getTaskById(1);
        manager.updateTask(updatedTask1);
        manager.getTaskById(1);

    }
}