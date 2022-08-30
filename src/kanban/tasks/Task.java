package kanban.tasks;

import java.util.Objects;

public class Task {

    private int iD;
    private String name;
    private String detail;
    private String status;

    public Task(int iD, String name, String detail, String status) {
        this.iD = iD;
        this.name = name;
        this.detail = detail;
        this.status = status;
    }


    public int getiD() {
        return iD;
    }

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return getiD() == task.getiD() && Objects.equals(getName(), task.getName()) && Objects.equals(getDetail(),
                task.getDetail()) && Objects.equals(getStatus(), task.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getiD(), getName(), getDetail(), getStatus());
    }

    @Override
    public String toString() {

        return "Task{" + "ID=" + iD +
                ", name='" + name +
                ", detail='" + detail +
                ", status=" + status +
                '}';
    }

}


