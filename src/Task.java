public class Task {

    String taskName;
    String taskDetail;
    String taskStatus;

    Task(String taskName, String taskDetail, String taskStatus) {
        this.taskName = taskName;
        this.taskDetail = taskDetail;
        this.taskStatus = taskStatus;
    }

    @Override
    public String toString() {
        return taskName;
    }
}
