package kanban.manager;

import kanban.tasks.*;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private final File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    @Override
    public int addNewTask(Task task) {
        int id = super.addNewTask(task);
        save();
        return id;
    }

    @Override
    public int addNewEpic(Epic epic) {
        int id = super.addNewEpic(epic);
        save();
        return id;
    }

    @Override
    public int addNewSubtask(Subtask subtask) {
        int id = super.addNewSubtask(subtask);
        save();
        return id;
    }

    @Override
    public Task getTask(int id) {
        Task task = super.getTask(id);
        save();
        return task;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = super.getEpic(id);
        save();
        return epic;
    }

    @Override
    public Subtask getSubTask(int id) {
        Subtask subtask = super.getSubTask(id);
        save();
        return subtask;
    }

    private void save() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {

            bufferedWriter.write("id, type, name, status, description, epic");
            bufferedWriter.newLine();

            Collection<Task> values = getTasks();
            for (Task value : values) {
                bufferedWriter.write(value.getId() + "," + value.getTaskType() + "," + value.getName() + "," +
                        value.getStatus() + "," + value.getDetail());
                bufferedWriter.newLine();
            }

            Collection<Epic> valuesEpics = getEpics();
            for (Epic value : valuesEpics) {
                bufferedWriter.write(value.getId() + "," + value.getTaskType() + "," + value.getName() + "," +
                        value.getStatus() + "," + value.getDetail());
                bufferedWriter.newLine();
            }

            Collection<Subtask> valuesSub = getSubTasks();
            for (Subtask value : valuesSub) {
                bufferedWriter.write(value.getId() + "," + value.getTaskType() + "," + value.getName() + "," +
                        value.getStatus() + "," + value.getDetail() + "," + value.getEpicId());
                bufferedWriter.newLine();
            }

            bufferedWriter.newLine();
            bufferedWriter.write(CSVTaskFormat.toString(getHistoryManager())); // запись истории
            bufferedWriter.newLine();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Не удалось сохранить файл");
        }
    }

    public static FileBackedTasksManager loadFromFile(File file) throws IOException {
        final FileBackedTasksManager taskManager = new FileBackedTasksManager(file);
        try {
            final String csv = Files.readString(file.toPath());
            final String[] lines = csv.split(System.lineSeparator());
            int linesNumber = 0;
            for (int i = 1; i < lines.length; i++) {
                String line = lines[i];
                if (!(line.isBlank())) {
                    fromString(line);
                    System.out.println(line);
                    linesNumber++;
                } else {
                    break;
                }
            }
            int historyLineNumber = linesNumber + 2;
            System.out.println(historyFromString(lines[historyLineNumber]));

        } catch (IOException e) {
            throw new ManagerSaveException("Невозможно прочитать файл: " + file.getName(), e);
        }
        return taskManager;
    }

    public static Task fromString(String value) {
        String[] values = value.split(",");
        String name = values[2];
        String detail = values[4];
        Integer id = Integer.valueOf(values[0]);
        TaskType type = TaskType.valueOf(values[1]);
        TaskStatus status = TaskStatus.valueOf(values[3]);

        switch (type) {
            case TASK:
                Task task = new Task(name, detail, status);
                task.setId(id);
                return task;
            case EPIC:
                Epic epic = new Epic(name, detail, status);
                epic.setId(id);
                return epic;
            case SUB_TASK:
                Integer epicId = Integer.valueOf(values[5]);
                Subtask subtask = new Subtask(name, detail, status, epicId);
                subtask.setId(id);
                return subtask;
            default:
                return null;
        }
    }

    static List<Integer> historyFromString(String value) {
        List<Integer> history = new ArrayList<>();
        String[] values = value.split(",");
        for (String val : values) {
            history.add(Integer.valueOf(val));
        }
        return history;
    }

}