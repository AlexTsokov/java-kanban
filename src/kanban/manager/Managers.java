package kanban.manager;


import com.google.gson.*;
import kanban.Http.HttpTaskServer;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;

public class Managers extends InMemoryHistoryManager {


    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static FileBackedTasksManager getDefaultFile() {
        return new FileBackedTasksManager(new File("src/resources/tasks.csv"));
    }

    public static HttpTaskManager getDefaultHttpManager(URI uri, boolean load) throws IOException, InterruptedException {
        return new HttpTaskManager(uri, load);
    }

    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new HttpTaskServer.LocalDateTimeAdapter());
        return gsonBuilder.create();
    }
}
