package kanban.manager;

import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.TreeSet;

public interface TaskManager {

    ArrayList<Task> getTasks();

    ArrayList<Epic> getEpics();

    ArrayList<Subtask> getSubTasks();

    void removeTaskById(int id);

    void removeEpicById(int id);

    void removeSubtaskById(int id);

    void deleteAnyTypeAllTasks();

    Task getTask(int id);

    Epic getEpic(int id);

    Subtask getSubTask(int id);


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

    TreeSet<Task> getPrioritizedTasks();

    int getEpicDuration(int id);

    LocalDateTime epicEndTime(int id);

    LocalDateTime epicStartTime(int id);
}

