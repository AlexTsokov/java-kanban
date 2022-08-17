public class Subtask extends Task {

    Epic epic;

    public Subtask(Epic epic, String taskName, String taskDetail, String taskStatus) {
        super(taskName, taskDetail, taskStatus);
        this.epic = epic;
    }

    @Override
    public String toString() {
        return taskName;
    }
}