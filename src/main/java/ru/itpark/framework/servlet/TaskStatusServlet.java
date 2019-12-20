package ru.itpark.framework.servlet;

import ru.itpark.implementation.repository.TaskRepository;
import ru.itpark.implementation.service.TaskService;
import ru.itpark.model.Task;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/status")
public class TaskStatusServlet extends HttpServlet {
    TaskService taskService = new TaskService(new TaskRepository());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Task> tasks = taskService.checkoutDataBase();
        req.setAttribute("tasks", tasks);
        req.getRequestDispatcher("/WEB-INF/result.jsp").forward(req, resp);
    }
}
