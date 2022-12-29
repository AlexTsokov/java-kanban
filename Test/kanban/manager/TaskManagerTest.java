package kanban.manager;

import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static kanban.tasks.TaskStatus.*;
import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskManagerTest<T extends TaskManager> { // Все общие тесты в этом классе

    protected T taskManager;
    protected Task task1;
    protected Task task2;
    protected Epic epic1;
    protected Subtask subtask1;
    protected Subtask subtask2;

    protected void initTask() {
        task1 = new Task("name", "description", NEW,"2021-12-20T23:20:21",60);
        task2 = new Task("name2", "description2", NEW, "2021-12-25T20:20:00",60);
        final int task1Id = taskManager.addNewTask(task1);
        final int task2Id = taskManager.addNewTask(task2);
    }

    protected void initEpicWithSubtasks() {
        epic1 = new Epic("Попить чай", "Приготовить чай и выпить его", NEW);
        subtask1 = new Subtask("Заварка", "Заварить заварку в чайничке", NEW, "2021-12-20T23:00:00",60,1);
        subtask2 = new Subtask("Сахар", "Положить сахар и размешать", NEW, "2021-12-21T21:00:00",60,1);
        final int epic1Id = taskManager.addNewEpic(epic1);
        final int subtask1Id = taskManager.addNewSubtask(subtask1);
        final int subtask2Id = taskManager.addNewSubtask(subtask2);
    }

    @Test
    @DisplayName("Проверка получения Тасков")
    void shouldReturnTasksList() {
        initTask();
        List<Task> tasks = taskManager.getTasks();

        Assertions.assertTrue(tasks.contains(taskManager.getTask(1)));
        Assertions.assertTrue(tasks.contains(taskManager.getTask(2)));

        Assertions.assertAll(
                () -> assertEquals(2, tasks.size()),
                () -> Assertions.assertTrue(tasks.contains(taskManager.getTask(1))),
                () -> Assertions.assertTrue(tasks.contains(taskManager.getTask(2)))
        );
    }

    @Test
    @DisplayName("Проверка добавления Тасков")
    void newTaskShouldBeAdded() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", NEW, "2021-12-20T23:20:21",60);
        final int taskId = taskManager.addNewTask(task);

        final Task savedTask = taskManager.getTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getTasks();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    @DisplayName("Проверка удаления Тасков по ID")
    public void taskByIdShouldBeRemoved() {
        initTask();
        taskManager.removeTaskById(1);
        List<Task> tasksAfterRemove = taskManager.getTasks();
        assertFalse(tasksAfterRemove.contains(task1));
        assertEquals(1, tasksAfterRemove.size(), "Размер списка задач не изменился.");
    }

    @Test
    @DisplayName("Проверка получения Таска по ID")
    public void shouldGetTaskById() {
        initTask();
        assertEquals(task1, taskManager.getTask(1), "Таски не совпадают");
    }

    @Test
    @DisplayName("Проверка получения Таска по несуществующему ID")
    public void shouldThrowExceptionWhenWrongTaskId() {
        initTask();
        assertThrows(NullPointerException.class, () -> taskManager.getTask(10));
    }

    @Test
    @DisplayName("Проверка получения всех Сабтасков Эпика по ID")
    public void shouldGetCurrentEpicSubtasks() {
        initEpicWithSubtasks();
        List<Subtask> subtasks = taskManager.getCurrentEpicSubTasks(1);
        assertAll(
                () -> assertEquals(2, subtasks.size()),
                () -> assertTrue(subtasks.contains(taskManager.getSubTask(2))),
                () -> assertTrue(subtasks.contains(taskManager.getSubTask(3)))
        );
    }

    @Test
    @DisplayName("Проверка статуса Эпика, когда все сабтаски NEW")
    void checkStatusOnEpicWithSubTasksNew() {
        initEpicWithSubtasks();
        Assertions.assertEquals(epic1.getStatus(), NEW);
    }

    @Test
    @DisplayName("Проверка статуса Эпика, когда сабтаск IN_PROGRESS")
    void checkStatusOnEpicWithSubTasksInProgress() {
        initEpicWithSubtasks();
        subtask1.setStatus(IN_PROGRESS);
        taskManager.updateEpicStatus(epic1.getId());
        Assertions.assertEquals(epic1.getStatus(), IN_PROGRESS);
    }

    @Test
    @DisplayName("Проверка статуса Эпика, когда один сабтаск DONE")
    void checkStatusOnEpicWithOneOfSubTasksDone() {
        initEpicWithSubtasks();
        subtask1.setStatus(DONE);
        subtask2.setStatus(IN_PROGRESS);
        taskManager.updateEpicStatus(epic1.getId());
        Assertions.assertEquals(epic1.getStatus(), IN_PROGRESS);
    }

    @Test
    @DisplayName("Проверка статуса Эпика, когда все сабтаски DONE")
    void checkStatusOnEpicWithAllSubTasksDone() {
        initEpicWithSubtasks();
        subtask1.setStatus(DONE);
        subtask2.setStatus(DONE);
        taskManager.updateEpicStatus(epic1.getId());
        Assertions.assertEquals(epic1.getStatus(), DONE);
    }

    @Test
    @DisplayName("Проверка статуса Эпика, когда сабтаски NEW и DONE")
    void checkStatusOnEpicWithSubTasksDoneAndNew() {
        initEpicWithSubtasks();
        subtask1.setStatus(DONE);
        taskManager.updateEpicStatus(epic1.getId());
        Assertions.assertEquals(epic1.getStatus(), IN_PROGRESS);
    }

    @Test
    @DisplayName("Проверка статуса Эпика без подзадач")
    void checkStatusOnEpicWithoutSubTasks() {
        Epic epicWithoutSubtasks = new Epic("Попить чай", "Приготовить чай и выпить его", NEW);
        taskManager.addNewEpic(epicWithoutSubtasks);
        taskManager.updateEpicStatus(epicWithoutSubtasks.getId());
        Assertions.assertEquals(epicWithoutSubtasks.getStatus(), NEW);
    }

    @Test
    @DisplayName("Проверка, что Сабтаск относится к Эпику")
    void subtaskShouldHaveEpic() {
        initEpicWithSubtasks();
        Subtask subtaskToCheck = new Subtask("Лимон", "Добавить лимон", NEW, "2021-12-20T23:00:00", 60, 1);
        taskManager.addNewSubtask(subtaskToCheck);
        assertAll(
                () -> assertEquals(3, taskManager.getCurrentEpicSubTasks(epic1.getId()).size()),
                () -> assertTrue(taskManager.getCurrentEpicSubTasks(epic1.getId()).contains(subtaskToCheck))
           );
    }

}


