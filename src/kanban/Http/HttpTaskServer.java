package kanban.Http;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import kanban.manager.Managers;
import kanban.manager.TaskManager;
import kanban.tasks.Epic;
import kanban.tasks.Subtask;
import kanban.tasks.Task;

import java.io.*;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.UTF_8;
import static kanban.manager.Managers.getGson;


public class HttpTaskServer {

    public static final int PORT = 8080;

    private HttpServer server;
    private Gson gson;

    private TaskManager taskManager;

    public HttpTaskServer() throws IOException {
        this(Managers.getDefaultFile());
    }

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        this.taskManager = taskManager;
        gson = getGson();
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/tasks", this::handleTasks);
    }

    private void handleTasks(HttpExchange httpExchange) {
        try {
            String path = httpExchange.getRequestURI().getPath();
            String requestMethod = httpExchange.getRequestMethod();
            switch (requestMethod) {
                case "GET": {
                    if (Pattern.matches("^/tasks$", path)) {
                        String response = gson.toJson(taskManager.getPrioritizedTasks());
                        sendText(httpExchange, response);
                        break;
                    }
                    if (Pattern.matches("^/tasks/task$", path)) {
                        String response = gson.toJson(taskManager.getTasks());
                        sendText(httpExchange, response);
                        break;
                    }
                    if (Pattern.matches("^/tasks/epic$", path)) {
                        String response = gson.toJson(taskManager.getEpics());
                        sendText(httpExchange, response);
                        break;
                    }
                    if (Pattern.matches("^/tasks/subtask$", path)) {
                        String response = gson.toJson(taskManager.getSubTasks());
                        sendText(httpExchange, response);
                        break;
                    }
                    if (Pattern.matches("^/tasks/history$", path)) {
                        String response = gson.toJson(taskManager.getHistoryManager().getHistory());
                        sendText(httpExchange, response);
                        break;
                    }
                    if (Pattern.matches("^/tasks/task/\\d+$", path)) {
                        String pathId = path.replaceFirst("/tasks/task/", "");
                        int id = parsePathId(pathId);
                        if (id != -1) {
                            String response = gson.toJson(taskManager.getTask(id));
                            sendText(httpExchange, response);
                            break;
                        }
                    }
                    if (Pattern.matches("^/tasks/epic/\\d+$", path)) {
                        String pathId = path.replaceFirst("/tasks/epic/", "");
                        int id = parsePathId(pathId);
                        if (id != -1) {
                            String response = gson.toJson(taskManager.getEpic(id));
                            sendText(httpExchange, response);
                            break;
                        }
                    }
                    if (Pattern.matches("^/tasks/subtask/\\d+$", path)) {
                        String pathId = path.replaceFirst("/tasks/subtask/", "");
                        int id = parsePathId(pathId);
                        if (id != -1) {
                            String response = gson.toJson(taskManager.getSubTask(id));
                            sendText(httpExchange, response);
                            break;
                        }
                    } else {
                        wrongId(httpExchange);
                        break;
                    }
                    break;
                }
                case "POST": {
                    if (Pattern.matches("^/tasks/task$", path)) {
                        String jsonTask = readText(httpExchange);
                        Task task = getGson().fromJson(jsonTask, Task.class);
                        taskManager.addNewTask(task);
                        System.out.println("Задача " + task.getId() + " добавлена");
                        httpExchange.sendResponseHeaders(200, 0);
                        break;
                    }
                    if (Pattern.matches("^/tasks/epic$", path)) {
                        String jsonEpic = readText(httpExchange);
                        Epic epic = getGson().fromJson(jsonEpic, Epic.class);
                        taskManager.addNewEpic(epic);
                        System.out.println("Задача " + epic.getId() + " добавлена");
                        httpExchange.sendResponseHeaders(200, 0);
                        break;
                    }
                    if (Pattern.matches("^/tasks/subtask$", path)) {
                        String jsonSubtask = readText(httpExchange);
                        Subtask subtask = getGson().fromJson(jsonSubtask, Subtask.class);
                        taskManager.addNewSubtask(subtask);
                        System.out.println("Задача " + subtask.getId() + " добавлена");
                        httpExchange.sendResponseHeaders(200, 0);
                        break;
                    }
                    break;
                }
                case "DELETE": {
                    if (Pattern.matches("^/tasks/task/?id=\\d+$", path)) {
                        String pathId = path.replaceFirst("/tasks/task/?id=", "");
                        int id = parsePathId(pathId);
                        if (id != -1) {
                            taskManager.removeTaskById(id);
                            System.out.println("Задача " + id + " удалена");
                            httpExchange.sendResponseHeaders(200, 0);
                        }
                    } else {
                        wrongId(httpExchange);
                        break;
                    }
                    if (Pattern.matches("^/tasks/epic/?id=\\d+$", path)) {
                        String pathId = path.replaceFirst("/tasks/epic/?id=", "");
                        int id = parsePathId(pathId);
                        if (id != -1) {
                            taskManager.removeEpicById(id);
                            System.out.println("Задача " + id + " удалена");
                            httpExchange.sendResponseHeaders(200, 0);
                        }
                    } else {
                        wrongId(httpExchange);
                        break;
                    }
                    if (Pattern.matches("^/tasks/subtask/?id=\\d+$", path)) {
                        String pathId = path.replaceFirst("/tasks/subtask/?id=", "");
                        int id = parsePathId(pathId);
                        if (id != -1) {
                            taskManager.removeSubtaskById(id);
                            System.out.println("Задача " + id + " удалена");
                            httpExchange.sendResponseHeaders(200, 0);
                        }
                    } else {
                        wrongId(httpExchange);
                        break;
                    }
                    break;
                }
                default: {
                    System.out.println("Недопустимый метод, введите GET или POST. Вы ввели " + requestMethod);
                    httpExchange.sendResponseHeaders(405, 0);
                    break;
                }
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            httpExchange.close();
        }

    }

    private int parsePathId(String path) {
        try {
            return Integer.parseInt(path);
        } catch (NumberFormatException exception) {
            return -1;
        }
    }

    public void start() {
        System.out.println("Запускаем сервер на порту " + PORT);
        server.start();
    }

    public void stop() {
        server.stop(0);
        System.out.println("Сервер остановлен на порту " + PORT);
    }

    protected String readText(HttpExchange h) throws IOException {
        return new String(h.getRequestBody().readAllBytes(), UTF_8);
    }

    protected void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
    }

    public static class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {

        @Override
        public void write(JsonWriter jsonWriter, LocalDateTime localDateTime) throws IOException {
            jsonWriter.value(String.valueOf(localDateTime));
        }

        @Override
        public LocalDateTime read(JsonReader jsonReader) throws IOException {
            return LocalDateTime.parse(jsonReader.nextString());
        }
    }

    public void wrongId(HttpExchange httpExchange) throws IOException {
        System.out.println("Недопустимый идентификатор");
        httpExchange.sendResponseHeaders(405, 0);
    }

}
