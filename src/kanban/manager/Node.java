package kanban.manager;

public class Node<Task> {

    Task task;
    Node next;
    Node prev;


    public Node(Task task, Node<Task> prev, Node<Task> next) {
        this.task = task;
        this.next = next;
        this.prev = prev;
    }

    @Override
    public String toString() {
        return "Node{" +
                "task=" + task +
                ", next=" + (next != null ? next.task : null) +
                ", prev=" + (prev != null ? prev.task : null) +
                '}';
    }
}


