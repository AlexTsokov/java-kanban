package kanban.manager;

import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;

import java.util.ArrayList;
import java.util.Set;
import com.google.gson.JsonArray; // описывает JSON-массив
import com.google.gson.JsonElement; // описывает любой тип данных JSON
import com.google.gson.JsonObject; // описывает JSON-объект
import com.google.gson.JsonParser; // разбирает JSON-документ на элементы

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

    Set<Task> getPrioritizedTasks();

    int getEpicDuration(int id);
}

