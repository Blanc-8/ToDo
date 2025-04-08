import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Repository {

    private Map<Integer, Task> mapOfTask = new HashMap<>();
    private int countOfTask = 0;

    public void addTaskInMemory(String nameOfTask, String descriptionOfTask, LocalDate dateOfDeadLine) {
        Task task = new Task(nameOfTask, descriptionOfTask, dateOfDeadLine);
        countOfTask++;
        mapOfTask.put(countOfTask, task);
    }

    public boolean filterTasksByStatusAtRepository(TaskStatus status) {
        return mapOfTask.values().stream()
                .filter(task -> task.getStatus() == status)
                .peek(System.out::println)
                .count() > 0;
    }

    public void sortTasksByStatusAtRepository() {
        mapOfTask.values().stream()
                .sorted(Comparator.comparing(Task::getStatus))
                .forEach(System.out::println);
    }

    public void sortTasksByDeadlineAtRepository() {
        mapOfTask.values().stream()
                .sorted(Comparator.comparing(Task::getDateDeadLine))
                .forEach(System.out::println);
    }

    public boolean isTaskListEmptyAtRepository() {
        return mapOfTask.isEmpty();
    }

    public Map<Integer, Task> getAllTasksAtRepository() {
        return mapOfTask;
    }

    public boolean doesTaskExistAtRepository(int id) {
        return mapOfTask.containsKey(id);
    }

    public String getTaskNameAtRepository(int id) {
        return mapOfTask.get(id).getNameOfTask();
    }

    public void updateTaskNameAtRepository(String newName, int id) {
        mapOfTask.get(id).setNameOfTask(newName);
    }

    public String getTaskDescriptionAtRepository(int id) {
        return mapOfTask.get(id).getDescriptionOfTask();
    }

    public void updateTaskDescriptionAtRepository(String newDescription, int id) {
        mapOfTask.get(id).setDescriptionOfTask(newDescription);
    }

    public LocalDate getTaskDeadlineAtRepository(int id) {
        return mapOfTask.get(id).getDateDeadLine();
    }

    public void updateTaskDeadlineAtRepository(LocalDate newDateOfDeadLine, int id) {
        mapOfTask.get(id).setDateDeadLine(newDateOfDeadLine);
    }

    public TaskStatus getTaskStatusAtRepository(int id) {
        return mapOfTask.get(id).getStatus();
    }

    public void updateTaskStatusAtRepository(TaskStatus newStatusOfTask, int id) {
        mapOfTask.get(id).setStatus(newStatusOfTask);
    }

    public void deleteTaskAtRepository(int id) {
        mapOfTask.remove(id);
    }
}
