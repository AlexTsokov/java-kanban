package kanban.manager;

public class ManagerSaveException extends RuntimeException {

    public ManagerSaveException() {
    }

    public ManagerSaveException(final String message) {
        super(message);
    }

    public ManagerSaveException(final Throwable cause) {
        super(cause);
    }

    public ManagerSaveException(String s, RuntimeException e) {
    }
}
