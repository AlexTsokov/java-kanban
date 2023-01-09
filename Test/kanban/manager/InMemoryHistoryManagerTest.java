package kanban.manager;

import kanban.tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static kanban.tasks.TaskStatus.NEW;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest extends TaskManagerTest {

    @BeforeEach
    void setUp() {
        taskManager = new InMemoryTaskManager();
    }

    List<Task> getHistoryForTest() {
        return taskManager.getHistoryManager().getHistory();
    }

    @Test
    @DisplayName("Проверка, что история пуста до инициации просмотров")
    public void historyShouldBeEmptyBeforeGetAnyTask() {
        initTask();
        assertTrue(getHistoryForTest().isEmpty());
    }

    @Test
    @DisplayName("Проверка наличия Тасков в истории после просмотров")
    public void taskShouldBeInHistory() {
        initTask();
        taskManager.getTask(1);
        taskManager.getTask(2);
        assertTrue(getHistoryForTest().contains(task1));
        assertTrue(getHistoryForTest().contains(task2));
    }

    @Test
    @DisplayName("Проверка отсутствия дублирования в истории после просмотров")
    public void taskShouldNotBeDoubledInHistory() {
        initTask();
        taskManager.getTask(1);
        taskManager.getTask(1);
        taskManager.getTask(1);
        taskManager.getTask(2);
        taskManager.getTask(2);
        assertTrue(getHistoryForTest().contains(task1));
        assertTrue(getHistoryForTest().contains(task2));
        assertEquals(2, getHistoryForTest().size());
    }

    @Test
    @DisplayName("Проверка удаления из истории")
    public void taskShouldBeDeletedFromHistory() {
        initTask();
        taskManager.addNewTask(new Task("name3", "description", NEW, "2021-12-20T23:20:21",60));
        taskManager.addNewTask(new Task("name3", "description", NEW, "2021-12-20T23:20:21",60));
        taskManager.getTask(1);
        taskManager.getTask(2);
        taskManager.getTask(3);
        taskManager.getTask(4);
        taskManager.getHistoryManager().remove(1); // удаляем из начала
        taskManager.getHistoryManager().remove(3); // удаляем из середины
        taskManager.getHistoryManager().remove(4); // удаляем с конца
        assertTrue(getHistoryForTest().contains(taskManager.getTask(2)));
        assertEquals(1, getHistoryForTest().size());
    }

}