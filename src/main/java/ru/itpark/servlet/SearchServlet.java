package ru.itpark.servlet;

import ru.itpark.repository.TaskRepository;
import ru.itpark.service.FileService;
import ru.itpark.service.TaskService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    private FileService fileService;
    TaskService taskService;

    @Override
    public void init() {
        try {
            fileService = new FileService();
            taskService = new TaskService(new TaskRepository());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String phrase = req.getParameter("phrase");
        if (phrase != null) {
            taskService.createTask(phrase, req.getSession().getId());


        }

        resp.sendRedirect("/");

    }

}
