package ru.itpark.framework.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.itpark.implementation.repository.FileRepositoryImpl;
import ru.itpark.implementation.repository.TaskRepository;
import ru.itpark.implementation.service.FileServiceImpl;
import ru.itpark.implementation.service.TaskService;
import ru.itpark.model.SearchByFileResult;
import ru.itpark.model.TaskResult;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@MultipartConfig
public class UploadDownloadServlet extends HttpServlet {
    private FileServiceImpl fileService;
    private FileRepositoryImpl fileRepository;
    TaskService taskService = new TaskService(new TaskRepository());

    @Override
    public void init() throws ServletException {
        try {
            InitialContext context = new InitialContext();
            fileService = (FileServiceImpl) context.lookup("java:/comp/env/bean/file-service");
            fileRepository = new FileRepositoryImpl();
        } catch (NamingException e) {
            e.printStackTrace();
            throw new ServletException();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        Path path = Paths.get("D:\\projects\\tasksexample-developer\\files\\" + req.getSession().getId());
        if (Files.exists(path)) {
            ObjectMapper objectMapper = new ObjectMapper();
            List<TaskResult> taskResultList = new ArrayList<>();
            Files.walk(path).filter(Files::isRegularFile).forEach(file -> {
                try {
                    TaskResult taskResult = objectMapper.readValue(new FileInputStream(file.toString()), TaskResult.class);
                    taskResult.setTempFileName(file.getFileName().toString());
                    taskResultList.add(taskResult);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            req.setAttribute("itemsMap", taskResultList);
        }

        String fileName = req.getParameter("filename");
        if (fileName != null) {

            resp.setContentType("text/plain");
            resp.setHeader("Content-disposition", "attachment; filename=" + fileName + ".txt");

            try (InputStream in = new FileInputStream(path.toString() + "\\" + fileName);
                 OutputStream out = resp.getOutputStream()) {

                int ARBITARY_SIZE = 1048;
                byte[] buffer = new byte[ARBITARY_SIZE];

                int numBytesRead;
                while ((numBytesRead = in.read(buffer)) > 0) {
                    out.write(buffer, 0, numBytesRead);
                }
            }
        }

        taskService.pushWork();
        req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //TODO: реорганизовать код, вынести всё в интерфейсы
        Part part = req.getPart("file");
        if (!(part == null)) {
            fileService.writeFile(part);
        }

        if (req.getParameter("button").equals("clear")) {
            SearchByFileResult result = new SearchByFileResult();
            req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
        }
        resp.sendRedirect("/");
    }
}