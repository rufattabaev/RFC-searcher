package ru.itpark.implementation.repository;

import ru.itpark.model.SearchByFileResult;
import ru.itpark.model.TaskResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class FileRepositoryImpl {
//    public  String readAllStrings(Path path) {
//        try {
//            File file = path.toFile();
//
//            parseAllFilesByPhrase(file);
////            FileInputStream input = new FileInputStream(file);
////            int size = input.available();
////            byte[] data = new byte[size];
////            input.read(data);
////            String content = new String(data);
////            return content;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }




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
        System.out.println("parse file started "+phrase);
        try {
            Thread.sleep(500);
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
        System.out.println("parse file is finished "+phrase);
        return foundStringsFromFile;
    }
}
