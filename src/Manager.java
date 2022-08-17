import java.util.HashMap;

public class Manager {

    private int taskId = 0;
    private String[] taskStatus = {"NEW", "IN PROGRESS", "DONE"};
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subTasks = new HashMap<>();

    Task task1 = new Task("Пропылесосить квартиру", "Время уборки! Нужно собрать всю пыль", taskStatus[0]);
    Epic epic1 = new Epic("Попить чай", "Приготовить чай и выпить его", taskStatus[0]);
    Epic epic2 = new Epic("Отдохнуть под музыку", "Надо расслабиться и послущать музыку", taskStatus[0]);
    Subtask subTask1 = new Subtask(epic1, "Заварка", "Заварить заварку в чайничке", taskStatus[0]);
    Subtask subTask2 = new Subtask(epic1, "Сахар", "Положить сахар и размешать", taskStatus[2]);
    Subtask subTask5 = new Subtask(epic1, "Остудить", "Долить холодной воды", taskStatus[0]);
    Subtask subTask3 = new Subtask(epic2, "Включить музыку", "Насладится пением", taskStatus[2]);
    Subtask subTask4 = new Subtask(epic2, "Найти другую песню", "Эта надоела, хочется другую песню", taskStatus[2]);

    void createEpicSubtasksList(Epic epic, Subtask subTask) {
        epic.epicSubTasks.add(subTask);
    }

    void createTasksList(Task task) {
        tasks.put(changeTaskId(), task);
    }

    void createEpicsList(Epic epic) {
        epics.put(changeTaskId(), epic);
    }

    void createSubTaskList(Subtask subTask) {
        subTasks.put(changeTaskId(), subTask);
    }

    void updateTask(Task task, int iD) {
        tasks.put(iD, task);
    }

    void updateEpic(Epic epic, int iD) {
        epics.put(iD, epic);
    }

    void updateSubTask(Subtask subTask, int iD) {
        subTasks.put(iD, subTask);
    }

    void deleteById(HashMap tasks, int iD) {
        tasks.remove(iD);
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public HashMap<Integer, Subtask> getSubTasks() {
        return subTasks;
    }

    HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    void deleteTasks(HashMap tasks) {
        tasks.clear();
    }


    Integer changeTaskId() {
        taskId += 1;
        return taskId;
    }

    public String getByTaskId(HashMap hashMap, int iD) {
        return hashMap.get(iD).toString();
    }

    public String getEpicSubTasksNames(Epic epic) {
        return "Список подзадач для задачи " + epic + ": " + epic.epicSubTasks;
    }


    void epicStatusChange(Epic epic) {
        for (Subtask value : epic.epicSubTasks) {
            if (!value.taskStatus.equals(taskStatus[2])) {
                epic.taskStatus = taskStatus[1];
            } else {
                epic.taskStatus = taskStatus[2];
            }
        }
        System.out.println(epic.taskStatus + epic.epicSubTasks);
    }
}
