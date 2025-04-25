# Start with Maven to build the project
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copy all files needed for build
COPY pom.xml .
COPY src ./src

# Build the project (creates target/*.jar)
RUN mvn clean package -DskipTests

# Now create the runtime image
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose and bind to Render's port
ENV PORT=8080
EXPOSE 8080

CMD ["sh", "-c", "java -jar app.jar --server.port=$PORT"]
