package kanban.manager;
import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;
import java.util.ArrayList;
import java.util.HashMap;

public class Manager {

    private int taskId = 0;
    public String[] taskStatus = {"NEW", "IN PROGRESS", "DONE"};
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subTasks = new HashMap<>();

    public ArrayList<Task> getTasks() { // Получение списка всех задач.
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Task> getEpics() { // Получение списка всех эпиков.
        return new ArrayList<>(epics.values());
    }

    public ArrayList<Task> getSubTasks() { // Получение списка всех подзадач.
        return new ArrayList<>(subTasks.values());
    }

    public void removeTaskById(int id) { // удаление таска по id
        tasks.remove(id);
    }

    public void removeEpicById(int id) { // удаление эпика по id
        epics.remove(id);
    }

    public void removeSubtaskById(int id) { // удаление сабтаска по id
        subTasks.remove(id);
    }

    public void deleteAnyTypeAllTasks(HashMap tasks) { // удаление всех задач любого типа
        tasks.clear();
    }


    public Task getTask(int id) { // получение таска по id
        return tasks.get(id);
    }

    public Task getEpic(int id) { // получение эпика по id
        return epics.get(id);
    }

    public Task getSubTask(int id) { // получение сабтаска по id
        return subTasks.get(id);
    }

    public ArrayList<Subtask> getCurrentEpicSubTasks(int id) { // Получение подзадач конкретного эпика по его id
        ArrayList<Subtask> currentEpicSubtasks = new ArrayList<>();
        for (Subtask subtask : subTasks.values()) {
            if (id == subtask.getEpicId()) {
                currentEpicSubtasks.add(subtask);
            }
        }
        return currentEpicSubtasks;
    }

    public void epicStatusCheckAndChange(int id) { // Обновление статуса Эпика. Не могу додумать реализацию, прошу подсказки.
        // Не корректно работает если у всех сабтасков статус NEW
        ArrayList<Subtask> subtasks = getCurrentEpicSubTasks(id);
        for (Subtask subtask : subtasks) {
            if (!subtask.getStatus().equals(taskStatus[2])) {
                epics.get(id).setStatus(taskStatus[1]);
            } else {
                epics.get(id).setStatus(taskStatus[2]);
            }
        }
    }

    public void updateTask(Task task) { // Обновление таска.
        final int id = task.getId();
        final Task savedTask = tasks.get(id);
        if (savedTask == null) {
            return;
        }
        tasks.put(id, task);
    }

    public void updateEpic(Epic epic) { // Обновление эпика.
        final int id = epic.getId();
        final Epic savedEpic = epics.get(id);
        if (savedEpic == null) {
            return;
        }
        epics.put(id, epic);
    }

    public void updateSubTask(Subtask subTask) { // Обновление сабтаска.
        final int id = subTask.getId();
        final Subtask savedTask = subTasks.get(id);
        if (savedTask == null) {
            return;
        }
        tasks.put(id, subTask);
    }

    public int addNewTask(Task task) {
        final int id = ++taskId;
        task.setId(id);
        tasks.put(id, task);
        return id;
    }

    public int addNewEpic(Epic epic) {
        final int id = ++taskId;
        epic.setId(id);
        epics.put(id, epic);
        return id;
    }

    public int addNewSubtask(Subtask subtask) {
        final int id = ++taskId;
        subtask.setId(id);
        subTasks.put(id, subtask);
        return id;
    }

}
