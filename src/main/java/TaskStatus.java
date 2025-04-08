public enum TaskStatus {
    TODO,
    IN_PROGRESS,
    DONE;

    public static TaskStatus returnStatusFromId(int id) {
        return switch (id) {
            case 1 -> TODO;
            case 2 -> IN_PROGRESS;
            case 3 -> DONE;
            default -> throw new IllegalArgumentException("Некорректный ID статуса: " + id);
        };
    }
}
