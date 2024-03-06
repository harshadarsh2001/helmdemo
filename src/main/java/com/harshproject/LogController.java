package com.harshproject;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//@RestController
//public class LogController {
//
//    @GetMapping("/logs")
//    public String getLogs() {
//        // Get the project directory path
//        String projectDir = System.getProperty("user.dir");
//
//        // Define the log file path relative to the project directory
//        String logFilePath = projectDir + "/logfile.log";
//
//        try (Stream<String> lines = Files.lines(Paths.get(logFilePath))) {
//            // Read the log file and collect lines into a single string
//            return lines.collect(Collectors.joining("\n"));
//        } catch (IOException e) {
//            // Handle exception
//            e.printStackTrace();
//            return "Failed to read log file: " + e.getMessage();
//        }
//    }
//}

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class LogController {

    @GetMapping("/logs")
    @ResponseBody
    public String getLogs(@RequestParam(required = false) String fileName) {
        // Get the project directory path
        String projectDir = System.getProperty("user.dir");

        try (Stream<Path> paths = Files.walk(Paths.get(projectDir))) {
            // Filter files with .log extension
            Stream<Path> logFiles = paths.filter(Files::isRegularFile)
                                         .filter(path -> path.toString().endsWith(".log"));

            if (fileName != null && !fileName.isEmpty()) {
                // Filter by file name if provided
                logFiles = logFiles.filter(path -> path.getFileName().toString().equals(fileName));
            }

            // Read the log files and collect lines into a single string
            String logs = logFiles.flatMap(this::readLines)
                                  .collect(Collectors.joining("\n"));
            return logs;
        } catch (IOException e) {
            // Handle exception
            e.printStackTrace();
            return "Failed to read log files: " + e.getMessage();
        }
    }

    private Stream<String> readLines(Path path) {
        try {
            return Files.lines(path);
        } catch (IOException e) {
            // Handle exception
            e.printStackTrace();
            return Stream.empty();
        }
    }
}
