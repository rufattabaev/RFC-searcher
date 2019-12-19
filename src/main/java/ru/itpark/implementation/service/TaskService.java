package ru.itpark.implementation.service;

import ru.itpark.enums.TaskStatus;
import ru.itpark.framework.workers.TaskWorker;
import ru.itpark.implementation.repository.TaskRepository;
import ru.itpark.model.Task;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskService {
    TaskRepository taskRepository;

    ExecutorService executorService = Executors.newFixedThreadPool(10);

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;

    }

    public void createTask(String phrase, String sessionId) {
        Task task = new Task();
        task.setPhrase(phrase);
        task.setSessionId(sessionId);
        task.setStatus(TaskStatus.WAITING);
        taskRepository.createTask(task);
    }

    public List<Task> getListTaskWithStatusOneAndSetStatusTwo(TaskStatus status, TaskStatus newStatus) {
        return taskRepository.getTasksByStatusOneAndSetStatusTwo(status, newStatus);
//        taskRepository.updateTask();
    }

    public void pushWork() {
        List<Task> tasksToWork = getListTaskWithStatusOneAndSetStatusTwo(TaskStatus.WAITING, TaskStatus.RUNNING);

        for (Task task : tasksToWork) {
            executorService.submit(new TaskWorker(task));
        }


    }

    public void updateTask(Task task) {
        taskRepository.updateTask(task);

    }
}
