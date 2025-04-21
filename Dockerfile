# Start from an OpenJDK base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the built JAR from local target directory into the container
COPY target/tmdb-proxy-0.0.1-SNAPSHOT.jar app.jar

# Expose port (Render will override it anyway)
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
