import java.util.HashMap;
import java.util.Map;

public class Repository {
        /*
    Controller - общение с пользователем, вся логика с scanner и sout
    Service - основная бизнес-логика, фильтрации, сортировки и тп
    Repository - хранение данных, в нашем случае в коллекции

    Service принимает запрос от Controller, отправляет запрос Repository, получает ответ от Repository, отправляет ответ Contoller
    Contoller и Repository не общаются напрямую и не используют методы друг друга!
     */
        private Map<Integer, Task> mapOfTask = new HashMap<>();

    public Map<Integer, Task> getMapOfTask() {
        return mapOfTask;
    }

    public void setMapOfTask(Map<Integer, Task> mapOfTask) {
        this.mapOfTask = mapOfTask;
    }

}
