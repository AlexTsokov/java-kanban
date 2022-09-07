package kanban.manager;

import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;

import java.util.ArrayList;

public interface TaskManager {

    ArrayList<Task> getTasks();

    ArrayList<Task> getEpics();

    ArrayList<Task> getSubTasks();

    void removeTaskById(int id);

    void removeEpicById(int id);

    void removeSubtaskById(int id);

    void deleteAnyTypeAllTasks();

    Task getTask(int id);

    Task getEpic(int id);

    Task getSubTask(int id);

    ArrayList<Subtask> getCurrentEpicSubTasks(int id);

    void updateEpicStatus(int epicId);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubTask(Subtask subTask);

    int addNewTask(Task task);

    int addNewEpic(Epic epic);

    int addNewSubtask(Subtask subtask);

    ArrayList<Integer> getSubtaskIds(int id);

    HistoryManager getHistoryManager();
}

