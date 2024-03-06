//package com.harshproject.controller;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.boot.SpringApplication;
////import org.springframework.boot.autoconfigure.SpringBootApplication;
////import org.springframework.context.ConfigurableApplicationContext;
////import org.springframework.stereotype.Controller;
////import org.springframework.web.bind.annotation.GetMapping;
////import org.springframework.web.bind.annotation.PostMapping;
////import org.springframework.web.bind.annotation.RequestBody;
////import org.springframework.web.bind.annotation.RestController;
////
////import java.util.Map;
////
////@RestController
////public class DryRunController {
////
////    @Autowired
////    private ConfigurableApplicationContext context;
////
////    @GetMapping("/dry-run")
////    public String dryRun() {
////        try {
////            // Get all beans with @SpringBootApplication annotation
////            Map<String, Object> beansWithAnnotation = context.getBeansWithAnnotation(SpringBootApplication.class);
////
////            if (!beansWithAnnotation.isEmpty()) {
////                // Iterate over beans and find the one that is not a proxy
////                for (Object beanInstance : beansWithAnnotation.values()) {
////                    Class<?> beanClass = beanInstance.getClass();
////                    if (!beanClass.getName().contains("CGLIB")) {
////                        SpringApplication.run(beanClass);
////                        return "Dry run successful";
////                    }
////                }
////                return "No Spring Boot application found";
////            } else {
////                return "No Spring Boot application found";
////            }
////        } catch (Exception e) {
////            return "Error during dry run: " + e.getMessage();
////        }
////    }
////}
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStreamReader;
//
//@RestController
//public class RunSpringBootApplicationController {
//
//    @GetMapping("/dry-run")
//    public String runJarAndGetResponse() {
//        try {
//            // Get the current working directory
//            String currentDirectory = System.getProperty("user.dir");
//            
//            // Get a list of all files in the current directory
//            File directory = new File(currentDirectory);
//            File[] files = directory.listFiles();
//
//            // Find the first JAR file and execute it
//            for (File file : files) {
//                if (file.isFile() && file.getName().endsWith(".jar")) {
//                    Process process = Runtime.getRuntime().exec("java -jar " + file.getAbsolutePath());
//
//                    // Read the output of the process
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//                    StringBuilder output = new StringBuilder();
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        output.append(line).append("\n");
//                    }
//
//                    // Wait for the process to finish
//                    int exitCode = process.waitFor();
//                    System.out.println("Process exited with code: " + exitCode);
//
//                    // Close the reader
//                    reader.close();
//
//                    return output.toString();
//                }
//            }
//
//            return "No JAR files found in the directory: " + currentDirectory;
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//            return "Error during execution: " + e.getMessage();
//        }
//    }
//}

//package com.harshproject.controller;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStreamReader;
//
//@RestController
//public class RunSpringBootApplicationController {
//
//    @GetMapping("/dry-run")
//    public ResponseEntity<String> runJarAndGetResponse() {
//        try {
//            // Get the current working directory
//            String currentDirectory = System.getProperty("user.dir");
//
//            // Search for JAR files recursively within the current directory
//            File directory = new File(currentDirectory);
//            File jarFile = findJarFile(directory);
//
//            // If a JAR file is found, execute it
//            if (jarFile != null) {
//                // Start a separate thread to read the process output
//                Process process = Runtime.getRuntime().exec("java -jar " + jarFile.getAbsolutePath());
//                StringBuilder output = new StringBuilder();
//                Thread outputThread = new Thread(() -> readProcessOutput(process, output));
//                outputThread.start();
//
//                // Wait for the process to finish
//                int exitCode = process.waitFor();
//                System.out.println("Process exited with code: " + exitCode);
//
//                // Wait for the output thread to finish
//                outputThread.join();
//
//                // Return the logs as the response body
//                return ResponseEntity.ok(output.toString());
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No JAR files found in the directory: " + currentDirectory);
//            }
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during execution: " + e.getMessage());
//        }
//    }
//
//    private File findJarFile(File directory) {
//        // Search for JAR files recursively within the directory
//        File[] files = directory.listFiles();
//        for (File file : files) {
//            if (file.isDirectory()) {
//                File jarFile = findJarFile(file);
//                if (jarFile != null) {
//                    return jarFile;
//                }
//            } else if (file.isFile() && file.getName().endsWith(".jar")) {
//                return file;
//            }
//        }
//        return null;
//    }
//
//    private void readProcessOutput(Process process, StringBuilder output) {
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                output.append(line).append("\n");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
//
package com.harshproject;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStreamReader;
//
//@RestController
//public class RunSpringBootApplicationController {
//
//    @PostMapping("/dry-run")
//    public ResponseEntity<String> runJarAndGetResponse() {
//        try {
//            // Get the current working directory
//            String currentDirectory = System.getProperty("user.dir");
//
//            // Search for JAR files recursively within the current directory
//            File directory = new File(currentDirectory);
//            File jarFile = findJarFile(directory);
//
//            // If a JAR file is found, execute it
//            if (jarFile != null) {
//                // Start a separate thread to read the process output
//                Process process = Runtime.getRuntime().exec("java -jar " + jarFile.getAbsolutePath());
//                StringBuilder output = new StringBuilder();
//                Thread outputThread = new Thread(() -> readProcessOutput(process, output));
//                outputThread.start();
//
//                // Wait for the process to finish asynchronously
//                new Thread(() -> {
//                    try {
//                        int exitCode = process.waitFor();
//                        System.out.println("Process exited with code: " + exitCode);
//                        if (exitCode == 0) {
//                            System.out.println("Application started successfully.");
//                        } else {
//                            System.out.println("Application failed to start.");
//                        }
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }).start();
//
//                // Return the logs as the response body asynchronously
//                return ResponseEntity.ok().body(output.toString());
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No JAR files found in the directory: " + currentDirectory);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during execution: " + e.getMessage());
//        }
//    }
//
//    private File findJarFile(File directory) {
//        // Search for JAR files recursively within the directory
//        File[] files = directory.listFiles();
//        for (File file : files) {
//            if (file.isDirectory()) {
//                File jarFile = findJarFile(file);
//                if (jarFile != null) {
//                    return jarFile;
//                }
//            } else if (file.isFile() && file.getName().endsWith(".jar")) {
//                return file;
//            }
//        }
//        return null;
//    }
//
//    private void readProcessOutput(Process process, StringBuilder output) {
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);  // Print the line in real-time
//                output.append(line).append("\n");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class RunSpringBootApplicationController {

    @GetMapping("/dry-run")
    public ResponseEntity<String> runApplicationAndGetResponse() {
        try {
            // Specify the base package where to scan for main classes
            String basePackage = "com.harshproject"; // Change this to your desired base package

            // Scan for main classes annotated with @SpringBootApplication
            List<Class<?>> mainClasses = findMainClasses(basePackage);

            if (mainClasses.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No main class found.");
            }

            // Start a separate thread to capture the output of the application
            StringBuilder output = new StringBuilder();
            Thread outputThread = new Thread(() -> readApplicationOutput(output));
            outputThread.start();

            // Invoke the main method of each main class found
            for (Class<?> mainClass : mainClasses) {
                Method mainMethod = mainClass.getMethod("main", String[].class);
                mainMethod.invoke(null, (Object) new String[0]);
            }

            // Wait for the output thread to finish capturing the output
            outputThread.join();

            return ResponseEntity.ok().body(output.toString());
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InterruptedException | IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during execution: " + e.getMessage());
        }
    }

    private List<Class<?>> findMainClasses(String basePackage) throws IOException, ClassNotFoundException {
        List<Class<?>> mainClasses = new ArrayList<>();

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        SimpleMetadataReaderFactory metadataReaderFactory = new SimpleMetadataReaderFactory();
        String packageSearchPath = "classpath*:" + basePackage.replace(".", "/") + "/**/*.class";
        Resource[] resources = resolver.getResources(packageSearchPath);

        for (Resource resource : resources) {
            MetadataReader reader = metadataReaderFactory.getMetadataReader(resource);
            String className = reader.getClassMetadata().getClassName();
            if (!className.endsWith("module-info.class") && className.startsWith(basePackage)) {
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(SpringBootApplication.class)) {
                    mainClasses.add(clazz);
                }
            }
        }

        return mainClasses;
    }

    private void readApplicationOutput(StringBuilder output) {
        try {
            String userDir = System.getProperty("user.dir");
            File directory = new File(userDir);
            File[] files = directory.listFiles((dir, name) -> name.endsWith(".jar"));
            
            if (files == null || files.length == 0) {
                System.err.println("No JAR files found in the current directory.");
                return;
            }
            
            // Assuming there's only one JAR file in the directory, you can choose the first one
            String jarFilePath = files[0].getAbsolutePath();
            
            Process process = Runtime.getRuntime().exec("java -jar " + jarFilePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);  // Print the line in real-time
                output.append(line).append("\n");
            }

            // Wait for the process to finish
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        // Specify the port
        int port = 9081; // Change this to your desired port

        // Set the port
        new SpringApplicationBuilder(RunSpringBootApplicationController.class)
                .properties("server.port=" + port)
                .run(args);
    }
}

