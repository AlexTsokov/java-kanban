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


    public void getTasksList() { // Получение списка задач (имена)
        System.out.println("Список задач типа Задача:");
        for (Task task : tasks.values()) {
            System.out.println(task.getName());
        }
    }

    public void getEpicsList() { // Получение списка эпиков (имена)
        System.out.println("Список задач типа Эпик:");
        for (Epic epic : epics.values()) {
            System.out.println(epic.getName());
        }
    }

    public void getSubtList() { // Получение списка сабтасков (имена)
        System.out.println("Список задач типа Сабтаск:");
        for (Subtask subtask : subTasks.values()) {
            System.out.println(subtask.getName());
        }
    }

    public void removeTaskById(int iD) { // удаление таска по id
        tasks.remove(iD);
    }

    public void removeEpicById(int iD) { // удаление эпика по id
        epics.remove(iD);
    }

    public void removeSubtaskById(int iD) { // удаление сабтаска по id
        subTasks.remove(iD);
    }

    public HashMap<Integer, Task> getTasks() { // вывести список всех тасков
        return tasks;
    }

    public HashMap<Integer, Subtask> getSubTasks() { // вывести список всех сабтасков
        return subTasks;
    }

    public HashMap<Integer, Epic> getEpics() { // вывести список всех эпиков
        return epics;
    }

    public void deleteAnyTypeAllTasks(HashMap tasks) { // удаление всех задач любого типа
        tasks.clear();
    }

    public int setId() { // генератор уникального id для задач
        taskId += 1;
        return taskId;
    }

    public void getTaskById(int iD) { // получение таска по id
        boolean taskPresent = false;
        int number = 0;
        for (Task task : tasks.values()) {
            if (iD == task.getiD()) {
                taskPresent = true;
                number = iD;
            }
        }
        if (taskPresent) {
            System.out.println(tasks.get(number));
        } else System.out.println("Задачи с таким номером нет");
    }

    public void getEpicById(int iD) { // получение эпика по id
        boolean taskPresent = false;
        int number = 0;
        for (Epic epic : epics.values()) {
            if (iD == epic.getiD()) {
                taskPresent = true;
                number = iD;
            }
        }
        if (taskPresent) {
            System.out.println(epics.get(number));
        } else System.out.println("Задачи с таким номером нет");
    }

    public void getSubtaskById(int iD) { // получение сабтаска по id
        boolean taskPresent = false;
        int number = 0;
        for (Subtask subTask : subTasks.values()) {
            if (iD == subTask.getiD()) {
                taskPresent = true;
                number = iD;
            }
        }
        if (taskPresent) {
            System.out.println(subTasks.get(number));
        } else System.out.println("Задачи с таким номером нет");
    }


    public ArrayList<Subtask> getCurrentEpicSubTasks(int iD) { // Получение подзадач конкретного эпика по его id
        ArrayList<Subtask> currentEpicSubtasks = new ArrayList<>();
        for (Subtask subtask : subTasks.values()) {
            if (iD == subtask.getEpicId()) {
                currentEpicSubtasks.add(subtask);
            }
        }
        return currentEpicSubtasks;
    }


    public void epicStatusCheckAndChange(int iD) { // Обновление статуса Эпика. Не могу додумать реализацию, прошу подсказки.
                                                    // Не корректно работает если у всех сабтасков статус NEW
        ArrayList<Subtask> subtasks = getCurrentEpicSubTasks(iD);
        for (Subtask subtask : subtasks) {
            if (!subtask.getStatus().equals(taskStatus[2])) {
                epics.get(iD).setStatus(taskStatus[1]);
            } else {
                epics.get(iD).setStatus(taskStatus[2]);
            }
        }
    }

    public void updateTask(Task newTask) {  // Обновление задачи любого типа
        for (Task task : tasks.values()) {
            if (newTask.getiD() == task.getiD()) {
                task = newTask;
                tasks.put(task.getiD(), task);
                System.out.println("Задача обновлена");
            }
        }
        for (Epic epic : epics.values()) {
            if (newTask.getiD() == epic.getiD()) {
                epic = (Epic) newTask;
                epics.put(epic.getiD(), epic);
                System.out.println("Задача обновлена");
            }
        }
        for (Subtask subtask : subTasks.values()) {
            if (newTask.getiD() == subtask.getiD()) {
                subtask = (Subtask) newTask;
                subTasks.put(subtask.getiD(), subtask);
                System.out.println("Задача обновлена");
            }
        }
    }

    public void createTasksList(Task task) { // создание списка тасков
        tasks.put(task.getiD(), task);
    }

    public void createEpicsList(Epic epic) { // создание списка эпиков
        epics.put(epic.getiD(), epic);
    }

    public void createSubTaskList(Subtask subTask) { // создание списка сабтасков
        subTasks.put(subTask.getiD(), subTask);
    }
}
