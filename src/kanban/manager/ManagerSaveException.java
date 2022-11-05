package kanban.manager;

import java.io.IOException;

public class ManagerSaveException extends IOException {

    public ManagerSaveException() {
    }

    public ManagerSaveException(final String message) {
        super(message);
    }

    public ManagerSaveException(final Throwable cause) {
        super(cause);
    }

    public ManagerSaveException(String s, IOException e) {
    }
}
