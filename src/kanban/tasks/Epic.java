package kanban.tasks;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {

    protected ArrayList<Integer> subtaskIds = new ArrayList<>();
    LocalDateTime epicEndTime;

    public void setEpicEndTime(LocalDateTime epicEndTime) {
        epicEndTime = super.getEndTime();
    }

    @Override
    public void setStartTime(LocalDateTime epicStartTime) {
        if (!subtaskIds.isEmpty()) {
            super.setStartTime(epicStartTime);
        }
    }

    @Override
    public void setDuration(int epicDuration) {
        if (!subtaskIds.isEmpty()) {
            super.setDuration(epicDuration);
        }
    }

    public Epic(String name, String detail, TaskStatus status) {
        super(name, detail, status);
    }

    public void addSubtaskIds(int id) {
        subtaskIds.add(id);
    }

    public void removeSubtaskIds(int id) {
        for (int i = 0; i < subtaskIds.size(); i++) {
            if (subtaskIds.get(i).equals(id)) {
                subtaskIds.remove(i);
            }
        }
    }

    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.EPIC;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Epic)) return false;
        Epic epic = (Epic) o;
        return getId() == epic.getId() && Objects.equals(getName(), epic.getName()) && Objects.equals(getDetail(),
                epic.getDetail()) && Objects.equals(getStatus(), epic.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDetail(), getStatus());
    }

}




