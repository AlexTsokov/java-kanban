package kanban.manager;


import com.google.gson.*;
import kanban.Http.HttpTaskServer;

import java.io.File;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

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

    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new HttpTaskServer.LocalDateTimeAdapter());
        return gsonBuilder.create();
    }

}
