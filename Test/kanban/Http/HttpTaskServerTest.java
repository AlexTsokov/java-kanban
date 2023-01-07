package kanban.Http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import kanban.manager.Managers;
import kanban.manager.TaskManager;
import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;
import kanban.tasks.TaskStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskServerTest {

    private HttpTaskServer taskServer;
    private TaskManager taskManager;
    private Gson gson = Managers.getGson();

    private Task task;
    private Epic epic;
    private Subtask subtask;

    @BeforeEach
    void init() throws IOException {
        taskManager = Managers.getDefaultFile();
        taskServer = new HttpTaskServer(taskManager);
        taskManager.addNewTask(new Task("Приготовить уху", "Приготовить суп из пойманной рыбы", TaskStatus.NEW, "2021-12-20T23:20:21", 60));
        Epic epic = new Epic("Попить чай", "Приготовить чай и выпить его", TaskStatus.NEW);
        epic.setStartTime(LocalDateTime.parse("2021-11-20T23:20:00"));
        epic.setEpicEndTime(LocalDateTime.parse("2021-11-20T23:21:00"));
        epic.setDuration(60);
        taskManager.addNewEpic(epic);

        taskManager.addNewSubtask(new Subtask("Заварка", "Заварить заварку в чайничке", TaskStatus.DONE, "2021-12-21T23:20:21", 60, 2));

        taskServer.start();
    }

    @AfterEach
    void tearDown() {
        taskServer.stop();
    }

    @Test
    @DisplayName("Проверка получения всех тасков с сервера")
    void shouldGetAllTasks() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        Type userType = new TypeToken<ArrayList<Task>>() {}.getType();
        List<Task> actual = gson.fromJson(response.body(), userType);
        List<Task> exp = new ArrayList<>(taskManager.getPrioritizedTasks());
        assertNotNull(actual);
        assertEquals(2, actual.size());
        assertEquals(actual, exp);
    }


    @Test
    @DisplayName("Проверка таска по id с сервера")
    void shouldGetTaskWithId() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task/1");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        Type userType = new TypeToken<Task>() {}.getType();
        Task actual = gson.fromJson(response.body(), userType);
        assertNotNull(actual);
        assertEquals(actual, taskManager.getTask(1));
    }

    @Test
    @DisplayName("Проверка таска по id с сервера")
    void shouldGetEpicWithId() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic/2");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        Type userType = new TypeToken<Epic>() {}.getType();
        Epic actual = gson.fromJson(response.body(), userType);
        assertNotNull(actual);
        assertEquals(actual, taskManager.getEpic(2));
    }

    @Test
    @DisplayName("Проверка сабтаска по id с сервера")
    void shouldGetSubtaskWithId() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask/3");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        Type userType = new TypeToken<Subtask>() {}.getType();
        Subtask actual = gson.fromJson(response.body(), userType);
        assertNotNull(actual);
        assertEquals(actual, taskManager.getSubTask(3));
    }
}