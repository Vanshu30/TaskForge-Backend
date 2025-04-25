# Use a base Java image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the JAR file (adjust name if needed)
COPY target/*.jar app.jar

# Set environment variable for the port
ENV PORT 8080

# Expose the port (optional but good practice)
EXPOSE 8080

# Start the application (bind to PORT)
CMD ["sh", "-c", "java -jar app.jar --server.port=$PORT"]
