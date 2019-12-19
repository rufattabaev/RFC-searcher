package ru.itpark.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itpark.enums.TaskStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private int id;
    private String phrase;
    private TaskStatus status;
    private String sessionId;
}
