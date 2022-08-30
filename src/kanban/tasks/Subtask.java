package kanban.tasks;

public class Subtask extends Task {

    private int epicId;

    public Subtask(int iD, String name, String detail, String status, int epicId) {
        super(iD, name, detail, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }



}

