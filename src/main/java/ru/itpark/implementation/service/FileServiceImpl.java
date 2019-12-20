package ru.itpark.implementation.service;

import ru.itpark.implementation.repository.FileRepositoryImpl;
import ru.itpark.model.TaskResult;

import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
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

}
