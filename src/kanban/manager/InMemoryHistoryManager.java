package kanban.manager;

import java.util.ArrayList;
import java.util.List;

import kanban.tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private final Map<Integer, Node<Task>> nodeMap = new HashMap<>();
    private final LinkedList<Node<Task>> customLinkedList = new LinkedList<>();
    private Node<Task> last;
    private Node<Task> first;

    private void linkedLast(Task task) {
        final Node<Task> newNode = new Node<>(task, last, null);
        if (first == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
    }

    private void removeNode(Node<Task> node) {
        while (customLinkedList.contains(node)) {
            customLinkedList.remove(node);
        }
    }

    private List<Task> getTasks() {
        List<Task> list = new ArrayList<>();
        for (Node<Task> node : customLinkedList) {
            list.add(node.task);
        }
        return list;
    }

    @Override
    public void add(Task task) {
        removeNode(nodeMap.get(task.getId()));
        linkedLast(task);
        customLinkedList.add(last);
        nodeMap.put(task.getId(), last);
    }

    @Override
    public void remove(int id) {
        removeNode(nodeMap.get(id));
        nodeMap.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    public List<Integer> getHistoryIds() {
        List<Integer> ids = new ArrayList<>();
        for (Task task : getTasks()) {
            ids.add(task.getId());
        }
        return ids;
    }

}