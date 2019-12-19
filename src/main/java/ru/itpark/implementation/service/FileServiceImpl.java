package ru.itpark.implementation.service;

import ru.itpark.implementation.repository.FileRepositoryImpl;
import ru.itpark.model.TaskResult;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileServiceImpl {
    private final String uploadPath;

    public FileServiceImpl() throws IOException {
        uploadPath = System.getenv("UPLOAD_PATH");
        Files.createDirectories(Paths.get(uploadPath));
    }

    public TaskResult searchFilesByPhrase(String phrase) throws IOException {

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<File> fileList = Files.walk(Paths.get(uploadPath))
                .filter(Files::isRegularFile).map(Path::toFile).collect(Collectors.toList());
        FileRepositoryImpl fileRepository = new FileRepositoryImpl();


        return fileRepository.parseAllFilesByPhrase(fileList, phrase);

    }


    public String writeFile(Part part) throws IOException, ServletException {
        String name = part.getSubmittedFileName();
        part.write(Paths.get(uploadPath).resolve(name).toString());
        return name;
    }

    public void downloadFile(String fileUrl, String fileName) {
        try (BufferedInputStream in = new BufferedInputStream(new URL(fileUrl).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            // handle exception
        }
    }
}
