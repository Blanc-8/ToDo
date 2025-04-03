import java.time.LocalDate;
import java.util.Comparator;
import java.util.Map;

public class Service {

    Repository repository = new Repository();
    private int countOfTask = 0;

    // Демо данные для ручного тестирования
    public Service() {
        addTask("Починить баг", "Исправить ошибку на странице входа", LocalDate.of(2025, 4, 5));
        addTask("Созвон с командой", "Планёрка по текущим задачам", LocalDate.of(2025, 4, 3));
        addTask("Обновить документацию", "Добавить инструкции по установке", LocalDate.of(2025, 4, 10));
        addTask("Рефакторинг", "Упростить метод фильтрации", LocalDate.of(2025, 4, 7));
        addTask("Написать тесты", "Покрыть add и edit юнит-тестами", LocalDate.of(2025, 4, 15));
        addTask("Протестировать UI", "Проверка отображения задач в списке", LocalDate.of(2025, 4, 12));
        addTask("Сделать бэкап", "Создать резервную копию проекта", LocalDate.of(2025, 4, 9));

        newStatusOfTask(TaskStatus.IN_PROGRESS, 1);
        newStatusOfTask(TaskStatus.TODO, 2);
        newStatusOfTask(TaskStatus.DONE, 3);
        newStatusOfTask(TaskStatus.IN_PROGRESS, 4);
        newStatusOfTask(TaskStatus.TODO, 5);
        newStatusOfTask(TaskStatus.DONE, 6);
        newStatusOfTask(TaskStatus.DONE, 7);
    }



    public void addTask(String nameOfTask, String descriptionOfTask, LocalDate dateOfDeadLine) {
        Task task = new Task(nameOfTask, descriptionOfTask, dateOfDeadLine);
        countOfTask++;
        repository.getMapOfTask().put(countOfTask, task);
    }

    public boolean filterTaskByStatus (TaskStatus status) {
        return repository.getMapOfTask().values().stream()
                .filter(task -> task.getStatus() == status)
                .peek(System.out::println)
                .count() > 0;
    }

    public void sortByStatus() {
        repository.getMapOfTask().values().stream()
                .sorted(Comparator.comparing(Task::getStatus))
                .forEach(System.out::println);
    }

    public void sortByDeadLine() {
        repository.getMapOfTask().values().stream()
                .sorted(Comparator.comparing(Task::getDateDeadLine))
                .forEach(System.out::println);
    }

    public boolean listIsEmpty() {
        return repository.getMapOfTask().isEmpty();
    }

    public Map<Integer, Task> listTask() {
        return repository.getMapOfTask();
    }

    public boolean idExist(int id) {
        return repository.getMapOfTask().containsKey(id);
    }

    public String nameOfTask(int id) {
        return repository.getMapOfTask().get(id).getNameOfTask();
    }

    public void newNameOfTask(String newName, int id) {
       repository.getMapOfTask().get(id).setNameOfTask(newName);
    }

    public String descriptionOfTask (int id) {
        return repository.getMapOfTask().get(id).getDescriptionOfTask();
    }

    public void newDescriptionOfTask(String newDescription, int id) {
        repository.getMapOfTask().get(id).setDescriptionOfTask(newDescription);
    }

    public LocalDate dateOfDeadLineTask (int id) {
        return repository.getMapOfTask().get(id).getDateDeadLine();
    }

    public void newDateOfDeadLineTask(LocalDate newDateOfDeadLine, int id) {
        repository.getMapOfTask().get(id).setDateDeadLine(newDateOfDeadLine);
    }

    public TaskStatus statusOfTask (int id) {
        return repository.getMapOfTask().get(id).getStatus();
    }

    public void newStatusOfTask(TaskStatus newStatusOfTask, int id) {
        repository.getMapOfTask().get(id).setStatus(newStatusOfTask);
    }

    public void deleteTask (int id) {
        repository.getMapOfTask().remove(id);
    }

}
