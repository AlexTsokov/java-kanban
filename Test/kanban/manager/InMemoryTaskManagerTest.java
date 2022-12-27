package kanban.manager;

import kanban.tasks.Task;
import kanban.tasks.TaskStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    void setUp() {
        taskManager = new InMemoryTaskManager();
    }

    void test() { // отдельный тест
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
    }


}