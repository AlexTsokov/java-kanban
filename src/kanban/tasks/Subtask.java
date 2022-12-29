package kanban.tasks;

public class Subtask extends Task {

    private int epicId;

    public Subtask(String name, String detail, TaskStatus status, int epicId) {
        super(name, detail, status);
        this.epicId = epicId;
    }

    public Subtask(String name, String detail, TaskStatus status, String startTime, Integer duration, int epicId) {
        super(name, detail, status, startTime, duration);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.SUB_TASK;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subtask)) return false;
        if (!super.equals(o)) return false;

        Subtask subtask = (Subtask) o;

        return getEpicId() == subtask.getEpicId();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getEpicId();
        return result;
    }
}

