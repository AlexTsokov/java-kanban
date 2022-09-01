package kanban.tasks;
import java.util.Objects;
public class Epic extends Task {

    public Epic(String name, String detail, String status) {
        super(name, detail, status);
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




