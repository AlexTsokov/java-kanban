package kanban.manager;

import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;
import kanban.tasks.TaskStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {

    private int taskId = 0;
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subTasks = new HashMap<>();
    public static HistoryManager historyManager = new Managers().getDefaultHistory();

    @Override
    public Task getTask(int id) { // получение таска по id
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Task getEpic(int id) { // получение эпика по id
        historyManager.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public Task getSubTask(int id) { // получение сабтаска по id
        historyManager.add(subTasks.get(id));
        return subTasks.get(id);
    }

    @Override
    public ArrayList<Task> getTasks() { // Получение списка всех задач.
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Task> getEpics() { // Получение списка всех эпиков.
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<Task> getSubTasks() { // Получение списка всех подзадач.
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public void removeTaskById(int id) { // удаление таска по id
        tasks.remove(id);
    }

    @Override
    public void removeEpicById(int id) { // удаление эпика по id
        epics.remove(id);
    }

    @Override
    public void removeSubtaskById(int id) { // удаление сабтаска по id
        int epicId = subTasks.get(id).getEpicId();
        epics.get(epicId).removeSubtaskIds(id);
        subTasks.remove(id);
        updateEpicStatus(epicId);
    }

    @Override
    public void deleteAnyTypeAllTasks() { // удаление всех задач любого типа
        tasks.clear();
        epics.clear();
        subTasks.clear();
    }

    @Override
    public ArrayList<Subtask> getCurrentEpicSubTasks(int id) { // Получение подзадач конкретного эпика по его id
        ArrayList<Subtask> currentEpicSubtasks = new ArrayList<>();
        for (Subtask subtask : subTasks.values()) {
            if (id == subtask.getEpicId()) {
                currentEpicSubtasks.add(subtask);
            }
        }
        return currentEpicSubtasks;
    }

    @Override
    public void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        List<Integer> subs = epic.getSubtaskIds();
        if (subs.isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
            return;
        }
        TaskStatus status = null;
        for (int id : subs) {
            final Subtask subtask = subTasks.get(id);
            if (status == null) {
                status = (TaskStatus) subtask.getStatus();
                continue;
            }
            if (status.equals(subtask.getStatus())
                    && status != TaskStatus.IN_PROGRESS) {
                continue;
            }
            epic.setStatus(TaskStatus.IN_PROGRESS);
            return;
        }
        epic.setStatus(status);
    }

    @Override
    public void updateTask(Task task) { // Обновление таска.
        final int id = task.getId();
        final Task savedTask = tasks.get(id);
        if (savedTask == null) {
            return;
        }
        tasks.put(id, task);
    }

    @Override
    public void updateEpic(Epic epic) { // Обновление эпика.
        final int id = epic.getId();
        final Epic savedEpic = epics.get(id);
        if (savedEpic == null) {
            return;
        }
        epics.put(id, epic);
    }

    @Override
    public void updateSubTask(Subtask subTask) { // Обновление сабтаска.
        final int id = subTask.getId();
        final Subtask savedTask = subTasks.get(id);
        if (savedTask == null) {
            return;
        }
        subTasks.put(id, subTask);
        updateEpicStatus(subTask.getEpicId());
    }

    @Override
    public int addNewTask(Task task) {
        final int id = ++taskId;
        task.setId(id);
        tasks.put(id, task);
        return id;
    }

    @Override
    public int addNewEpic(Epic epic) {
        final int id = ++taskId;
        epic.setId(id);
        epics.put(id, epic);
        return id;
    }

    @Override
    public int addNewSubtask(Subtask subtask) {
        final int id = ++taskId;
        subtask.setId(id);
        subTasks.put(id, subtask);
        epics.get(subtask.getEpicId()).addSubtaskIds(id);
        updateEpicStatus(subtask.getEpicId());
        return id;
    }

    @Override
    public ArrayList<Integer> getSubtaskIds(int id) { // ввод списка id всех сабтасков эпика
        return epics.get(id).getSubtaskIds();
    }

}