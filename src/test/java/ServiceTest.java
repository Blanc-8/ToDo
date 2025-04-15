import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    private Service service;

    @BeforeEach
    void setUp() {
        service = new Service();

        // Если хочешь минимальные задачи вручную:
        service.addTask("Задача 1", "Описание 1", LocalDate.of(2025, 4, 15));
        service.addTask("Задача 2", "Описание 2", LocalDate.of(2025, 4, 10));
    }

    @Test
    void addTask() {
        int initialSize = service.getAllTasks().size();
        service.addTask("Новая задача", "Описание новой", LocalDate.of(2025, 5, 1));
        assertEquals(initialSize + 1, service.getAllTasks().size(), "Размер списка должен увеличиться на 1");
    }

    @Test
    void filterTasksByStatus() {
        service.updateTaskStatus(TaskStatus.IN_PROGRESS, 1);
        service.updateTaskStatus(TaskStatus.TODO, 2);

        var filtered = service.filterTasksByStatus(TaskStatus.IN_PROGRESS);
        assertEquals(2, filtered.size(), "Должна быть найдено две задача со статусом IN_PROGRESS");
    }

    @Test
    void sortTasksByStatus() {
        List<Map.Entry<Integer, Task>> sorted = service.sortTasksByStatus();
        assertFalse(sorted.isEmpty(), "Список не должен быть пустым");

        for (int i = 0; i < sorted.size() - 1; i++) {
            TaskStatus current = sorted.get(i).getValue().getStatus();
            TaskStatus next = sorted.get(i + 1).getValue().getStatus();
            assertTrue(current.ordinal() <= next.ordinal(), "Задачи должны быть отсортированы по статусу");
        }
    }

    @Test
    void sortTasksByDeadline() {
        List<Map.Entry<Integer, Task>> sorted = service.sortTasksByDeadline();
        assertFalse(sorted.isEmpty(), "Список не должен быть пустым");

        for (int i = 0; i < sorted.size() - 1; i++) {
            LocalDate current = sorted.get(i).getValue().getDateDeadLine();
            LocalDate next = sorted.get(i + 1).getValue().getDateDeadLine();
            assertTrue(current.isBefore(next) || current.isEqual(next), "Задачи должны быть отсортированы по дедлайну");
        }
    }

    @Test
    void isTaskListEmpty() {
        assertFalse(service.isTaskListEmpty(), "Список не должен быть пустым после добавления задач");
    }

    @Test
    void deleteTask() {
        int idToDelete = 1;
        assertTrue(service.doesTaskExist(idToDelete), "Задача с ID " + idToDelete + " должна существовать до удаления");

        service.deleteTask(idToDelete);

        assertFalse(service.doesTaskExist(idToDelete), "Задача с ID " + idToDelete + " должна быть удалена");
    }

    @Test
    void filterTodoTasks() {
        List<Map.Entry<Integer, Task>> filtered = service.filterTasksByStatus(TaskStatus.TODO);
        assertEquals(4, filtered.size(), "Должно быть найдено четыре задачи со статусом TODO");
    }

    @Test
    void filterDoneTasks() {
        List<Map.Entry<Integer, Task>> filtered = service.filterTasksByStatus(TaskStatus.DONE);
        assertEquals(3, filtered.size(), "Должно быть найдено три задачи со статусом DONE");
    }

    @Test
    void filterNonExistentStatus() {
        service.deleteTask(3); // удалим задачу со статусом DONE
        service.deleteTask(6); // еще одну DONE
        service.deleteTask(7); // и третью DONE
        List<Map.Entry<Integer, Task>> filtered = service.filterTasksByStatus(TaskStatus.DONE);
        assertEquals(0, filtered.size(), "Должно быть 0 задач со статусом DONE после удаления всех");
    }
}