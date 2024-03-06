FROM openjdk:17
EXPOSE 8080
ADD target/department-spring-boot-docker.jar department-spring-boot-docker.jar

# Specify the command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/department-spring-boot-docker.jar"]