package kanban.manager;

import kanban.tasks.Task;
import kanban.tasks.TaskStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static kanban.tasks.TaskStatus.NEW;
import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {

    private File file;

    @BeforeEach
    void setUp() {
        file = new File("test/kanban/manager/test.csv");
        taskManager = new FileBackedTasksManager(file);
    }

    @AfterEach
    void cleanUp(){
        file.delete();
    }

    @Test
    @DisplayName("Проверка восстановления Таскменеджера из файла")
    void testSaveAndRestoreEpicWOSubs() {
        initTask();
        taskManager.getTask(1);
        FileBackedTasksManager restoredTaskManager = FileBackedTasksManager.loadFromFile(new File("test/kanban/manager/test.csv"));
        assertEquals(taskManager.getTask(1), restoredTaskManager.getTask(1));
        assertEquals(taskManager.getHistoryManager().getHistory(), restoredTaskManager.getHistoryManager().getHistory());
    }

    @Test
    @DisplayName("Проверка записи Таска в строку")
    void taskShouldTransformToString() {
        Task task = new Task("name", "description", NEW,"2000-01-01T00:00:00",10);
        String expected = "0,TASK,name,NEW,description,2000-01-01T00:00,10";
        assertEquals(expected, taskManager.toString(task));
    }

    @Test
    @DisplayName("Проверка восстановления Таска из строки")
    void taskShouldBeCreatedFromString() {
        Task taskAsTask = new Task("name", "description", NEW,"2000-01-01T00:00:00",10);
        String taskAsString = "0,TASK,name,NEW,description,2000-01-01T00:00,10";
        assertEquals(taskAsTask, FileBackedTasksManager.fromString(taskAsString));
    }

}