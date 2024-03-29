package kanban.manager;

import kanban.tasks.*;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    protected int taskId = 0;
    protected final HashMap<Integer, Task> tasks = new HashMap<>();
    protected final HashMap<Integer, Epic> epics = new HashMap<>();
    protected final HashMap<Integer, Subtask> subTasks = new HashMap<>();
    protected final HistoryManager historyManager = Managers.getDefaultHistory();
    public final static Comparator<Task> comparator = Comparator
            .comparing(Task::getStartTime, Comparator.nullsLast(Comparator.naturalOrder()))
            .thenComparing(Task::getId);
    private final TreeSet<Task> prioritizedTasks = new TreeSet<>(comparator);

    @Override
    public TreeSet<Task> getPrioritizedTasks() {
        prioritizedTasks.clear();
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
        ArrayList<Subtask> subs = getCurrentEpicSubTasks(epic.getId());
        if (!subs.isEmpty()) {
            updateEpicStartEndTimeAndDuration(epic.getId());
            updateEpicStatus(epicId);
        } else removeEpicById(epic.getId());
    }

    @Override
    public void deleteAnyTypeAllTasks() { // удаление всех задач любого типа
        tasks.clear();
        epics.clear();
        subTasks.clear();
        historyManager.clear();
        prioritizedTasks.clear();
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
    public void updateSubTask(Subtask subtask) { // Обновление сабтаска.
        if (validateTaskAndSubtaskTime(subtask)) {
            final int id = subtask.getId();
            final Subtask savedTask = subTasks.get(id);
            int epicId = subtask.getEpicId();
            if (savedTask == null) {
                return;
            }
            subTasks.put(id, subtask);
            updateEpicStatus(epicId);
            updateEpicStartEndTimeAndDuration(epicId);
        }
    }

    @Override
    public int addNewTask(Task task) {
        final int id = ++taskId;
        task.setEndTime();
        if (validateTaskAndSubtaskTime(task)) {
            task.setId(id);
            tasks.put(id, task);
        }
        return id;
    }

    @Override
    public int addNewEpic(Epic epic) {
        final int id = ++taskId;
        epic.setEndTime();
        epic.setId(id);
        epics.put(id, epic);
        return id;
    }

    @Override
    public int addNewSubtask(Subtask subtask) {
        final int id = ++taskId;
        subtask.setEndTime();
        if (validateTaskAndSubtaskTime(subtask)) {
            subtask.setId(id);
            subTasks.put(id, subtask);
            Epic subEpic = epics.get(subtask.getEpicId());
            subEpic.addSubtaskIds(id);
            updateEpicStartEndTimeAndDuration(subEpic.getId());
            updateEpicStatus(subEpic.getId());
        }
        return id;
    }

    private void updateEpicStartEndTimeAndDuration(int id) { // обновление времени и длительности эпика
        Epic subEpic = epics.get(id);
        ArrayList<Subtask> subs = getCurrentEpicSubTasks(id);
        subEpic.setStartTime(subEpic.epicStartTime(subs));
        subEpic.setEpicEndTime(subEpic.epicEndTime(subs));
        subEpic.setDuration(getEpicDuration(id));
    }

    @Override
    public ArrayList<Integer> getSubtaskIds(int id) { // ввод списка id всех сабтасков эпика
        return epics.get(id).getSubtaskIds();
    }

    @Override
    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    public boolean validateTaskAndSubtaskTime(Task task) {
        if (getPrioritizedTasks().isEmpty())
            return true;
        for (Task existTask : getPrioritizedTasks()) {
            if (existTask.getStartTime().isBefore(task.getStartTime())) {
                if (existTask.getEndTime().isBefore(task.getStartTime())
                        || existTask.getEndTime().equals(task.getStartTime())) {
                    return true;
                }
            }
            if (task.getStartTime().isBefore(existTask.getStartTime())) {
                if (task.getEndTime().isBefore(existTask.getStartTime())
                        || task.getEndTime().equals(existTask.getStartTime())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getEpicDuration(int id) {
        ArrayList<Subtask> subtasks = getCurrentEpicSubTasks(id);
        int duration = 0;
        if (subtasks != null) {
            for (Subtask sub : subtasks) {
                duration += sub.getDuration();
            }
        }
        return duration;
    }

    public void findAndSetMaxId(int id) {
        if (this.taskId < id) {
            this.taskId = id;
        }
    }

    public int getCurrentTaskId() {
        return taskId;
    }
}
