package kanban.Http;

import kanban.manager.ManagerSaveException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {

    private URI url;
    private final String apiToken;

    public KVTaskClient(URI url) {
        this.url = url;
        apiToken = register(url.toString());
    }

    private String register(String url) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url + "register"))
                    .GET()
                    .build();
            HttpResponse<String> send = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return send.body();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void put(String key, String value) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url + "save/" + key + "?API_TOKEN=" + apiToken))
                    .POST(HttpRequest.BodyPublishers.ofString(value))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new ManagerSaveException("Сервер не вернул данные. Код состояния: " + response.statusCode());
            }
        } catch (InterruptedException | IOException e) {
            throw new ManagerSaveException(e);
        }
    }

    public String load(String key) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "load/" + key + "?API_TOKEN=" + apiToken))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new ManagerSaveException("Данные не найдены");
        }
        return response.body();
    }
}
