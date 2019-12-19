package ru.itpark.model;

import lombok.Data;
import ru.itpark.enums.TaskStatus;

@Data
public class Task {
    private int id;
    private String phrase;
    private TaskStatus status;
    private String sessionId;
}
