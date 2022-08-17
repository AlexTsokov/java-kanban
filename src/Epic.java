import java.util.ArrayList;

public class Epic extends Task {


    ArrayList<Subtask> epicSubTasks = new ArrayList<>();
    public Epic(String taskName, String taskDetail, String taskStatus) {
        super(taskName, taskDetail, taskStatus);
        this.taskStatus = "NEW";
    }

    @Override
    public String toString() {
        return taskName;
    }

    }

