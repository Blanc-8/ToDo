import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode

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

    @Override
    public String toString() {
        return String.format("%-20s | %-30s | %-12s | %-10s%n",
               nameOfTask, descriptionOfTask, dateDeadLine, status);
    }
}
