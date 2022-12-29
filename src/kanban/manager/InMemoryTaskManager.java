package kanban.manager;

import kanban.tasks.*;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    private int taskId = 0;
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subTasks = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();
    public final static Comparator<Task> comparator = Comparator
            .comparing(Task::getStartTime, Comparator.nullsLast(Comparator.naturalOrder()))
            .thenComparing(Task::getId);
    private final TreeSet<Task> prioritizedTasks = new TreeSet<>(comparator);

    @Override
    public TreeSet<Task> getPrioritizedTasks() {
        prioritizedTasks.addAll(getTasks());
        prioritizedTasks.addAll(getSubTasks());
        return prioritizedTasks;
    }

    @Override
    public Task getTask(int id) { // получение таска по id
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Epic getEpic(int id) { // получение эпика по id
        historyManager.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public Subtask getSubTask(int id) { // получение сабтаска по id
        historyManager.add(subTasks.get(id));
        return subTasks.get(id);
    }

    @Override
    public ArrayList<Task> getTasks() { // Получение списка всех задач.
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Epic> getEpics() { // Получение списка всех эпиков.
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<Subtask> getSubTasks() { // Получение списка всех подзадач.
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public void removeTaskById(int id) { // удаление таска по id
        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void removeEpicById(int id) { // удаление эпика по id
        for (Integer ids : epics.get(id).getSubtaskIds()) {
            historyManager.remove(ids);
        }
        epics.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void removeSubtaskById(int id) { // удаление сабтаска по id
        int epicId = subTasks.get(id).getEpicId();
        Epic epic = epics.get(epicId);
        epic.removeSubtaskIds(id);
        subTasks.remove(id);
        historyManager.remove(id);
        if (getCurrentEpicSubTasks(epic.getId()) != null) {
            epic.setStartTime(epic.epicStartTime(getCurrentEpicSubTasks(epic.getId())));
            epic.setEpicEndTime(epic.epicEndTime(getCurrentEpicSubTasks(epic.getId())));
            updateEpicStatus(epicId);
        } else removeEpicById(epic.getId());
    }

    @Override
    public void deleteAnyTypeAllTasks() { // удаление всех задач любого типа
        tasks.clear();
        epics.clear();
        subTasks.clear();
        historyManager.getHistory().clear();
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
        int epicId = subTask.getEpicId();
        if (savedTask == null) {
            return;
        }
        subTasks.put(id, subTask);
        updateEpicStatus(epicId);
        epics.get(epicId).updateEpicStartAndEndTime(subTask);
    }

    @Override
    public int addNewTask(Task task) {
        final int id = ++taskId;
        if (!validateTaskTime(task)) {
            task.setId(id);
            tasks.put(id, task);
        } return id;
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
        if (!validateSubtaskTime(subtask)) {
            subtask.setId(id);
            subTasks.put(id, subtask);
            Epic subEpic = epics.get(subtask.getEpicId());
            subEpic.addSubtaskIds(id);
            subEpic.setStartTime(subEpic.epicStartTime(getCurrentEpicSubTasks(subEpic.getId())));
            subEpic.setEpicEndTime(subEpic.epicEndTime(getCurrentEpicSubTasks(subEpic.getId())));
            updateEpicStatus(subEpic.getId());
        }
        return id;
    }

    @Override
    public ArrayList<Integer> getSubtaskIds(int id) { // ввод списка id всех сабтасков эпика
        return epics.get(id).getSubtaskIds();
    }

    @Override
    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    public boolean validateTaskTime(Task newTask) {
        for (Task task : tasks.values())
            return newTask.getStartTime().isEqual(task.getStartTime());
        return false;
    }

    public boolean validateSubtaskTime(Subtask newSubtask) {
        for (Subtask subtask : subTasks.values())
            return newSubtask.getStartTime().isEqual(subtask.getStartTime());
        return false;
    }

}
