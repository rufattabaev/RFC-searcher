package ru.itpark.implementation.repository;

import ru.itpark.model.SearchByFileResult;
import ru.itpark.model.TaskResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileRepositoryImpl {

    public TaskResult parseAllFilesByPhrase(List<File> fileList, String phrase) throws FileNotFoundException {

        TaskResult taskResult = new TaskResult();
        taskResult.setQuery(phrase);
        List<SearchByFileResult> resultList = new ArrayList<>();
        taskResult.setResult(resultList);
        for (File file : fileList) {
            List<String> stringsFromFile = this.parseFile(phrase, file);
            if (!stringsFromFile.isEmpty()) {
                SearchByFileResult searchByFileResult = new SearchByFileResult();
                searchByFileResult.setFileName(file.getName());
                searchByFileResult.setStringsFromFile(stringsFromFile);
                resultList.add(searchByFileResult);
            }

        }
        return taskResult;
    }

    private List<String> parseFile(String phrase, File file) throws FileNotFoundException {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final Scanner scanner = new Scanner(file);
        List<String> foundStringsFromFile = new ArrayList<>();
        while (scanner.hasNextLine()) {
            final String lineFromFile = scanner.nextLine();
            if (lineFromFile.contains(phrase)) {
                foundStringsFromFile.add(lineFromFile);
            }
        }
        return foundStringsFromFile;
    }
}
