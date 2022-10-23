package kanban.tasks;
import java.util.Objects;

public class Task {

    private int id;
    private String name;
    private String detail;
    private TaskStatus status;

    public Task(String name, String detail, TaskStatus status) {
        this.name = name;
        this.detail = detail;
        this.status = status;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return getId() == task.getId() && Objects.equals(getName(), task.getName()) && Objects.equals(getDetail(),
                task.getDetail()) && Objects.equals(getStatus(), task.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDetail(), getStatus());
    }

    @Override
    public String toString() {

        return "Task{" + "ID=" + id +
                ", name='" + name +
                ", detail='" + detail +
                ", status=" + status +
                '}';
    }

}


