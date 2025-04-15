import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Controller {
    private Scanner scanner = new Scanner(System.in);
    private Service service = new Service();

    public void execute() {

        boolean isRunning = true;
        System.out.println("ToDo запущен. Введите help для вызова справки или введите нужную команду. ");

        while (isRunning) {
            System.out.println("Ваша команда: ");
            String command = scanner.nextLine();

            switch (command) {

                case "add" -> {
                    System.out.println("Введите название задачи: ");
                    String nameOfTask = readValidString("Некорректное название, заполните поле.");

                    System.out.println("Введите описание задачи: ");
                    String descriptionOfTask = readValidString("Некорректное описание, заполните поле.");

                    System.out.println("Введите дату дедлайна в формате dd.mm.yyyy");
                    LocalDate dateOfDeadLine = readValidDeadline(scanner.nextLine());

                    service.addTask(nameOfTask, descriptionOfTask, dateOfDeadLine);
                    System.out.println("Задача успешно добавлена!");
                }

                case "list" -> {
                    if (service.isTaskListEmpty()) {
                        System.out.println("Задач пока нет, создайте новую!");
                    } else {
                        System.out.printf("%-5s | %-20s | %-30s | %-12s | %-10s%n",
                                "ID", "Название", "Описание", "Дата", "Статус");
                        System.out.println("=".repeat(85));
                        service.getAllTasks().forEach((id, task) -> System.out.printf("%-5s | %-20s | %-30s | %-12s | %-10s%n",
                                id, task.getNameOfTask(), task.getDescriptionOfTask(), task.getDateDeadLine(), task.getStatus()));
                    }
                }

                case "edit" -> {
                    int id = readValidTaskIdOrExit();
                    if (id == -1) break;

                    System.out.println("Выберите пункт для изменений:");
                    System.out.println("1 - Название задачи");
                    System.out.println("2 - Описание задачи");
                    System.out.println("3 - Дата дэдлайна");
                    System.out.println("4 - Статус задачи");
                    System.out.println("0 - Выйти из меню редактирования без изменений.");
                    System.out.print("Ваш выбор: ");

                    String input = scanner.nextLine().trim();

                    switch (input) {
                        case "1" -> setNewNameOfTask(id);
                        case "2" -> setNewDescriptionOfTask(id);
                        case "3" -> setNewDateOfDeadLineTask(id);
                        case "4" -> setNewStatusTask(id);
                        case "0" -> {
                            System.out.println("Вы вышли из меню редактирования.");
                            break;
                        }
                        default -> System.out.println("Некорректный ввод. Попробуйте ещё раз.");
                    }
                }

                case "delete" -> {
                    int id = readValidTaskIdOrExit();
                    if (id == -1) break;
                    service.deleteTask(id);
                    System.out.println("Задача с ID " + id + " удалена.");
                }

                case "filter" -> {
                    System.out.println("Выберите статус для фильтрации:");
                    System.out.println("1 - TODO");
                    System.out.println("2 - IN_PROGRESS");
                    System.out.println("3 - DONE");
                    System.out.print("Ваш выбор: ");

                    String inputStatus = scanner.nextLine().trim();
                    try {
                        int statusId = Integer.parseInt(inputStatus);
                        TaskStatus chosenStatus = TaskStatus.returnStatusFromId(statusId);

                        List<Map.Entry<Integer, Task>> filteredTasks = service.filterTasksByStatus(chosenStatus);
                        if (filteredTasks.isEmpty()) {
                            System.out.println("Нет задач со статусом " + chosenStatus);
                        } else {
                            printFormattedTaskList(filteredTasks);
                        }
                    } catch (Exception e) {
                        System.out.println("Некорректный ввод. Фильтрация не выполнена.");
                    }
                }

                case "sort" -> {
                    if (service.isTaskListEmpty()) {
                        System.out.println("Задач пока нет, добавьте хотя бы одну.");
                    } else {
                        System.out.println("Выберите тип сортировки:");
                        System.out.println("1 - По сроку выполнения");
                        System.out.println("2 - По статусу");
                        System.out.print("Ваш выбор: ");

                        String input = scanner.nextLine().trim();

                        List<Map.Entry<Integer, Task>> sortedTasks;
                        if (input.equals("1")) {
                            sortedTasks = service.sortTasksByDeadline();
                            printFormattedTaskList(sortedTasks);
                        } else if (input.equals("2")) {
                            sortedTasks = service.sortTasksByStatus();
                            printFormattedTaskList(sortedTasks);
                        } else {
                            System.out.println("Некорректный ввод, сортировка не выполнена.");
                        }
                    }
                }

                case "exit" -> {
                    System.out.println("Завершение работы программы...");
                    isRunning = false;
                }

                case "help" -> {
                    System.out.println("Доступные команды:");
                    System.out.println("add     – добавить задачу");
                    System.out.println("list    – вывести список задач");
                    System.out.println("edit    – редактировать задачу");
                    System.out.println("delete  – удалить задачу");
                    System.out.println("filter  – отфильтровать задачи по статусу");
                    System.out.println("sort    – отсортировать задачи по сроку или статусу");
                    System.out.println("exit    – завершить работу программы");
                }
                default ->
                        System.out.println("Некорректный ввод. Изучите доступные команды введя \"help\" и повторите ввод: ");
            }
        }
    }

    public String readValidString(String errorMessage) {
        String input = scanner.nextLine();
        while (input.isBlank()) {
            System.out.println(errorMessage);
            input = scanner.nextLine();
        }
        return input;
    }

    public LocalDate readValidDeadline(String inputDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        while (true) {
            try {
                return LocalDate.parse(inputDate, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Некорректный формат даты! Попробуйте снова:");
                inputDate = scanner.nextLine();
            }
        }
    }

    public int readValidTaskIdOrExit() {
        while (true) {
            System.out.println("Введите ID задачи или 0 для выхода:");
            String input = scanner.nextLine().trim();
            if (input.equals("0")) {
                return -1;
            }
            try {
                int id = Integer.parseInt(input);
                if (service.doesTaskExist(id)) {
                    return id;
                } else {
                    System.out.println("Такой задачи не существует, попробуйте ещё раз.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Введите корректный числовой ID.");
            }
        }
    }

    public void setNewNameOfTask(int id) {
        System.out.println("Текущее название задачи: " + service.getTaskName(id) + ". Новое название: ");
        String name = readValidString("Название не может быть пустым");
        service.updateTaskName(name, id);
        System.out.println("Вы изменили название задачи на: " + service.getTaskName(id));
    }

    public void setNewDescriptionOfTask(int id) {
        System.out.println("Текущее описание задачи: " + service.getTaskDescription(id) + ". Новое описание: ");
        String description = readValidString("Описание не может быть пустым");
        service.updateTaskDescription(description, id);
        System.out.println("Вы изменили описание задачи на: " + service.getTaskDescription(id));
    }

    public void setNewDateOfDeadLineTask(int id) {
        System.out.println("Текущее дата дэдлайна: " + service.getTaskDeadline(id) + ". Новая дата: ");
        String newDateOfDeadLine = scanner.nextLine().trim();
        service.updateTaskDeadline(readValidDeadline(newDateOfDeadLine), id);
        System.out.println("Вы изменили дату на: " + service.getTaskDeadline(id));
    }

    public void setNewStatusTask(int id) {
        System.out.println("Текущий статус: " + service.getTaskStatus(id) + ". Выберите новый статус: ");
        System.out.println("1 - TODO");
        System.out.println("2 - IN_PROGRESS");
        System.out.println("3 - DONE");

        try {
            int statusId = Integer.parseInt(scanner.nextLine().trim());
            TaskStatus newStatus = TaskStatus.returnStatusFromId(statusId);
            service.updateTaskStatus(newStatus, id);
            System.out.println("Статус изменён на " + newStatus);
        } catch (NumberFormatException e) {
            System.out.println("Некорректный ввод. Статус не изменён.");
        }
    }

    private void printFormattedTaskList(List<Map.Entry<Integer, Task>> tasks) {
        System.out.printf("%-5s | %-20s | %-30s | %-12s | %-10s%n",
                "ID", "Название", "Описание", "Дата", "Статус");
        System.out.println("=".repeat(85));

        for (Map.Entry<Integer, Task> entry : tasks) {
            Integer id = entry.getKey();
            Task task = entry.getValue();

            System.out.printf("%-5s | %-20s | %-30s | %-12s | %-10s%n",
                    id, task.getNameOfTask(), task.getDescriptionOfTask(), task.getDateDeadLine(), task.getStatus());
        }

    }
}
