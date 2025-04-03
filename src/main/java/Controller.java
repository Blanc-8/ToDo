import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;

public class Controller {
    Scanner scanner = new Scanner(System.in);
    Service service = new Service();

    /*
    Controller - общение с пользователем, вся логика с scanner и sout
    Service - основная бизнес-логика, фильтрации, сортировки и тп
    Repository - хранение данных, в нашем случае в коллекции

    Service принимает запрос от Controller, отправляет запрос Repository, получает ответ от Repository, отправляет ответ Contoller
    Contoller и Repository не общаются напрямую и не используют методы друг друга!


    //1 получить корректную строку из сканера
// Тесты только на сервис
                    // Задачки по дефолту в репрозиторий добавить
     */
    public void execute() {

        boolean isRunning = true;
        System.out.println("ToDo запущен. Введите help для вызова справки или введите нужную команду. ");

        while (isRunning) {
            System.out.println("Ваша команда: ");
            String command = scanner.nextLine();

            switch (command) {

                case "add" -> {
                    System.out.println("Введите название задачи: ");
                    String nameOfTask = addNameOfTask(scanner.nextLine());

                    System.out.println("Введите описание задачи: ");
                    String descriptionOfTask = addDescriptionOfTask(scanner.nextLine());

                    System.out.println("Введите дату дедлайна в формате dd.mm.yyyy");
                    LocalDate dateOfDeadLine = addDateOfDeadLine(scanner.nextLine());

                    service.addTask(nameOfTask, descriptionOfTask, dateOfDeadLine);
                    System.out.println("Задача успешно добавлена!");
                }

                case "list" -> {
                    if (service.listIsEmpty()) {
                        System.out.println("Задач пока нет, создайте новую!");
                    } else {
                        System.out.printf("%-5s | %-20s | %-30s | %-12s | %-10s%n",
                                "ID", "Название", "Описание", "Дата", "Статус");
                        System.out.println("=".repeat(85));
                        service.listTask().forEach((id, task) -> System.out.printf("%-5s | %-20s | %-30s | %-12s | %-10s%n",
                                id, task.getNameOfTask(), task.getDescriptionOfTask(), task.getDateDeadLine(), task.getStatus()));
                    }
                }

                case "edit" -> {
                    int id = availabilityCheckID();
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
                    int id = availabilityCheckID();
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

                    Optional<TaskStatus> chosenStatus = switch (inputStatus) {
                        case "1" -> Optional.of(TaskStatus.TODO);
                        case "2" -> Optional.of(TaskStatus.IN_PROGRESS);
                        case "3" -> Optional.of(TaskStatus.DONE);
                        default -> Optional.empty();
                    };

                    if (chosenStatus.isEmpty()) {
                        System.out.println("Некорректный ввод. Фильтрация не выполнена.");
                        break;
                    } else {
                        boolean hasTask = service.filterTaskByStatus(chosenStatus.get());
                        if (!hasTask) {
                            System.out.println("Нет задач со статусом " + chosenStatus.get());
                        }
                    }
                }

                case "sort" -> {
                    if (service.listIsEmpty()) {
                        System.out.println("Задач пока нет, добавьте хотя бы одну.");
                    } else {
                        System.out.println("Выберите тип сортировки:");
                        System.out.println("1 - По сроку выполнения");
                        System.out.println("2 - По статусу");
                        System.out.print("Ваш выбор: ");

                        String input = scanner.nextLine().trim();

                        if (input.equals("1")) {
                            service.sortByDeadLine();
                        } else if (input.equals("2")) {
                            service.sortByStatus();
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

    public String addNameOfTask(String nameOfTask) {
        while (nameOfTask.trim().isEmpty()) {
            System.out.println("Некорректное название, заполните поле.");
            nameOfTask = scanner.nextLine();
        }
        return nameOfTask;
    }

    public String addDescriptionOfTask(String descriptionOfTask) {
        while (descriptionOfTask.trim().isEmpty()) {
            System.out.println("Некорректное описание, заполните поле.");
            descriptionOfTask = scanner.nextLine();
        }
        return descriptionOfTask;
    }

    public LocalDate addDateOfDeadLine(String inputDate) {
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

    public int availabilityCheckID() {
        while (true) {
            System.out.println("Введите ID задачи или 0 для выхода:");
            String input = scanner.nextLine().trim();
            if (input.equals("0")) {
                return -1;
            }
            try {
                int id = Integer.parseInt(input);
                if (service.idExist(id)) {
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
        System.out.println("Текущее название задачи: " + service.nameOfTask(id) + ". Новое название: ");
        String newNameOfTask = scanner.nextLine().trim();
        service.newNameOfTask(addNameOfTask(newNameOfTask), id);
        System.out.println("Вы изменили название задачи на: " + service.nameOfTask(id));
    }

    public void setNewDescriptionOfTask(int id) {
        System.out.println("Текущее описание задачи: " + service.descriptionOfTask(id) + ". Новое описание: ");
        String newDescriptionOfTask = scanner.nextLine().trim();
        service.newDescriptionOfTask(addDescriptionOfTask(newDescriptionOfTask), id);
        System.out.println("Вы изменили описание задачи на: " + service.descriptionOfTask(id));
    }

    public void setNewDateOfDeadLineTask(int id) {
        System.out.println("Текущее дата дэдлайна: " + service.dateOfDeadLineTask(id) + ". Новая дата: ");
        String newDateOfDeadLine = scanner.nextLine().trim();
        service.newDateOfDeadLineTask(addDateOfDeadLine(newDateOfDeadLine), id);
        System.out.println("Вы изменили дату на: " + service.dateOfDeadLineTask(id));
    }

    public void setNewStatusTask(int id) {
        System.out.println("Текущий статус: " + service.statusOfTask(id) + ". Выберите новый статус: ");
        System.out.println("1 - TODO");
        System.out.println("2 - IN_PROGRESS");
        System.out.println("3 - DONE");

        TaskStatus newStatus = null;

        while (newStatus == null) {
            int choice = Integer.parseInt(scanner.nextLine().trim());

            try {
                switch (choice) {
                    case 1 -> {
                        newStatus = TaskStatus.TODO;
                        System.out.println("Статус изменён на TODO");
                    }
                    case 2 -> {
                        newStatus = TaskStatus.IN_PROGRESS;
                        System.out.println("Статус изменён на IN_PROGRESS");
                    }
                    case 3 -> {
                        newStatus = TaskStatus.DONE;
                        System.out.println("Статус изменён на DONE");
                    }
                    default -> System.out.println("Некорректный ввод. Статус не изменён.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод. Статус не изменён.");
            }
            if (newStatus != null) {
                service.newStatusOfTask(newStatus, id);
            }
        }
    }


}
