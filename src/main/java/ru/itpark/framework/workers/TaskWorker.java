package ru.itpark.framework.workers;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.itpark.enums.TaskStatus;
import ru.itpark.implementation.repository.TaskRepository;
import ru.itpark.implementation.service.FileServiceImpl;
import ru.itpark.implementation.service.TaskService;
import ru.itpark.model.Task;
import ru.itpark.model.TaskResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TaskWorker extends Thread {
    private FileServiceImpl fileService;
    private Task task;
    private TaskService taskService = new TaskService(new TaskRepository());

    public TaskWorker(Task task) {
        try {
            fileService = new FileServiceImpl();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.task = task;
    }

    @Override
    public void run() {

        System.out.println("Start working ..." + task.getPhrase());
        try {
            TaskResult taskResult = fileService.searchFilesByPhrase(task.getPhrase());
//TODO записать в файл
            ObjectMapper objectMapper = new ObjectMapper();
            Path path = Paths.get("D:\\projects\\tasksexample-developer\\files\\" + task.getSessionId());
            Path directory = null;
            if (!Files.exists(path)) {
                directory = Files.createDirectory(path);
            }


            objectMapper.writeValue(new File(path.toString() + "\\" + task.getId()), taskResult);
//            System.out.println(taskResult.getQuery());
            task.setStatus(TaskStatus.COMPLETED);
            taskService.updateTask(task);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}


