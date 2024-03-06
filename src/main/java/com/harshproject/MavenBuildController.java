//package com.harshproject;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import org.apache.maven.shared.invoker.DefaultInvocationRequest;
//import org.apache.maven.shared.invoker.DefaultInvoker;
//import org.apache.maven.shared.invoker.InvocationRequest;
//import org.apache.maven.shared.invoker.InvocationResult;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Collections;
//
//@RestController
//public class MavenBuildController {
//
//    @GetMapping("/build-project")
//    public String buildProject() {
//        // Set Maven home directory
//        System.setProperty("maven.home", "C:/apache-maven-3.9.6");
//
//        // Define the Maven project directory
//        File pomFile = findPomFile();
//
//        if (pomFile == null) {
//            return "Could not find pom.xml file in the project directory.";
//        }
//
//        // Create a Maven invocation request
//        InvocationRequest request = new DefaultInvocationRequest();
//        request.setPomFile(pomFile);
//        request.setGoals(Collections.singletonList("clean install")); // Specify the goals
//
//        // Create Maven invoker
//        DefaultInvoker invoker = new DefaultInvoker();
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        invoker.setOutputHandler((line) -> {
//            outputStream.write(line.getBytes(StandardCharsets.UTF_8));
//            outputStream.write(System.lineSeparator().getBytes(StandardCharsets.UTF_8));
//        });
//
//        try {
//            // Execute Maven command
//            InvocationResult result = invoker.execute(request);
//
//            // Return output
//            String output = outputStream.toString(StandardCharsets.UTF_8);
//            return "Exit code: " + result.getExitCode() + "\nOutput:\n" + output;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "An error occurred during Maven build: " + e.getMessage();
//        }
//    }
//
//    private File findPomFile() {
//        // Get the project directory path
//        String projectDir = System.getProperty("user.dir");
//
//        // Define the pom file path relative to the project directory
//        Path pomFilePath = Paths.get(projectDir, "pom.xml");
//
//        // Check if the pom file exists
//        if (Files.exists(pomFilePath)) {
//            return pomFilePath.toFile();
//        }
//
//        return null;
//    }
//}

package com.harshproject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
public class MavenBuildController {

    @GetMapping("/build-project")
    public String buildProject() {
        // Define the Maven command
        String mavenCommand = "mvn clean install";

        // Get the project directory path
        String projectDir = System.getProperty("user.dir");

        // Create a ProcessBuilder for running the Maven command
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("cmd", "/c", mavenCommand); // For Windows
        // processBuilder.command("sh", "-c", mavenCommand); // For Unix/Linux

        // Set the working directory to the project directory
        processBuilder.directory(new File(projectDir));

        // Redirect error stream to input stream
        processBuilder.redirectErrorStream(true);

        try {
            // Start the process
            Process process = processBuilder.start();

            // Read the output of the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder outputBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                outputBuilder.append(line).append("\n");
            }

            // Wait for the process to complete
            int exitCode = process.waitFor();

            // Return output
            return "Exit code: " + exitCode + "\nOutput:\n" + outputBuilder.toString();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "An error occurred during Maven build: " + e.getMessage();
        }
    }
}
