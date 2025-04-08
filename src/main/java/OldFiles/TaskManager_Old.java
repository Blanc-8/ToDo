package OldFiles;

public class TaskManager_Old {

//    private String nameOfTask;
//    private String descriptionOfTask;
//
//    Scanner scanner = new Scanner(System.in);
//    private final Map<Integer, Task> mapOfTask = new HashMap<>();
//
//    private int countOfTask = 0;
//
//    public void add() {
//        LocalDate dateDeadLine = null;
//
//        System.out.println("Введите название задачи: ");
//        nameOfTask = scanner.nextLine();
//
//        while (nameOfTask.trim().isEmpty()) {
//            System.out.println("Некорректное название, заполните поле.");
//            nameOfTask = scanner.nextLine();
//        }
//
//        System.out.println("Введите описание задачи: ");
//        descriptionOfTask = scanner.nextLine();
//
//        while (descriptionOfTask.trim().isEmpty()) {
//            System.out.println("Некорректное описание, заполните поле.");
//            descriptionOfTask = scanner.nextLine();
//        }
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//
//        while (dateDeadLine == null) {
//            System.out.println("Введите дату дедлайна в формате dd.mm.yyyy");
//            String input = scanner.nextLine();
//
//            try {
//                dateDeadLine = LocalDate.parse(input, formatter);
//            } catch (DateTimeParseException e) {
//                System.out.println("Некорректный формат даты! Попробуйте снова.");
//            }
//        }
//        Task task = new Task(nameOfTask, descriptionOfTask, dateDeadLine);
//        countOfTask++;
//        mapOfTask.put(countOfTask, task);
//        System.out.println("Задача успешно добавлена!");
//
//    }
//
//    //Редактировать задачу: изменить название, описание, срок выполнения, статус
//    public void edit() {
//        System.out.println("Введите ID задачи, которую хотите изменить: ");
////        int id = scanner.nextInt();
//        String idInput = scanner.nextLine();
//        int id = Integer.parseInt(idInput);
//
//
//        while (mapOfTask.get(id) == null) {
//            System.out.println("Такой задачи не существует, попробуйте ещё раз.");
//            idInput = scanner.nextLine();
//            id = Integer.parseInt(idInput);
//        }
////        "Проглатываем" Enter после nextInt()
////        scanner.nextLine();
//        System.out.println("Введите изменение в том поле, которое хотите изменить. Поля оставленные пустыми, меняться не будут");
//
//        System.out.println("Текущее название задачи: " + mapOfTask.get(id).getNameOfTask() + ". Новое название: ");
//        nameOfTask = scanner.nextLine();
//        if (!nameOfTask.trim().isEmpty()) {
//            mapOfTask.get(id).setNameOfTask(nameOfTask);
//        }
//
//        System.out.println("Текущее описание задачи: " + mapOfTask.get(id).getDescriptionOfTask() + ". Новое описание: ");
//        descriptionOfTask = scanner.nextLine();
//        if (!descriptionOfTask.trim().isEmpty()) {
//            mapOfTask.get(id).setDescriptionOfTask(descriptionOfTask);
//        }
//
//        System.out.println("Текущий дедлайн: " + mapOfTask.get(id).getDateDeadLine() + ". Новая дата в формате dd.mm.yyyy: ");
//        String inputDate = scanner.nextLine();
//        while (!inputDate.trim().isEmpty()) {
//            try {
//                LocalDate newDeadLine = LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
//                mapOfTask.get(id).setDateDeadLine(newDeadLine);
//                break;
//            } catch (DateTimeParseException e) {
//                System.out.println("Неправильный формат даты. Попробуйте снова или оставьте поле пустым.");
//            }
//            inputDate = scanner.nextLine();
//        }
//
//        System.out.println("Выберите новый статус:");
//        System.out.println("1 - TODO");
//        System.out.println("2 - IN_PROGRESS");
//        System.out.println("3 - DONE");
//
//        String inputStatus;
//        TaskStatus newStatus = null;
//
//        while (newStatus == null) {
//            System.out.print("Ваш выбор: ");
//            inputStatus = scanner.nextLine().trim();
//
//            if (inputStatus.isEmpty()) {
//                break;
//            }
//            try {
//                int choice = Integer.parseInt(inputStatus);
//
//
//                switch (choice) {
//                    case 1 -> {
//                        newStatus = TaskStatus.TODO;
//                        System.out.println("Статус изменён на TODO");
//                    }
//                    case 2 -> {
//                        newStatus = TaskStatus.IN_PROGRESS;
//                        System.out.println("Статус изменён на IN_PROGRESS");
//                    }
//                    case 3 -> {
//                        newStatus = TaskStatus.DONE;
//                        System.out.println("Статус изменён на DONE");
//                    }
//                    default -> System.out.println("Некорректный ввод. Статус не изменён.");
//                }
//
//
//            } catch (NumberFormatException e) {
//                System.out.println("Некорректный ввод. Статус не изменён.");
//            }
//            if (newStatus != null) {
//                mapOfTask.get(id).setStatus(newStatus);
//            }
//        }
//        System.out.println("Вы закончили редактирование задачи.");
//    }
//
//    // Вывод списка задач в формате ID / Название / Описание / Дата / Статус
//    public void list() {
//        if (mapOfTask.isEmpty()) {
//            System.out.println("Задач пока нет, создайте новую!");
//            return;
//        }
//        System.out.printf("%-5s | %-20s | %-30s | %-12s | %-10s%n",
//                "ID", "Название", "Описание", "Дата", "Статус");
//        System.out.println("=".repeat(85));
//
//        mapOfTask.forEach((id, task) -> System.out.printf("%-5s | %-20s | %-30s | %-12s | %-10s%n",
//                id, task.getNameOfTask(), task.getDescriptionOfTask(), task.getDateDeadLine(), task.getStatus()));
//    }
//
//    public void delete() {
//        System.out.println("Введите ID задачи, которую хотите удалить: ");
//        String idInput = scanner.nextLine();
//        int id = Integer.parseInt(idInput);
//
//        if (mapOfTask.containsKey(id)) {
//            mapOfTask.remove(id);
//            System.out.println("Задача с ID " + id + " удалена.");
//        } else {
//            System.out.println("Задача с таким ID не найдена, попробуйте ещё раз: ");
//        }
//    }
//
//    public void filter() {
//        System.out.println("Выберите статус для фильтрации:");
//        System.out.println("1 - TODO");
//        System.out.println("2 - IN_PROGRESS");
//        System.out.println("3 - DONE");
//        System.out.print("Ваш выбор: ");
//
//        String inputStatus = scanner.nextLine().trim();
//
//        Optional<TaskStatus> chosenStatus = switch (inputStatus) {
//            case "1" -> Optional.of(TaskStatus.TODO);
//            case "2" -> Optional.of(TaskStatus.IN_PROGRESS);
//            case "3" -> Optional.of(TaskStatus.DONE);
//            default -> Optional.empty();
//        };
//
//        if (chosenStatus.isEmpty()) {
//            System.out.println("Некорректный ввод. Фильтрация не выполнена.");
//            return;
//        }
//
//        boolean hasTask = mapOfTask.values().stream()
//                .filter(task -> task.getStatus() == chosenStatus.get())
//                .peek(System.out::println)
//                .findAny()
//                .isPresent();
//
//        if (!hasTask) {
//            System.out.println("Нет задач со статусом " + chosenStatus.get());
//        }
//
//    }
//
//    // Сортировать задачи по сроку выполнения или статусу. Тут через перегрузку метода вижу смысл реализовывать
//    public void sort(TaskStatus status) {
//        mapOfTask.values().stream()
//                .sorted(Comparator.comparing(Task::getStatus))
//                .forEach(System.out::println);
//    }
//
//    public void sort(LocalDate date) {
//        mapOfTask.values().stream()
//                .sorted(Comparator.comparing(Task::getDateDeadLine))
//                .forEach(System.out::println);
//    }
//
}

