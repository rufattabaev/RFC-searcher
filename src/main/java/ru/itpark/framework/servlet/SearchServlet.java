package ru.itpark.framework.servlet;

import ru.itpark.implementation.repository.FileRepositoryImpl;
import ru.itpark.implementation.repository.TaskRepository;
import ru.itpark.implementation.service.FileServiceImpl;
import ru.itpark.implementation.service.TaskService;
import ru.itpark.model.Task;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    private FileServiceImpl fileService;
    ExecutorService executor = Executors.newFixedThreadPool(10);
    FileRepositoryImpl fileRepository = new FileRepositoryImpl();
    TaskService taskService;

    @Override
    public void init() {
        try {
            fileService = new FileServiceImpl();
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


        String fileUrl = req.getParameter("fileName");
        if (fileUrl != null) {

            resp.setContentType("text/plain");
            resp.setHeader("Content-disposition", "attachment; filename=sample.txt");

            try (InputStream in = new FileInputStream(fileUrl);
                 OutputStream out = resp.getOutputStream()) {
                int ARBITARY_SIZE = 1048;
                byte[] buffer = new byte[ARBITARY_SIZE];

                int numBytesRead;
                while ((numBytesRead = in.read(buffer)) > 0) {
                    out.write(buffer, 0, numBytesRead);
                }
            }

        }

        resp.sendRedirect("/");

    }
}
