package kanban.tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {

    private int id;
    private String name;
    private String detail;
    private TaskStatus status;
    private LocalDateTime startTime;
    private int duration;

    public Task(String name, String detail, TaskStatus status) {
        this.name = name;
        this.detail = detail;
        this.status = status;
    }

    public Task(String name, String detail, TaskStatus status, String startTime, Integer duration) {
        this.name = name;
        this.detail = detail;
        this.status = status;
        this.startTime = LocalDateTime.parse(startTime);
        this.duration = duration;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration);
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

    public TaskType getTaskType() {
        return TaskType.TASK;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public int getDuration() {
        return duration;
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
                ", start time=" + startTime +
                ", duration=" + duration +
                '}';
    }

}


