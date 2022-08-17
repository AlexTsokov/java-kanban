public class Main {

    public static void main(String[] args) {

        Manager manager = new Manager();

        manager.createTasksList(manager.task1);
        manager.createEpicsList(manager.epic1);
        manager.createEpicsList(manager.epic2);
        manager.createEpicSubtasksList(manager.epic1, manager.subTask1);
        manager.createEpicSubtasksList(manager.epic1, manager.subTask2);
        manager.createEpicSubtasksList(manager.epic1, manager.subTask5);
        manager.createEpicSubtasksList(manager.epic2, manager.subTask3);
        manager.createEpicSubtasksList(manager.epic2, manager.subTask4);
        System.out.println(manager.task1);
        System.out.println(manager.getTasks());
        System.out.println(manager.getEpics());
        System.out.println(manager.getEpicSubTasksNames(manager.epic1));
        System.out.println(manager.getEpicSubTasksNames(manager.epic2));

    }
}