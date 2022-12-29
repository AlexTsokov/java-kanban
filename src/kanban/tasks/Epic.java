package kanban.tasks;

import kanban.manager.InMemoryTaskManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.TreeSet;

public class Epic extends Task {

    protected ArrayList<Integer> subtaskIds = new ArrayList<>();
    LocalDateTime epicEndTime;

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

    public void setEpicEndTime(LocalDateTime getEpicEndTime) {
        if (!subtaskIds.isEmpty()) {
            epicEndTime = getEpicEndTime;
        }
    }

    @Override
    public void setStartTime(LocalDateTime epicStartTime) {
        if (!subtaskIds.isEmpty()) {
            super.setStartTime(epicStartTime);
        }
    }

    public LocalDateTime getEpicEndTime() {
        return epicEndTime;
    }

    public LocalDateTime epicStartTime(ArrayList<Subtask> subtasks) {
        if (subtasks != null) {
            TreeSet<Subtask> prioritizedSubTasks = new TreeSet<>(InMemoryTaskManager.comparator);
            prioritizedSubTasks.addAll(subtasks);
            Subtask first = prioritizedSubTasks.first();
            return first.getStartTime();
        }
        return null;
    }

    public LocalDateTime epicEndTime(ArrayList<Subtask> subtasks) {
        if (subtasks != null) {
            TreeSet<Subtask> prioritizedSubTasks = new TreeSet<>(InMemoryTaskManager.comparator);
            prioritizedSubTasks.addAll(subtasks);
            Subtask last = prioritizedSubTasks.last();
            return last.getStartTime().plusMinutes(last.getDuration());
        }
        return null;
    }

    public int getEpicDuration(ArrayList<Subtask> subtasks) {
        int duration = 0;
        if (subtasks != null) {
            for (Subtask sub : subtasks) {
                duration += sub.getDuration();
            }
        }
        return duration;
    }

    public void updateEpicStartAndEndTime(Subtask subtask) {
        if (getStartTime().isAfter(subtask.getStartTime())) {
            setStartTime(subtask.getStartTime());
        }
        if (subtask.getStartTime().isAfter(getEndTime())) {
            setEpicEndTime(subtask.getStartTime().plusMinutes(subtask.getDuration()));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Epic)) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        if (!getSubtaskIds().equals(epic.getSubtaskIds())) return false;
        return epicEndTime.equals(epic.epicEndTime);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getSubtaskIds().hashCode();
        result = 31 * result + epicEndTime.hashCode();
        return result;
    }
}





