# Use the official OpenJDK 17 image from Docker Hub
FROM eclipse-temurin:17-jre-alpine
# Set working directory inside the container
WORKDIR /app
# Copy the compiled Java application JAR file into the container
COPY ./target/watchshop-1.0-SNAPSHOT.jar /app
# Expose the port the Spring Boot application will run on
EXPOSE 8080
# Command to run the application
CMD ["java", "-jar", "watchshop-1.0-SNAPSHOT.jar"]