import java.time.LocalDate;
import java.util.Objects;

public class Task {
    private String nameOfTask;
    private String descriptionOfTask;
    private LocalDate dateDeadLine;
    private TaskStatus status;

    public Task(String nameOfTask, String descriptionOfTask, LocalDate dateDeadLine) {
        this.nameOfTask = nameOfTask;
        this.descriptionOfTask = descriptionOfTask;
        this.dateDeadLine = dateDeadLine;
        this.status = TaskStatus.TODO;
    }

    public String getNameOfTask() {
        return nameOfTask;
    }

    public void setNameOfTask(String nameOfTask) {
        this.nameOfTask = nameOfTask;
    }

    public String getDescriptionOfTask() {
        return descriptionOfTask;
    }

    public void setDescriptionOfTask(String descriptionOfTask) {
        this.descriptionOfTask = descriptionOfTask;
    }

    public LocalDate getDateDeadLine() {
        return dateDeadLine;
    }

    public void setDateDeadLine(LocalDate dateDeadLine) {
        this.dateDeadLine = dateDeadLine;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(nameOfTask, task.nameOfTask) && Objects.equals(descriptionOfTask, task.descriptionOfTask) && Objects.equals(dateDeadLine, task.dateDeadLine) && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameOfTask, descriptionOfTask, dateDeadLine, status);
    }

    @Override
    public String toString() {
        return String.format("%-20s | %-30s | %-12s | %-10s%n",
               nameOfTask, descriptionOfTask, dateDeadLine, status);
    }
}
