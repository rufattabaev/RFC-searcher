package ru.itpark.service;

import ru.itpark.enums.TaskStatus;
import ru.itpark.workers.TaskWorker;
import ru.itpark.repository.TaskRepository;
import ru.itpark.model.Task;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskService {
    private TaskRepository taskRepository;

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;

    }

    public Task createTask(String phrase, String sessionId) {
        Task task = new Task();
        task.setPhrase(phrase);
        task.setSessionId(sessionId);
        task.setStatus(TaskStatus.WAITING);
        taskRepository.createTask(task);
        return task;
    }

    public List<Task> getListTaskWithStatusOneAndSetStatusTwo(TaskStatus status, TaskStatus newStatus) {
        return taskRepository.getTasksByStatusOneAndSetStatusTwo(status, newStatus);

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

    public List<Task> checkoutDataBase() {
        return taskRepository.checkoutDataBase();
    }
}
