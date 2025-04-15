import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Service {

    Repository repository = new Repository();

    // Демо данные для ручного тестирования
    public Service() {
        addTask("Починить баг", "Исправить ошибку на странице входа", LocalDate.of(2025, 4, 5));
        addTask("Созвон с командой", "Планёрка по текущим задачам", LocalDate.of(2025, 4, 3));
        addTask("Обновить документацию", "Добавить инструкции по установке", LocalDate.of(2025, 4, 10));
        addTask("Рефакторинг", "Упростить метод фильтрации", LocalDate.of(2025, 4, 7));
        addTask("Написать тесты", "Покрыть add и edit юнит-тестами", LocalDate.of(2025, 4, 15));
        addTask("Протестировать UI", "Проверка отображения задач в списке", LocalDate.of(2025, 4, 12));
        addTask("Сделать бэкап", "Создать резервную копию проекта", LocalDate.of(2025, 4, 9));

        updateTaskStatus(TaskStatus.IN_PROGRESS, 1);
        updateTaskStatus(TaskStatus.TODO, 2);
        updateTaskStatus(TaskStatus.DONE, 3);
        updateTaskStatus(TaskStatus.IN_PROGRESS, 4);
        updateTaskStatus(TaskStatus.TODO, 5);
        updateTaskStatus(TaskStatus.DONE, 6);
        updateTaskStatus(TaskStatus.DONE, 7);
    }

    public void addTask(String nameOfTask, String descriptionOfTask, LocalDate dateOfDeadLine) {
                repository.addTaskInMemory(nameOfTask, descriptionOfTask, dateOfDeadLine);
    }

    public List<Map.Entry<Integer,Task>> filterTasksByStatus(TaskStatus status) {
        return repository.filterTasksByStatusAtRepository(status);
    }

    public List<Map.Entry<Integer,Task>> sortTasksByStatus() {
        return repository.sortTasksByStatusAtRepository();
    }

    public List<Map.Entry<Integer,Task>> sortTasksByDeadline() {
        return repository.sortTasksByDeadlineAtRepository();
    }

    public boolean isTaskListEmpty() {
        return repository.isTaskListEmptyAtRepository();
    }

    public Map<Integer, Task> getAllTasks() {
        return repository.getAllTasksAtRepository();
    }

    public boolean doesTaskExist(int id) {
        return repository.doesTaskExistAtRepository(id);
    }

    public String getTaskName(int id) {
        return repository.getTaskNameAtRepository(id);
    }

    public void updateTaskName(String newName, int id) {
       repository.updateTaskNameAtRepository(newName, id);
    }

    public String getTaskDescription(int id) {
        return repository.getTaskDescriptionAtRepository(id);
    }

    public void updateTaskDescription(String newDescription, int id) {
        repository.updateTaskDescriptionAtRepository(newDescription, id);
    }

    public LocalDate getTaskDeadline(int id) {
        return repository.getTaskDeadlineAtRepository(id);
    }

    public void updateTaskDeadline(LocalDate newDateOfDeadLine, int id) {
        repository.updateTaskDeadlineAtRepository(newDateOfDeadLine, id);
    }

    public TaskStatus getTaskStatus(int id) {
        return repository.getTaskStatusAtRepository(id);
    }

    public void updateTaskStatus(TaskStatus newStatusOfTask, int id) {
        repository.updateTaskStatusAtRepository(newStatusOfTask, id);
    }

    public void deleteTask(int id) {
        repository.deleteTaskAtRepository(id);
    }

}
