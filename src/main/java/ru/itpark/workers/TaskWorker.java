package ru.itpark.workers;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.itpark.enums.TaskStatus;
import ru.itpark.repository.TaskRepository;
import ru.itpark.service.FileService;
import ru.itpark.service.TaskService;
import ru.itpark.model.Task;
import ru.itpark.model.TaskResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TaskWorker extends Thread {
    private FileService fileService;
    private Task task;
    private TaskService taskService = new TaskService(new TaskRepository());

    public TaskWorker(Task task) {
        try {
            fileService = new FileService();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.task = task;
    }

    @Override
    public void run() {

        try {
            TaskResult taskResult = fileService.searchFilesByPhrase(task.getPhrase());
            ObjectMapper objectMapper = new ObjectMapper();
            Path path = Paths.get("D:\\projects\\final-project\\files\\" + task.getSessionId());
            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }

            objectMapper.writeValue(new File(path.toString() + "\\" + task.getId()), taskResult);
            task.setStatus(TaskStatus.COMPLETED);
            taskService.updateTask(task);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}


